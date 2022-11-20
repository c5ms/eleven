package com.eleven.access.standard.jdbc;

import cn.hutool.core.io.IoUtil;
import cn.hutool.db.ds.pooled.DbConfig;
import cn.hutool.db.ds.pooled.PooledDataSource;
import com.cnetong.access.core.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Service
public class JdbcResourceFactory implements ResourceFactory {

    @Override
    public ComponentSpecification getSpecification() {
        return null;
    }

    @Override
    public JdbcResource create(Map<String, String> config) {
        return new JdbcResource(config);
    }

    @RequiredArgsConstructor
    public static class JdbcResource implements Resource<DataSource> {
        private PooledDataSource dataSource;

        public JdbcResource(Map<String, String> config) {
            createDataSource(config);
        }

        private void createDataSource(Map<String, String> config) {
            DbConfig dbConfig = new DbConfig();
            dbConfig.setUrl(config.get("jdbcUrl"));
            dbConfig.setUser(config.get("username"));
            dbConfig.setPass(config.get("password"));
            dbConfig.setMaxActive(Integer.parseInt(config.getOrDefault("maxConnection", "5")));

            var props=new Properties();

            if (StringUtils.startsWithIgnoreCase(dbConfig.getUrl(),"jdbc:sqlserver")){
                props.setProperty("encrypt","false");
            }

            dbConfig.setConnProps( props);
            dataSource = new PooledDataSource(dbConfig);
        }

        @Override
        public DataSource getActual() {
            return dataSource;
        }

        @Override
        public void update(Map<String, String> config) {
            dataSource.close();
            createDataSource(config);
        }

        public void check() throws Exception {
            IoUtil.closeIfPosible(dataSource.getConnection());
        }

        @Override
        public void destroy() {
            dataSource.close();
        }

        @Override
        public ResourceProducer createProducer(Map<String, String> config) {
            return null;
        }

        @Override
        public ResourceConsumer createConsumer(Map<String, String> config) {
            return null;
        }

    }
}
