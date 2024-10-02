package com.eleven.access.standard.jdbc;

import com.cnetong.access.core.ComponentConfigException;
import com.cnetong.access.core.ComponentSpecification;
import com.cnetong.access.core.RecordWriter;
import com.cnetong.access.core.RecordWriterFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.*;
import static com.cnetong.access.core.ComponentSpecification.property;

@Component
@RequiredArgsConstructor
public class JdbcRecordWriterFactory implements RecordWriterFactory {

    @Override
    public RecordWriter create(Map<String, String> config) throws ComponentConfigException {
        JdbcRecordWriter jdbcRecordSink = new JdbcRecordWriter();
        jdbcRecordSink.setDataSource(JdbcConfigurations.createDatasource(config));
        jdbcRecordSink.setEncoding(config.getOrDefault("encoding", StandardCharsets.UTF_8.name()));
        jdbcRecordSink.setBatchSize(Integer.parseInt(config.getOrDefault("batchSize", "1000")));
        jdbcRecordSink.setEmptyAsNull(StringUtils.equals(config.get("emptyAsNull"), "true"));
        return jdbcRecordSink;
    }

    @Override
    public ComponentSpecification getSpecification() {
        return ComponentSpecification.of(RecordWriter.class, "jdbc")
                .label("写入至数据库系统")
                .describe("利用 JDBC 向数据库写入数据")
                .property(
                        property("jdbcUrl", string).withLabel("连接字符串").required(true),
                        property("username", string).withLabel("用户名").required(true),
                        property("password", password).withLabel("密码").required(true),
                        property("encoding", string).withLabel("字符集").required(true).withDefault("UTF-8"),
                        property("batchSize", number).withLabel("批量条数").required(true).withDefault("1000")
                )
                .runtime(
                );
    }
}
