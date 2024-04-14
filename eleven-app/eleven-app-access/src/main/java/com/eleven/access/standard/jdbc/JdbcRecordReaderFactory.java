package com.eleven.access.standard.jdbc;

import com.cnetong.access.core.ComponentConfigException;
import com.cnetong.access.core.ComponentSpecification;
import com.cnetong.access.core.RecordReader;
import com.cnetong.access.core.RecordReaderFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.*;
import static com.cnetong.access.core.ComponentSpecification.property;

@Component
@RequiredArgsConstructor
public class JdbcRecordReaderFactory implements RecordReaderFactory {

    @Override
    public RecordReader create(Map<String, String> config) throws ComponentConfigException {
        JdbcRecordReader jdbcRecordReader = new JdbcRecordReader();
        jdbcRecordReader.setDataSource(JdbcConfigurations.createDatasource(config));
        jdbcRecordReader.setQuerySql(config.get("querySql"));
        jdbcRecordReader.setEncoding(config.getOrDefault("encoding", StandardCharsets.UTF_8.name()));
        jdbcRecordReader.setUseMills(StringUtils.equals(config.getOrDefault("useMills", "false"), "true"));
        jdbcRecordReader.setUseUppercase(StringUtils.equals(config.getOrDefault("useUppercase", "false"), "true"));
        return jdbcRecordReader;
    }


    @Override
    public ComponentSpecification getSpecification() {
        return ComponentSpecification.of(RecordReader.class, "jdbc")
                .label("(通用)时间戳增量策略")
                .describe("利用 JDBC SQL 查询来根据时间戳字段同步数据")
                .describe("您可以使用内置变量来读取最后一次同步时间，该变量为 startTime，在 SQL 中使用 :startTime 来占位")
                .property(
                        property("jdbcUrl", string).withLabel("连接字符串").required(true).stretch(true),
                        property("username", string).withLabel("用户名").required(true),
                        property("password", password).withLabel("密码").required(true),
                        property("encoding", string).withLabel("字符集").required(true).withDefault("UTF-8"),
                        property("useMills", bool).withLabel("使用时间戳").required(false).withDefault("false"),
                        property("useUppercase", bool).withLabel("全部转大写").required(false).withDefault("false").withTip("开启后，字段和表名会自动转换为大写，比如您的目标库是一个要求全大写且敏感的 Oracle"),
                        property("querySql", sql).withLabel("查询 SQL").required(true).stretch(true)
                                .withTip("例如：select * from log where log_time >=:startTime; startTime是最后一次同步时间，用于时间戳同步标记")
                                .withPlaceholder("输入您的查询 SQL 来读取数据，分号；分割")
                )
                .runtime(
                        property("startTime", datetime).withLabel("开始时间").required(true).withDefault("1970-01-01 00:00:00")
                );
    }
}
