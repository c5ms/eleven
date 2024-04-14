package com.eleven.access.standard.jdbc;

import cn.hutool.core.io.IoUtil;
import com.cnetong.access.core.*;
import com.cnetong.access.core.value.BoolRecordValue;
import com.cnetong.access.standard.jdbc.utils.JdbcUtil;
import com.cnetong.common.util.PaginationList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用JDBC入库逻辑,使用一个mapping确定入库插入,去重和更新的SQL
 *
 * @author wangzc
 */
@Slf4j
@Getter
@Setter
public class JdbcRecordWriter implements RecordWriter {

    private final Map<String, Worker> writers = new ConcurrentHashMap<>();

    private String encoding;
    private boolean emptyAsNull;
    private int batchSize = 500;
    private DataSource dataSource;

    @Override
    public void write(Record record) throws Exception {
        String schemaName = record.getSchema().getName();
        if (!writers.containsKey(schemaName)) {
            var schema = extracted(record, schemaName);
            var executor = new Worker(dataSource, schema);
            executor.setEmptyAsNull(emptyAsNull);
            executor.setBatchSize(batchSize);
            writers.put(schemaName, executor);
        }
        writers.get(schemaName).write(dataSource, record);

    }

    private Schema extracted(Record record, String schemaName) {
        try {
            Schema targetSchema = JdbcUtil.extractSchema(dataSource, schemaName);
            Schema schema = new Schema(targetSchema.getName());
            Schema sourceSchema = record.getSchema();
            for (SchemaField targetField : targetSchema.getFields()) {
                if (sourceSchema.hasField(targetField.getName())) {
                    schema.addField(targetField);
                }
            }
            return schema;
        } catch (Exception e) {
            throw new AccessRuntimeException("数据写入错误，无法分析目标数据库表结构," + schemaName);
        }
    }

    @Override
    public void flush() throws Exception {
        for (Worker value : writers.values()) {
            value.flush(dataSource);
        }
    }


    @Override
    public void close() {
        IoUtil.closeIfPosible(dataSource);
    }

    /**
     * 通用JDBC入库逻辑,使用一个mapping确定入库插入,去重和更新的SQL
     *
     * @author wangzc
     */
    @Slf4j
    @Getter
    @Setter
    public static class Worker {
        private final String tableName;
        private final JdbcSql insertSql;
        private final JdbcSql updateSql;
        private final List<Record> writeBuffer;
        private final Map<String, Integer> sqlTypes;
        private final Schema schema;
        private boolean emptyAsNull;
        private int batchSize;

        Worker(DataSource dataSource, Schema schema) throws Exception {
            this.schema = schema;
            this.tableName = schema.getName();
            this.sqlTypes = getSqlTypes(dataSource);
            this.insertSql = makeInsertSql();
            this.updateSql = makeUpdateSql();
            this.writeBuffer = new LinkedList<>();
        }


        public void write(DataSource dataSource, Record record) throws Exception {
            writeBuffer.add(record);
            if (writeBuffer.size() >= batchSize) {
                flush(dataSource);
            }
        }

        public void flush(DataSource dataSource) throws Exception {
            List<Record> updateRecords = new ArrayList<>();
            List<Record> insertRecords = new ArrayList<>();
            switchInsertUpdate(dataSource, writeBuffer, insertRecords, updateRecords);
            if (!insertRecords.isEmpty()) {
                doBatchMerge(dataSource, insertSql, insertRecords);
            }
            if (!updateRecords.isEmpty()) {
                doBatchMerge(dataSource, updateSql, updateRecords);
            }
            writeBuffer.clear();
        }

