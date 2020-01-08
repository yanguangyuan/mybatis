package com.ygy.learn.mybatis.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import lombok.AllArgsConstructor;

/**
 * 处理只包含#{}的sql
 */
@AllArgsConstructor
public class StaticTextSqlNode implements ISqlNode {
    private String sqlText;
    @Override
    public void apply(DynamicContext dynamicContext) {
        dynamicContext.appendSql(sqlText);
    }
}
