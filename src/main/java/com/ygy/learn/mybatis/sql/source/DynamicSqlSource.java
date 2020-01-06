package com.ygy.learn.mybatis.sql.source;

import com.ygy.learn.mybatis.entity.BoundSql;

/**
 * 动态sqlsource封装 含有${}
 */
public class DynamicSqlSource implements SqlSource{

    @Override
    public BoundSql getBoundSql(Object param) {
        return null;
    }
}
