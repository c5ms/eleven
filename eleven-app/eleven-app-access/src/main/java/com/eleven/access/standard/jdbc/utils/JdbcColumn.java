package com.eleven.access.standard.jdbc.utils;

import lombok.Data;

@Data
public class JdbcColumn {
    private String tableName;
    private String columnName;
    private int dataType;
    private String dataTypeName;
    private long columnSize;
    private int decimalDigits;
    private boolean isPk;
    private boolean nullable;
    private String remarks;
    private boolean autoIncrement;
}
