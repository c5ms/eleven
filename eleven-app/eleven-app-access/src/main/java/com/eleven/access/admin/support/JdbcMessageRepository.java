package com.eleven.access.admin.support;

import cn.hutool.json.JSONUtil;
import com.cnetong.access.core.Message;
import com.cnetong.access.core.MessageErrors;
import com.cnetong.access.core.MessageLog;
import com.cnetong.access.core.MessageRepository;
import com.cnetong.common.json.JsonUtil;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.ProcessRejectException;
import com.github.drinkjava2.jdialects.Dialect;
import io.lettuce.core.cluster.PartitionException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdbcMessageRepository implements MessageQuery, MessageRepository {

    private static final Object tableCheckLock = new Object();
    private final static String CREATE_TABLE_DDL = "store/CREATE_TABLE_DDL";

    private final static String TABLE_NAME = "message_log_";
    private final static String INSERT_SQL = "insert into " + "%s " + "(message_id_,partition_ ,topic_,state_ ,direction_,endpoint_id_ ,message_ ,exception_ ,process_time_ ,create_time_) " + "values " + "(?,?,?,?,?,?,?,?,?,?) ";

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    private final RowMapper<MessageLog> messageLogRowMapper = (rs, rowNum) -> {
        MessageLog detail = new MessageLog();
        fillLog(rs, detail);
        var message = rs.getString("message_");
        var vo = JsonUtil.toObject(message, MessageVo.class);
        detail.setHeader(vo.getHeader());
        detail.setBody(vo.getMessageBody());
        return detail;
    };

    @NotNull
    private static String toTableName(String partition) {
        return TABLE_NAME + partition;
    }

    private void fillLog(ResultSet rs, MessageLog messageLog) throws SQLException {
        var message_id_ = rs.getString("message_id_");
        var partition_ = rs.getString("partition_");
        var endpoint_id_ = rs.getString("endpoint_id_");
        var topic_ = rs.getString("topic_");
        var state_ = rs.getString("state_");
        var direction_ = rs.getString("direction_");
        var exception_ = rs.getString("exception_");
        var process_time_ = rs.getTimestamp("process_time_");
        var create_time_ = rs.getTimestamp("create_time_");

        messageLog.setPartition(partition_);
        messageLog.setMessageId(message_id_);
        messageLog.setTopic(topic_);
        messageLog.setEndpointId(endpoint_id_);
        messageLog.setDirection(Message.Direction.valueOf(direction_));
        messageLog.setException(exception_);
        messageLog.setState(Message.State.valueOf(state_));
        messageLog.setProcessTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(process_time_.getTime()), TimeZone.getDefault().toZoneId()));
        messageLog.setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(create_time_.getTime()), TimeZone.getDefault().toZoneId()));
    }

    @Override
    public void save(MessageLog log) {
        var partition = log.getPartition();
        var tableName = toTableName(partition);
        var sql = String.format(INSERT_SQL, tableName);

        Object[] args = new Object[]{
                log.getMessageId(),
                log.getPartition(),
                log.getTopic(),
                log.getState().toString(),
                log.getDirection().toString(),
                log.getEndpointId(),
                JSONUtil.toJsonStr(new MessageVo(log.getHeader(), log.getBody())),
                log.getException(),
                log.getProcessTime(),
                log.getCreateTime()};
        try {
            jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            // 开始尝试解决
            try {
                // 检查是不是表不存在的问题
                boolean isTableProblem = isTableExitProblem(e);
                if (isTableProblem) {
                    createTable(tableName);
                }
                jdbcTemplate.update(sql, args);
            } catch (Exception re) {
                throw MessageErrors.PERSIST_ERROR.exception("数据入库失败,分区:" + partition, re);
            }

        }
    }


    /**
     * 判断是不是表不存在的问题
     *
     * @param e 异常
     * @return true 表示是
     */
    private boolean isTableExitProblem(Exception e) {
        boolean isTableProblem = false;
        // 确认是不是表的问题
        Throwable cause = ExceptionUtils.getRootCause(e);
        if (cause instanceof SQLException) {
            int vendorCode = ((SQLException) cause).getErrorCode();
            // oracle 表不存在 942
            if (942 == vendorCode) {
                // 保证表存在
                isTableProblem = true;
            }
            // mysql 表不存在 1146
            if (1146 == vendorCode) {
                isTableProblem = true;
            }
            // postgresql
            if ("42P01".equals(((SQLException) cause).getSQLState())) {
                isTableProblem = true;
            }
        }
        return isTableProblem;
    }


    @Override
    public Page<MessageLog> query(String partition, MessageQueryFilter filter) {
        try {
            var dialect = Dialect.guessDialect(dataSource);
            var tableName = toTableName(partition);
            var args = new ArrayList<>();

            String querySql = String.format("select * from %s t where 1=1 ", tableName);
            if (Objects.nonNull(filter.getStartTime())) {
                querySql += "and create_time_ >=? ";
                args.add(filter.getStartTime());
            }

            if (Objects.nonNull(filter.getEndTime())) {
                querySql += "and create_time_ <=? ";
                args.add(filter.getEndTime());
            }

            if (Objects.nonNull(filter.getDirection())) {
                querySql += "and direction_ =? ";
                args.add(filter.getDirection().name());
            }

            if (StringUtils.isNotBlank(filter.getEndpoint())) {
                querySql += "and endpoint_id_ =? ";
                args.add(filter.getEndpoint());
            }


            if (StringUtils.isNotBlank(filter.getTopic())) {
                querySql += "and topic_ =? ";
                args.add(filter.getTopic());
            }

            if (Objects.nonNull(filter.getState())) {
                querySql += "and state_ =? ";
                args.add(filter.getState().name());
            }

            querySql += " order by create_time_ desc ";

            String pageSql = dialect.pagin(filter.getPage(), filter.getSize(), querySql);
            String countSql = "select count(1) from ( " + querySql + " ) t";
            List<MessageLog> messageLogs = jdbcTemplate.query(pageSql, new RowMapperResultSetExtractor<>(messageLogRowMapper), args.toArray());
            return Page.of(messageLogs).withSize(filter.getSize()).withTotalSize(ObjectUtils.defaultIfNull(jdbcTemplate.queryForObject(countSql, Integer.class, args.toArray()), 0));
        } catch (Exception e) {
            throw MessageErrors.PERSIST_ERROR.exception(e);
        }
    }

    @Override
    public Optional<MessageLog> get(String partition, String id) {
        try {
            var tableName = toTableName(partition);
            var querySql = String.format("select * from %s t where partition_=? and message_id_=?", tableName);
            var args = new ArrayList<>();
            args.add(partition);
            args.add(id);
            var detail = jdbcTemplate.queryForObject(querySql, messageLogRowMapper, args.toArray());
            return Optional.ofNullable(detail);
        } catch (Exception e) {
            throw MessageErrors.PERSIST_ERROR.exception(e);
        }
    }

    @Override
    public void delete(String partition, String id) {
        try {
            var tableName = toTableName(partition);
            var querySql = String.format("delete from %s t where partition_=? and message_id_=?", tableName);
            jdbcTemplate.update(querySql, partition, id);
        } catch (Exception e) {
            throw MessageErrors.PERSIST_ERROR.exception(e);
        }
    }

    @Override
    public void update(MessageLog log) {
        try {
            var messageId = log.getMessageId();
            var partition = log.getPartition();
            var tableName = toTableName(partition);
            var querySql = String.format("update  %s t set t.state_=?,t.exception_=?,t.process_time_=? where partition_=? and message_id_=?", tableName);

            jdbcTemplate.update(querySql, log.getState().toString(), log.getException(), log.getProcessTime(), partition, messageId);
        } catch (Exception e) {
            throw MessageErrors.PERSIST_ERROR.exception(e);
        }
    }


    /**
     * 判断当前表是否存在
     *
     * @param tableName 表名
     * @return true 表示表存在
     * @throws SQLException -
     */
    private boolean isTableExist(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final String catalog = connection.getCatalog();
            final String schema = connection.getSchema();
            final DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables(catalog, schema, tableName.toUpperCase(), new String[]{"TABLE"})) {
                if (null == rs) {
                    return false;
                }
                if (rs.next()) {
                    return StringUtils.equalsAnyIgnoreCase(rs.getString("TABLE_NAME"), tableName);
                }
            }
        }
        return false;
    }

    private void createTable(String tableName) {
        synchronized (tableCheckLock) {
            try {
                try (var conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
                    if (isTableExist(tableName)) {
                        return;
                    }
                    log.info("尝试创建表:{}", tableName);

                    var resource = CREATE_TABLE_DDL;
                    String dbType = conn.getMetaData().getDatabaseProductName();
                    final String _dbType = dbType.toLowerCase().trim().replaceAll("\b+", "");
                    if (_dbType.contains("mysql")) {
                        resource += "-mysql.sql";
                    }
                    if (_dbType.contains("oracle")) {
                        resource += "-oracle.sql";
                    }
                    if (_dbType.contains("postgresql")) {
                        resource += "-postgresql.sql";
                    }
                    @Cleanup InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
                    if (null == is) {
                        throw ProcessRejectException.of("不支持的数据库类型");
                    }
                    String ddl = IOUtils.toString(is, StandardCharsets.UTF_8);
                    ddl = ddl.replace("v$TABLE_NAME$", tableName);
                    String[] ddlList = ddl.split(";");
                    for (String s : ddlList) {
                        s = StringUtils.trim(s);
                        if (StringUtils.isNotBlank(s) && !StringUtils.startsWith(s, "#")) {
                            this.jdbcTemplate.update(s);
                        }
                    }
                    log.info("表创建成功:{}", tableName);
                } catch (SQLException | IOException ignored) {
                    //  创建/检查表失败
                    if (!isTableExist(tableName)) {
                        log.warn("表创建失败，或许是已经存在");
                    }
                }
            } catch (SQLException e) {
                throw new PartitionException(e);
            }
        }
    }


    @Override
    public void dropPartition(String partition) {
        var dialect = Dialect.guessDialect(dataSource);
        var tableName = toTableName(partition);
        var ddl = dialect.dropTableDDL(tableName);
        jdbcTemplate.update(ddl);
    }

    @Override
    public void createPartition(String partition) {
        var tableName = toTableName(partition);
        createTable(tableName);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class MessageVo {
        Map<String, String> header;
        String messageBody;
    }
}
