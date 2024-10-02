package com.eleven.access.standard.jdbc;

import cn.hutool.db.ds.pooled.DbConfig;
import cn.hutool.db.ds.pooled.PooledDataSource;
import com.cnetong.access.core.ComponentConfigException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@UtilityClass
public class JdbcConfigurations {

    /**
     * 根据配置信息创建数据源
     *
     * @param config 配置
     * @return 数据源
     */
    public static DataSource createDatasource(Map<String, String> config) {
        var url = config.get("jdbcUrl");
        var username = config.get("username");
        var password = config.get("password");

        if (StringUtils.isBlank(url)) {
            throw new ComponentConfigException("数据库连接地址不能为空");
        }
        if (StringUtils.isBlank(username)) {
            throw new ComponentConfigException("数据库用户不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new ComponentConfigException("数据库口令不能为空");
        }
        DbConfig dbConfig = new DbConfig();
        dbConfig.setPass(password);
        dbConfig.setUrl(url);
        dbConfig.setUser(username);
        dbConfig.setInitialSize(16);
        dbConfig.setMaxActive(32);
        return new PooledDataSource(dbConfig);
    }

    public static String getString(Map<String, String> config, String name) {
        if (!config.containsKey(name)) {
            return null;
        }
        return config.get(name);
    }

    public static Date getDate(Map<String, String> config, String name, String pattern) throws ParseException {
        if (!config.containsKey(name)) {
            return null;
        }
        String string = getString(config, name);
        if (StringUtils.isBlank(string)) {
            return null;
        }
        return DateUtils.parseDate(string, pattern);
    }
}
