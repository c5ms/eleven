package com.eleven.access.standard.jdbc.utils.statement;

import lombok.Getter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 任意语句
 */
@Getter
public class SqlStatement {

    private final String sql;
    private final ArrayList<Object> args = new ArrayList<>();

    public SqlStatement(String sql, SqlContext context) throws JSQLParserException {
        final Statement statement = CCJSqlParserUtil.parse(sql);
        this.sql = statement.toString();
        // 解析参数
        if (context != null && !context.isEmpty()) {
            Collections.addAll(this.args, NamedParameterUtils.buildValueArray(sql, context));
        }
    }

}
