package com.eleven.access.standard.jdbc;

import cn.hutool.core.io.IoUtil;
import com.cnetong.access.core.RecordChannel;
import com.cnetong.access.core.RecordReader;
import com.cnetong.access.core.Schema;
import com.cnetong.access.standard.jdbc.utils.JdbcUtil;
import com.cnetong.access.standard.jdbc.utils.statement.SelectStatement;
import com.cnetong.access.standard.jdbc.utils.statement.SqlContext;
import com.cnetong.common.time.TimeContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 使用SQL同步
 */
@Slf4j
@Getter
@Setter
public class JdbcRecordReader implements RecordReader {

    private final static String timestampName = "startTime";
    private final static String timestampFormat = "yyyy-MM-dd HH:mm:ss";

    //===================== 配置参数 =======================

    private String encoding = StandardCharsets.UTF_8.name();
    private boolean useMills;
    private boolean useUppercase;
    private String querySql;
    private DataSource dataSource;

    @Override
    public void start(RecordChannel channel, Map<String, String> config, Map<String, String> runtime) throws Exception {
        // 获取执行SQL上下文
        SqlContext sqlContext = new SqlContext();

        // 本此开始时间
        Date beginTime = JdbcConfigurations.getDate(runtime, timestampName, timestampFormat);
        if (null == beginTime) {
            beginTime = new Date(0);
        }

        // 制作语句编译上下文
        sqlContext.put(timestampName, new Timestamp(beginTime.getTime()));
        if (useMills) {
            sqlContext.put(timestampName, beginTime.getTime());
        }

        var recordExtractor = new JdbcRecordExtractor(dataSource, channel);
        recordExtractor.setUseUppercase(useUppercase);


        // 执行同步逻辑
        long count = 0;
        for (String query : splitSql(querySql)) {
            var select = new SelectStatement(query, sqlContext);
            count += recordExtractor.extract(select);
        }

        if (count > 0) {
            // 设置开始时间，下次开始是本此开始时间
            runtime.put(timestampName, TimeContext.localDateTime().format(DateTimeFormatter.ofPattern(timestampFormat)));
        }

    }


    @Override
    public Collection<Schema> readSchemas(Map<String, String> config) throws Exception {
        List<Schema> metadataList = new ArrayList<>();
        for (String sql : splitSql(querySql)) {
            SelectStatement select = new SelectStatement(sql, null, true);
            String table = select.getFrom();
            String tableAlias = select.getAlias();
            String query = select.getSql();
            Schema schema = JdbcUtil.extractSchema(dataSource, table, tableAlias, query);
            if (this.useUppercase) {
                schema.tpUppercase();
            }
            metadataList.add(schema);
        }
        return metadataList;
    }

    /**
     * 分割一个SQL语句
     *
     * @param text SQL文本
     * @return sql集合
     */
    protected Set<String> splitSql(String text) {
        final String[] sqlArray = StringUtils.defaultString(text, "").trim().split(";");
        return Arrays.stream(sqlArray)
                .map(String::trim)
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toSet());
    }

    @Override
    public void close() {
        IoUtil.closeIfPosible(dataSource);
    }


}
