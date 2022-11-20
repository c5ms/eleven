package com.eleven.access.standard.jdbc.utils.statement;

import com.cnetong.access.core.AccessRuntimeException;
import lombok.Getter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 查询语句
 * 鸣谢 spring 提供工具类 <br/>
 * 已知依赖:  {@link NamedParameterUtils} 使用该工具解析sql语句
 */
@Getter
public class SelectStatement {

    private final String sql;
    private final ArrayList<Object> args = new ArrayList<>();

    private final String from;
    private final String alias;
    private final PlainSelect selectStatement;


    /**
     * @param sql     查询语句
     * @param context 语句上下文
     * @throws JSQLParserException -
     */
    public SelectStatement(String sql, SqlContext context) throws JSQLParserException {
        this(sql, context, false);
    }

    public SelectStatement(String sql) throws JSQLParserException {
        this(sql, new SqlContext(), false);
    }

    /**
     * @param sql        SQL 语句
     * @param context    执行环境
     * @param cleanWhere 删除where条件
     * @throws JSQLParserException -
     */
    public SelectStatement(String sql, SqlContext context, boolean cleanWhere) throws JSQLParserException {
        var parseSql = sql;
        if (sql.contains("where")) {
            parseSql = sql.substring(0, sql.indexOf("where"));
        }
        if (sql.contains("WHERE")) {
            parseSql = sql.substring(0, sql.indexOf("WHERE"));
        }
        var statement = (Select) CCJSqlParserUtil.parse(parseSql);
        this.selectStatement = ((PlainSelect) statement.getSelectBody());
        this.from = guessTable();
        this.alias = guessTableAlias();

        // 清理where条件
        if (cleanWhere) {
            this.selectStatement.setWhere(null);
        }

        // 解析参数
        if (context != null && !context.isEmpty()) {
            Collections.addAll(this.args, NamedParameterUtils.buildValueArray(sql, context));
            sql = NamedParameterUtils.parseSqlStatementIntoString(sql);
        }

        this.sql = sql;
    }

    String guessTableAlias() {
        String table = null;
        FromItem fromItem = selectStatement.getFromItem();
        if (null != fromItem.getAlias()) {
            table = fromItem.getAlias().getName();
        }
        if (StringUtils.isBlank(table)) {
            if (fromItem instanceof Table) {
                table = ((Table) fromItem).getName();
            }
        }
        if (null == table) {
            throw new AccessRuntimeException("无法分析表名");
        }
        return table;
    }

    // 支持子查询和一般查询 缺少 leftJoin
    String guessTable() {
        String table = null;
        FromItem fromItem = selectStatement.getFromItem();
        if (fromItem instanceof Table) {
            table = ((Table) fromItem).getName();
        }
        if (null == table) {
            throw new AccessRuntimeException("无法分析表名");
        }
        return table;
    }

}
