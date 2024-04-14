package com.eleven.access.standard.jdbc.utils;

import com.cnetong.access.core.AccessRuntimeException;
import com.cnetong.access.core.RecordValue;
import com.cnetong.access.core.Schema;
import com.cnetong.access.core.SchemaField;
import com.cnetong.access.standard.jdbc.utils.statement.SelectStatement;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * 数据库逻辑处理
 * TODO replace with hutool MetaUtil
 */
@Slf4j
@UtilityClass
public class JdbcUtil {

    public static void main(String[] args) throws SQLException {
//        DbConfig dbConfig = new DbConfig();
//        dbConfig.setUrl("jdbc:sqlserver://10.69.2.47:1433;databaseName=DLCIMS;");
//        dbConfig.setUser("xiangmuke_readonly");
//        dbConfig.setPass("Xiangmuke_readonly");
//
//        var props=new Properties();
//
//        if (StringUtils.startsWithIgnoreCase(dbConfig.getUrl(),"jdbc:sqlserver")){
//            props.setProperty("encrypt","false");
//        }
//        dbConfig.setConnProps( props);
//
//
//        dbConfig.setMaxActive(22);
//        var dataSource = new PooledDataSource(dbConfig);
//        System.out.println( JdbcUtil.extractSchema(dataSource,"Announcement"));
        System.out.println(StringUtils.startsWithIgnoreCase("jdbc:sqlserver://10.69.2.47:1433;databaseName=DLCIMS;encrypt=false","jdbc:sqlserver"));
    }

    /**
     * 获取一个数据源下面的表描述
     *
     * @param conn      数据库连接
     * @param tableName 表名
     * @return 表信息
     * @throws SQLException -
     */
    public static JdbcTable getTable(Connection conn, String tableName) throws SQLException {
        String catalog = StringUtils.defaultIfBlank(conn.getCatalog(),"");
        String schema = StringUtils.defaultIfBlank(conn.getSchema(),"");

        JdbcTable table = null;
        var meta=conn.getMetaData();
        try (ResultSet tableRet = meta.getTables(catalog, schema, tableName, new String[]{"TABLE", "VIEW"})) {
            if (tableRet.next()) {
                table = new JdbcTable();
                table.setSchema(schema);
                table.setCatalog(catalog);
                table.setTableName(tableRet.getString("TABLE_NAME"));
                table.setTableType(tableRet.getString("TABLE_TYPE"));
                table.setComments(tableRet.getString("REMARKS"));
            }
        }


        if (null != table) {

            // 设置主键
            try (ResultSet pkRSet = meta.getPrimaryKeys(catalog, schema, tableName)) {
                while (pkRSet.next()) {
                    String pkColumnName = pkRSet.getString("COLUMN_NAME");
                    table.getPkNames().add(pkColumnName);
                }
            }

            //  设置列
            try (ResultSet rs = meta.getColumns(catalog, schema, tableName, "%")) {
                while (rs.next()) {
                    JdbcColumn column = new JdbcColumn();
                    column.setTableName(rs.getString("TABLE_NAME"));
                    column.setColumnName(rs.getString("COLUMN_NAME"));
                    column.setDataType(rs.getInt("DATA_TYPE"));
                    column.setDataTypeName(rs.getString("TYPE_NAME"));
                    column.setColumnSize(rs.getLong("COLUMN_SIZE"));
                    column.setNullable(rs.getBoolean("NULLABLE"));
                    column.setRemarks(rs.getString("REMARKS"));

                    // 设置主键
                    if (table.getPkNames().contains(column.getColumnName())) {
                        column.setPk(true);
                    }

                    // 保留小数位数
                    try {
                        column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
                    } catch (SQLException ignore) {
                        //某些驱动可能不支持，跳过
                    }

                    // 是否自增
                    try {
                        String auto = rs.getString("IS_AUTOINCREMENT");
                        if (StringUtils.equalsIgnoreCase("yes", auto)) {
                            column.setAutoIncrement(true);
                        }
                    } catch (SQLException ignore) {
                        //某些驱动可能不支持，跳过
                    }
                    table.getColumns().add(column);
                }
            }
        }

        return table;
    }

