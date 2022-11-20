package com.eleven.access.standard.jdbc.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一条JDBC执行语句的包装
 */
@Getter
@Setter
public class JdbcSql {
    /**
     * 执行使用的参数
     */
    private final List<Object> args = new ArrayList<>();
    /**
     * 要执行的SQL
     */
    private String sql;


    public JdbcSql() {
    }

    /**
     * @param sql 使用的sql文
     */
    public JdbcSql(String sql) {
        this.sql = sql;
    }


    public JdbcSql(String sql, Object... args) {
        this.sql = sql;
        for (Object arg : args) {
            this.addArg(arg);
        }
    }

    public JdbcSql(String sql, List<Object> args) {
        this.sql = sql;
        for (Object arg : args) {
            this.addArg(arg);
        }
    }

    public JdbcSql addArg(Object arg) {
        this.args.add(arg);
        return this;
    }

    public JdbcSql addArg(List<Object> arg) {
        this.args.addAll(arg);
        return this;
    }

    public JdbcSql addArg(Object[] arg) {
        Collections.addAll(this.args, arg);
        return this;
    }

    @Override
    public String toString() {
        return "JdbcSql{" +
                "sql='" + sql + '\'' +
                ", args=" + args +
                '}';
    }
}
