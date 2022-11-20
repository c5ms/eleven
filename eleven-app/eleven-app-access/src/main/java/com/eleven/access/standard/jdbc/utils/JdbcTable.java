package com.eleven.access.standard.jdbc.utils;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author wangzc
 */
@Data
public class JdbcTable {
    private String catalog;
    private String schema;
    private String tableName;
    private String tableType;
    private String comments;
    private Set<String> pkNames = new LinkedHashSet<>();
    private List<JdbcColumn> columns = new LinkedList<>();
}