    /**
     * 根据查询语句获取数据列
     *
     * @param conn  数据库连接
     * @param query 查询语句
     * @return 语句对应查询到的列
     */
    public static List<JdbcColumn> getQueryColumns(Connection conn, String query) throws SQLException {
        conn.getMetaData().getTimeDateFunctions();
        List<JdbcColumn> columns = new ArrayList<>();
        if (StringUtils.indexOfIgnoreCase(query,"where")!=-1){
            query=query.substring(0,StringUtils.indexOfIgnoreCase(query,"where"));
        }
        query = "select * from (" + query + ") a where 1=2";
        try (
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; ++i) {
                JdbcColumn column = new JdbcColumn();
                column.setDataType(resultSet.getMetaData().getColumnType(i));
                column.setDataTypeName(resultSet.getMetaData().getColumnTypeName(i));
                column.setColumnName(resultSet.getMetaData().getColumnName(i));
                column.setDecimalDigits(resultSet.getMetaData().getScale(i));
                column.setTableName(resultSet.getMetaData().getTableName(i));
                columns.add(column);
            }
        }
        columns.sort(Comparator.comparing(JdbcColumn::getColumnName));
        return columns;
    }


    /**
     * 设置预编译语句参数
     *
     * @param stmt 语句
     * @param args 参数
     * @throws SQLException -
     */
    public static void setParameters(PreparedStatement stmt, Collection<Object> args) throws SQLException {
        int index = 0;
        for (Object arg : args) {
            if (arg instanceof Date) {
                stmt.setObject(index + 1, new java.sql.Date(((Date) arg).getTime()));
            } else {
                stmt.setObject(index + 1, arg);
            }
            index++;
        }
    }


    /**
     * 制造一个插入SQL
     *
     * @param table   表
     * @param columns 字段
     * @return SQL
     */
    public static String makeInsertSql(String table, Collection<String> columns) {
        StringBuilder sql = new StringBuilder() //
                .append("insert into ") //
                .append(table) //
                .append("("); //

        int nameCount = 0;
        for (String name : columns) {
            if (nameCount > 0) {
                sql.append(",");
            }
            sql.append(name);
            nameCount++;
        }
        sql.append(") values (");
        for (int i = 0; i < nameCount; ++i) {
            if (i != 0) {
                sql.append(",");
            }
            sql.append("?");
        }
        sql.append(")");

        return sql.toString();
    }


    public static String makeDeleteSql(String table, String[] fields) {
        StringBuilder sql = new StringBuilder() //
                .append("delete from ") //
                .append(table) //
                .append(" where 1=1 "); //
        for (String field : fields) {
            sql.append(" and ")
                    .append(field)
                    .append("=?");
        }
        return sql.toString();
    }


    /**
     * 向一个表插入数据
     *
     * @param conn  连接
     * @param table 表
     * @param data  数据
     * @return 影响行数
     * @throws SQLException -
     */
    public static int insertToTable(Connection conn, String table, Map<String, Object> data) throws SQLException {
        String sql = makeInsertSql(table, data.keySet());
        return executeUpdate(conn, sql, data.values());
    }

    /**
     * 从一个表删除
     *
     * @param conn  连接
     * @param table 表
     * @param data  数据
     * @return 影响行数
     * @throws SQLException -
     */
    public static int deleteFromTable(Connection conn, String table, Map<String, Object> data, String... fields) throws SQLException {
        String sql = makeDeleteSql(table, fields);
        Object[] args = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            args[i] = data.get(fields[i]);
        }
        return executeUpdate(conn, sql, args);
    }


    /**
     * 执行更新操作
     *
     * @param conn 连接
     * @param sql  sql
     * @param args 参数
     * @return 影响行数
     * @throws SQLException -
     */
    public static int executeUpdate(Connection conn, String sql, Object... args) throws SQLException {
        return executeUpdate(conn, sql, Arrays.asList(args));
    }


    /**
     * 抽取一个表的模式
     *
     * @param dataSource 连接
     * @param tableName  表明
     * @return 模式
     * @throws SQLException -
     */
    public static Schema extractSchema(DataSource dataSource, String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Schema schema = new Schema(tableName);
            JdbcTable table = JdbcUtil.getTable(connection, tableName);
            if (null == table) {
                throw new AccessRuntimeException("数据库表分析失败，或许此表不存在:" + tableName);
            }
            List<JdbcColumn> columns = table.getColumns();
            for (JdbcColumn jdbcColumn : columns) {
                SchemaField schemaField = new SchemaField(
                        jdbcColumn.getColumnName(),
                        convertJdbcType(jdbcColumn.getDataType()),
                        jdbcColumn.isPk(),
                        jdbcColumn.isNullable()
                );
                schema.addField(schemaField);
            }
            return schema;
        }
    }


    /**
     * 抽取一个表的模式
     *
     * @param dataSource 数据库连接
     * @param select     sql
     * @return 模式
     * @throws SQLException -
     */
    public static Schema extractSchema(DataSource dataSource, SelectStatement select) throws SQLException {
        return extractSchema(dataSource, select.getFrom(), select.getAlias(), select.getSql());
    }


    /**
     * 抽取一个表的模式
     *
     * @param table      要抽取的表
     * @param tableAlias 表别名
     * @param query      查询语句
     * @return 模式
     * @throws SQLException -
     */

    public static Schema extractSchema(DataSource dataSource, String table, String tableAlias, String query) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Schema schema = new Schema(tableAlias);
            List<JdbcColumn> columns = JdbcUtil.getQueryColumns(connection, query);
            for (JdbcColumn jdbcColumn : columns) {
                SchemaField schemaField = new SchemaField(
                        jdbcColumn.getColumnName(),
                        convertJdbcType(jdbcColumn.getDataType()),
                        jdbcColumn.isPk(),
                        jdbcColumn.isNullable()
                );
                schema.addField(schemaField);
            }

          try {
              JdbcTable jdbcTable = JdbcUtil.getTable(connection, table);
              for (JdbcColumn column : jdbcTable.getColumns()) {
                  if (column.isPk()) {
                      schema.getField(column.getColumnName()) .ifPresent(schemeField -> schemeField.setUnique(true));
                  }
              }
          }catch (Exception e ){
              log.warn("Could not get table of "+table, e);
          }
            return schema;
        }
    }


    /**
     * 将java类型转换为对应的记录类型
     *
     * @param jdbcType jdbc的java类型
     * @return 列类型
     */
    public static RecordValue.Type convertJdbcType(int jdbcType) {
        switch (jdbcType) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
            case Types.CLOB:
            case Types.NCLOB:
                return RecordValue.Type.STRING;

            case Types.SMALLINT:
            case Types.TINYINT:
            case Types.INTEGER:
            case Types.BIGINT:
                return RecordValue.Type.LONG;

            case Types.NUMERIC:
            case Types.DECIMAL:
            case Types.FLOAT:
            case Types.REAL:
            case Types.DOUBLE:
                return RecordValue.Type.DOUBLE;


            // for mysql bug, see http://bugs.mysql.com/bug.php?id=35115
            case Types.TIME:
            case Types.DATE:
            case Types.TIMESTAMP:
                return RecordValue.Type.DATE;

            case Types.BINARY:
            case Types.VARBINARY:
            case Types.BLOB:
            case Types.LONGVARBINARY:
                return RecordValue.Type.BYTES;

            // warn: bit(1) -> Types.BIT 可使用BoolColumn
            // warn: bit(>1) -> Types.VARBINARY 可使用BytesColumn
            case Types.BOOLEAN:
            case Types.BIT:
                return RecordValue.Type.BOOL;

            case Types.NULL:
                return RecordValue.Type.BAD;

        }
        return RecordValue.Type.BAD;
    }

    /**
     * 执行更新操作
     *
     * @param conn 连接
     * @param sql  sql
     * @param args 参数
     * @return 影响行数
     * @throws SQLException -
     */
    public static int executeUpdate(Connection conn, String sql, Collection<Object> args) throws SQLException {
        PreparedStatement stmt = null;
        int updateCount;
        try {
            stmt = conn.prepareStatement(sql);
            setParameters(stmt, args);
            updateCount = stmt.executeUpdate();
        } finally {
            close(stmt);
        }
        return updateCount;
    }


    /* ==================== 关闭功能 ================== */
    public static void close(@Nullable Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException var2) {
                log.debug("Could not close JDBC Connection", var2);
            } catch (Throwable var3) {
                log.debug("Unexpected exception on closing JDBC Connection", var3);
            }
        }

    }

    public static void close(@Nullable Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException var2) {
                log.trace("Could not close JDBC Statement", var2);
            } catch (Throwable var3) {
                log.trace("Unexpected exception on closing JDBC Statement", var3);
            }
        }

    }

    public static void close(@Nullable ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException var2) {
                log.trace("Could not close JDBC ResultSet", var2);
            } catch (Throwable var3) {
                log.trace("Unexpected exception on closing JDBC ResultSet", var3);
            }
        }
    }

    public static String makeSelectInSql(Collection<String> places) {
        var items = new ArrayList<>(places);
        StringBuilder snap = new StringBuilder("(");
        for (int i = 0; i < items.size(); i++) {
            snap.append("'").append(items.get(i)).append("'");
            if (i != items.size() - 1) {
                snap.append(",");
            }
        }
        snap.append(")");
        return snap.toString();
    }
}
