package com.demcia.eleven.domain.time.provider;

import com.demcia.eleven.domain.time.TimestampProvider;
import com.demcia.eleven.domain.time.TimestampProviderException;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;

@RequiredArgsConstructor
public class DataBaseTimestampProvider implements TimestampProvider {

    private final DataSource dataSource;

    public static String makeDBTimeSql(String dbType) {
        if (StringUtils.isNotBlank(dbType)) {
            final String _dbType = dbType.toLowerCase().trim().replaceAll("\b+", "");
            final StringBuilder sb = new StringBuilder();
            if (_dbType.contains("oracle") || _dbType.contains("h2")) {
                sb.append("select systimestamp from dual");
            } else if (_dbType.contains("mysql")) {
                sb.append("select current_timestamp(6)");
            } else if (_dbType.contains("sqlserver")) {
                sb.append("select GETDATE()");
            } else if (_dbType.contains("postgresql")) {
                sb.append("select now()");
            }
            return sb.toString();
        }
        return null;
    }

    @Override
    public long provide() throws TimestampProviderException {
        try {
            long timestamp = -1;
            @Cleanup Connection collection = dataSource.getConnection();
            String dbType = collection.getMetaData().getDatabaseProductName();
            if (dbType.toLowerCase().contains("h2")) {
                return System.currentTimeMillis();
            }
            String dbSql = makeDBTimeSql(dbType);
            if (StringUtils.isNotBlank(dbSql)) {
                @Cleanup PreparedStatement sm = collection.prepareStatement(dbSql);
                @Cleanup ResultSet rs = sm.executeQuery();
                if (rs.next()) {
                    Timestamp rsTs = rs.getTimestamp(1);
                    if (null != rsTs) {
                        timestamp = rsTs.getTime();
                    }
                }
            }
            if (-1 == timestamp) {
                throw new TimestampProviderException("读取数据库时间失败");
            }
            return timestamp;
        } catch (SQLException e) {
            throw new TimestampProviderException("读取数据库时间失败", e);
        }

    }

}
