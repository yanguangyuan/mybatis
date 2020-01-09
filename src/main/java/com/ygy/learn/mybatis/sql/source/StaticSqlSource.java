package com.ygy.learn.mybatis.sql.source;

import com.ygy.learn.mybatis.config.BoundSql;
import com.ygy.learn.mybatis.config.ParameterMapping;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 已经解析好的sql封装
 */
@AllArgsConstructor
public class StaticSqlSource implements  SqlSource{
    private String sql;
    private List<ParameterMapping> parameterMappings;
    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql,parameterMappings);
    }
}