        private JdbcSql makeInsertSql() {
            JdbcSql sqlDescription = new JdbcSql();
            StringBuilder sql = new StringBuilder()
                    .append("INSERT INTO ")
                    .append(this.tableName)
                    .append(" ( ");
            int nameCount = 0;
            for (SchemaField column : schema.getFields()) {
                String targetColumn = column.getName();

                if (nameCount > 0) {
                    sql.append(",");
                }
                sql.append(targetColumn);
                nameCount++;
                sqlDescription.valueHolder.add(targetToValueHolder(column));
            }
            sql.append(" ) VALUES ( ");
            for (int i = 0; i < nameCount; i++) {
                if (i != 0) {
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(") ");
            sqlDescription.sql = sql.toString();
            return sqlDescription;
        }

        private JdbcSql makeUpdateSql() {
            JdbcSql sqlDescription = new JdbcSql();
            StringBuilder sql = new StringBuilder()
                    .append("UPDATE ")
                    .append(this.tableName)
                    .append(" SET ");
            int nameCount = 0;
            final int size = schema.getFields().size();
            for (SchemaField column : schema.getFields()) {
                sql.append(column.getName()).append(" = ?");
                if (nameCount++ < size) {
                    sql.append(",");
                }
                sqlDescription.valueHolder.add(targetToValueHolder(column));
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE 1=1 ");
            for (SchemaField column : schema.getFields()) {
                if (column.isUnique()) {
                    sql.append(" AND ").append(column.getName()).append(" = ? ");
                    //记录占位符字段
                    sqlDescription.valueHolder.add(targetToValueHolder(column));
                }
            }
            sqlDescription.sql = sql.toString();
            return sqlDescription;
        }

        private JdbcSql makeQuerySql(List<Record> records) {
            JdbcSql sqlDescription = new JdbcSql();
            StringBuilder sql = new StringBuilder().append("SELECT  ");
            int nameCount = 0;
            for (SchemaField column : schema.getUniqueFields()) {
                if (column.isUnique()) {
                    if (nameCount++ != 0) {
                        sql.append(",");
                    }
                }
                sql.append(column.getName());
            }
            sql.append(" FROM ").append(this.tableName).append(" WHERE 1=1 ");
            for (SchemaField column : schema.getUniqueFields()) {
                StringBuilder sqlPartial = new StringBuilder("(");
                for (Record ignored : records) {
                    sqlPartial.append("?,");
                    //记录占位符字段
                    sqlDescription.valueHolder.add(targetToValueHolder(column));
                }
                sqlPartial = new StringBuilder(sqlPartial.substring(0, sqlPartial.length() - 1) + ")");
                sql.append(" AND ").append(column.getName()).append(" IN ").append(sqlPartial);
            }
            sqlDescription.sql = sql.toString();
            return sqlDescription;
        }

        private ValueHolder targetToValueHolder(SchemaField column) {
            ValueHolder valueHolder = new ValueHolder();
            valueHolder.columnName = column.getName();
            valueHolder.targetSqlType = sqlTypes.get(column.getName());
            if (Objects.isNull(valueHolder.targetSqlType)) {
                throw new AccessRuntimeException(String.format("无法找到目标列[%s].[%s]", this.tableName, valueHolder.columnName));
            }
            return valueHolder;
        }

        private void switchInsertUpdate(DataSource dataSource, List<Record> records, List<Record> insertRecords, List<Record> updateRecords) throws SQLException {
            Set<String> existKeys = new HashSet<>();
            PaginationList<Record> listPagination = PaginationList.NewInstance(records, 1000);
            while (listPagination.hasNext() && schema.getUniqueFields().size() > 0) {
                List<Record> pageRecords = listPagination.next();
                this.queryExistKeys(dataSource, pageRecords, existKeys);
            }
            for (Record record : records) {
                String recordKey = this.recordKey(record);
                if (existKeys.contains(recordKey)) {
                    updateRecords.add(record);
                } else {
                    insertRecords.add(record);
                    existKeys.add(recordKey);
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("批处理入库数据筛选完毕,更新:{}条,插入:{}条", updateRecords.size(), insertRecords.size());
            }
        }

        private String recordKey(Record record) {
            StringBuilder key = new StringBuilder();
            for (SchemaField column : schema.getUniqueFields()) {
                RecordValue keyColumn = record.getColumn(column.getName());
                if (null == keyColumn) {
                    return "";
                }
                key.append(keyColumn.asString()).append("$$");
            }
            return key.toString();
        }

        private void queryExistKeys(DataSource dataSource, List<Record> records, Set<String> keySet) throws SQLException {
            try (Connection conn = dataSource.getConnection()) {
                JdbcSql querySql = makeQuerySql(records);
                try (PreparedStatement preparedStatement = conn.prepareStatement(querySql.sql)) {
                    preparedStatement.setFetchSize(records.size() + 1);
                    int index = 0;
                    for (SchemaField ignore : schema.getUniqueFields()) {
                        for (Record record : records) {
                            ValueHolder valueHolder = querySql.valueHolder.get(index);
                            RecordValue column = record.getColumn(valueHolder.columnName);
                            fillPreparedStatementColumnType(preparedStatement, ++index, valueHolder.targetSqlType, column);
                        }
                    }
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        resultSet.setFetchSize(records.size() + 1);
                        int col = resultSet.getMetaData().getColumnCount();
                        while (resultSet.next()) {
                            StringBuilder keyBuilder = new StringBuilder();
                            for (int i = 1; i <= col; i++) {
                                keyBuilder.append(resultSet.getObject(i)).append("$$");
                            }
                            keySet.add(keyBuilder.toString());
                        }
                    }
                }
            }
        }

        private void doBatchMerge(DataSource dataSource, JdbcSql sqlDesc, List<Record> buffer) throws SQLException {
            try (Connection conn = dataSource.getConnection()) {
                conn.setAutoCommit(false);
                try (PreparedStatement preparedStatement = conn.prepareStatement(sqlDesc.sql)) {
                    for (Record record : buffer) {
                        fillPreparedStatement(preparedStatement, sqlDesc, record);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    conn.commit();
                } catch (Exception e) {
                    log.error("SQL执行失败:{}", sqlDesc.sql);
                    conn.rollback();
                    throw e;
                }
            }
        }

        // 直接使用了两个类变量：columnNumber,resultSetMetaData
        private void fillPreparedStatement(PreparedStatement preparedStatement, JdbcSql sqlDesc, Record record) throws SQLException {
            int columnIndex = 0;
            //遍历SQL中给出的占位符
            for (ValueHolder valueHolder : sqlDesc.valueHolder) {
                RecordValue sourceColumn = record.getColumn(valueHolder.columnName);
                columnIndex++;
                //根据类型进行值转换,并且设置到预编译语句中
                fillPreparedStatementColumnType(preparedStatement, columnIndex, valueHolder.targetSqlType, sourceColumn);
            }
        }

        private void fillPreparedStatementColumnType(PreparedStatement preparedStatement, int columnIndex, int columnSqlType, RecordValue column) throws SQLException {
            //如果来源表查出来的数据为null,直接设置为null
            if (null == column || null == column.getRaw()) {
                preparedStatement.setObject(columnIndex, null);
                return;
            }
//            if (column instanceof NullRecordValue) {
//                preparedStatement.setObject(columnIndex, null);
//            } else if (column instanceof DateRecordValue) {
//                preparedStatement.setDate(columnIndex, new java.sql.Date(column.asDate().getTime()));
//            } else if (column instanceof DoubleRecordValue) {
//                preparedStatement.setDouble(columnIndex, column.asDouble());
//            } else if (column instanceof LongRecordValue) {
//                preparedStatement.setLong(columnIndex, column.asLong());
//            } else if (column instanceof BytesRecordValue) {
//                preparedStatement.setBytes(columnIndex, column.asBytes());
//            } else if (column instanceof StringRecordValue) {
//                preparedStatement.setString(columnIndex, column.asString());
//            } else {
//                preparedStatement.setObject(columnIndex, column.getRaw());
//            }

            switch (columnSqlType) {
                case Types.CHAR:
                case Types.NCHAR:
                case Types.CLOB:
                case Types.NCLOB:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.NVARCHAR:
                case Types.LONGNVARCHAR:

                    // warn: bit(1) -> Types.BIT 可使用setBoolean
                    // warn: bit(>1) -> Types.VARBINARY 可使用setBytes
                case Types.BIT:
                    if (null == column.getRaw()) {
                        preparedStatement.setObject(columnIndex, null);
                    } else {
                        if (column instanceof BoolRecordValue) {
                            preparedStatement.setBoolean(columnIndex, column.asBoolean());
                        } else {
                            //byte 转string 需要编码格式
                            preparedStatement.setString(columnIndex, column.asString());
                        }
                    }
                    break;

                case Types.SMALLINT:
                case Types.INTEGER:
                case Types.BIGINT:
                case Types.TINYINT:
                    String longValue = column.asString();
                    if (emptyAsNull && "".equals(longValue)) {
                        preparedStatement.setObject(columnIndex, null);
                    } else if (null == column.getRaw()) {
                        preparedStatement.setObject(columnIndex, null);
                    } else {
                        preparedStatement.setDouble(columnIndex, column.asLong());
                    }
                    break;
                case Types.NUMERIC:
                case Types.DECIMAL:
                case Types.FLOAT:
                case Types.REAL:
                case Types.DOUBLE:
                    String strValue = column.asString();
                    if (emptyAsNull && "".equals(strValue)) {
                        preparedStatement.setObject(columnIndex, null);
                    } else if (null == column.getRaw()) {
                        preparedStatement.setObject(columnIndex, null);
                    } else {
                        preparedStatement.setDouble(columnIndex, column.asDouble());
                    }
                    break;

                case Types.DATE:
                    java.sql.Date sqlDate;
                    Date utilDate = column.asDate();
                    if (null != utilDate) {
                        sqlDate = new java.sql.Date(utilDate.getTime());
                        preparedStatement.setDate(columnIndex, sqlDate);
                    } else {
                        preparedStatement.setNull(columnIndex, Types.DATE);
                    }
                    break;

                case Types.TIME:
                    Time sqlTime;
                    utilDate = column.asDate();
                    if (null != utilDate) {
                        sqlTime = new Time(utilDate.getTime());
                        preparedStatement.setTime(columnIndex, sqlTime);
                    } else {
                        preparedStatement.setNull(columnIndex, Types.TIME);
                    }
                    break;

                case Types.TIMESTAMP:
                    Timestamp sqlTimestamp;
                    utilDate = column.asDate();
                    if (null != utilDate) {
                        sqlTimestamp = new Timestamp(utilDate.getTime());
                        preparedStatement.setTimestamp(columnIndex, sqlTimestamp);
                    } else {
                        preparedStatement.setNull(columnIndex, Types.TIMESTAMP);
                    }
                    break;

                case Types.BINARY:
                case Types.VARBINARY:
                case Types.BLOB:
                case Types.LONGVARBINARY:
                    if (null == column.getRaw()) {
                        preparedStatement.setObject(columnIndex, null);
                    } else {
                        preparedStatement.setBytes(columnIndex, column.asBytes());
                    }
                    break;

                case Types.BOOLEAN:
                    if (null == column.getRaw()) {
                        preparedStatement.setNull(columnIndex, Types.BOOLEAN);
                    } else {
                        preparedStatement.setBoolean(columnIndex, column.asBoolean());
                    }
                    break;
                default:
                    preparedStatement.setObject(columnIndex, column.getRaw());
                    break;
            }
        }

        /**
         * @return [COLUMN_NAME:jdbcType]
         */
        private Map<String, Integer> getSqlTypes(DataSource dataSource) throws Exception {
            try (Connection connection = dataSource.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String queryColumnSql = "select * from " + schema.getName() + " where 1=2";
                    try (ResultSet rs = statement.executeQuery(queryColumnSql)) {
                        final Map<String, Integer> typeMap = new HashMap<>();
                        ResultSetMetaData rsMetaData = rs.getMetaData();
                        for (int i = 1, len = rsMetaData.getColumnCount(); i <= len; i++) {
                            typeMap.put(rsMetaData.getColumnName(i), rsMetaData.getColumnType(i));
                        }
                        return typeMap;
                    }
                } catch (Exception e) {
                    throw new AccessRuntimeException(String.format("获取表:%s 的字段的元信息失败", schema), e);
                }
            }
        }


        public static class JdbcSql {
            String sql;
            List<ValueHolder> valueHolder = new ArrayList<>();
        }

        @ToString
        public static class ValueHolder {
            String columnName;
            Integer targetSqlType;
        }

    }
}
