package com.ygy.learn.mybatis.sql.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import lombok.AllArgsConstructor;

/**
 * 处理只包含#{}的sql
 */
@AllArgsConstructor
public class StaticTextSqlNode implements SqlNode {
    private String sqlText;
    @Override
    public void apply(DynamicContext dynamicContext) {
        dynamicContext.appendSql(sqlText);
    }
}
