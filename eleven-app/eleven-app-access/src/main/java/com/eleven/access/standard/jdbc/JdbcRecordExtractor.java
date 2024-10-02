package com.eleven.access.standard.jdbc;

import com.cnetong.access.core.AccessRuntimeException;
import com.cnetong.access.core.DefaultRecord;
import com.cnetong.access.core.RecordChannel;
import com.cnetong.access.core.Schema;
import com.cnetong.access.core.value.*;
import com.cnetong.access.standard.jdbc.utils.JdbcUtil;
import com.cnetong.access.standard.jdbc.utils.statement.SelectStatement;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Setter
public class JdbcRecordExtractor {

    /**
     * 数据源
     */
    private final DataSource dataSource;

    /**
     * 目标通道
     */
    private final RecordChannel recordChannel;

    /**
     * 字段全部使用大写
     */
    private boolean useUppercase = false;

    /**
     * 字符编码
     */
    private String encoding = "utf-8";

    /**
     * 是否正在运行
     */
    private volatile boolean running;


    public JdbcRecordExtractor(DataSource dataSource, RecordChannel recordChannel) {
        this.dataSource = dataSource;
        this.recordChannel = recordChannel;
    }

    public int extract(String sql) {
        try {
            return this.extract(new SelectStatement(sql));
        } catch (JSQLParserException e) {
            throw new AccessRuntimeException("SQL 分析错误 :" + sql, e);
        }
    }

    /**
     * 抽取数据
     *
     * @param select 执行 SQL
     * @return 抽取多少条
     * @throws Exception -
     */
    public int extract(SelectStatement select) {
        try {
            if (this.running) {
                throw new IllegalStateException("extraction is performing");
            }
            this.running = true;

            int count = 0;
            if (log.isDebugEnabled()) {
                log.debug("使用SQL查询 ===> {}", select.getSql());
                log.debug("使用SQL参数 ===> {}", select.getArgs());
            }

            String tableAlias = select.getAlias();
            String tableName = select.getFrom();
            Schema schema = JdbcUtil.extractSchema(dataSource, tableName, tableAlias, select.getSql());

            if (this.useUppercase) {
                schema.tpUppercase();
            }
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement stmt = connection.prepareStatement(select.getSql(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                    JdbcUtil.setParameters(stmt, select.getArgs());
                    try (ResultSet rs = stmt.executeQuery()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        while (rs.next() && running) {
                            Record record = DefaultRecord.builder()
                                    .schema(schema)
                                    .initialCapacity(metaData.getColumnCount())
                                    .charset(encoding)
                                    .build();
                            fillRecord(record, rs);
                            recordChannel.write(record);
                            count++;
                        }
                    }
                }
            }
            this.running = false;
            return count;
        } catch (JSQLParserException e) {
            throw new AccessRuntimeException("SQL 分析错误", e);
        } catch (Exception e) {
            if (e instanceof AccessRuntimeException) {
                throw (AccessRuntimeException) e;
            }
            throw new AccessRuntimeException("数据抽取错误", e);
        }
    }


    public void stop() {
        this.running = false;
    }

    /**
     * 使用jdbc数据填充一条记录
     *
     * @param record 记录
     * @param rs     数据结果集
     * @throws SQLException -
     */
    public void fillRecord(Record record, ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnLabel(i);
            AbstractRecordValue column;
            switch (metaData.getColumnType(i)) {
                case Types.CHAR:
                case Types.NCHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.NVARCHAR:
                case Types.LONGNVARCHAR:
                case Types.CLOB:
                case Types.NCLOB:
                    column = new StringRecordValue(rs.getString(i));
                    break;

                case Types.SMALLINT:
                case Types.TINYINT:
                case Types.INTEGER:
                case Types.BIGINT:
                    column = new LongRecordValue(rs.getString(i));
                    break;

                case Types.NUMERIC:
                case Types.DECIMAL:
                case Types.FLOAT:
                case Types.REAL:
                case Types.DOUBLE:
                    column = new DoubleRecordValue(rs.getString(i));
                    break;

                case Types.TIME:
                    column = new DateRecordValue(rs.getTime(i));
                    break;

                // for mysql bug, see http://bugs.mysql.com/bug.php?id=35115
                case Types.DATE:
                    if (metaData.getColumnTypeName(i).equalsIgnoreCase("year")) {
                        column = new LongRecordValue(rs.getInt(i));
                    } else {
                        column = new DateRecordValue(rs.getDate(i));
                    }
                    break;

                case Types.TIMESTAMP:
                    column = new DateRecordValue(rs.getTimestamp(i));
                    break;

                case Types.BINARY:
                case Types.VARBINARY:
                case Types.BLOB:
                case Types.LONGVARBINARY:
                    column = new BytesRecordValue(rs.getBytes(i));
                    break;

                // warn: bit(1) -> Types.BIT 可使用BoolColumn
                // warn: bit(>1) -> Types.VARBINARY 可使用BytesColumn
                case Types.BOOLEAN:
                case Types.BIT:
                    column = new BoolRecordValue(rs.getBoolean(i));
                    break;

                case Types.NULL:
                    String stringData = null;
                    if (rs.getObject(i) != null) {
                        stringData = rs.getObject(i).toString();
                    }
                    column = new StringRecordValue(stringData);
                    break;

                default:
                    throw new AccessRuntimeException(
                            String.format(
                                    "不支持数据库读取这种字段类型. 字段名:[%s], 类型名称:[%s], 字段Java类型:[%s]. 请尝试使用数据库函数将其转换为支持的类型 或者不同步该字段 .",
                                    metaData.getColumnName(i),
                                    metaData.getColumnTypeName(i),
                                    metaData.getColumnClassName(i)));
            }
            if (useUppercase) {
                columnName = StringUtils.upperCase(columnName);
            }
            record.putColumn(columnName, column);
        }
    }


}
