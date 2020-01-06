package com.ygy.learn.mybatis.sql.source;

import com.ygy.learn.mybatis.entity.BoundSql;
import com.ygy.learn.mybatis.sql.DynamicContext;
import com.ygy.learn.mybatis.sql.node.MixedSqlNode;
import com.ygy.learn.mybatis.utils.GenericTokenParser;
import com.ygy.learn.mybatis.utils.ParameterMappingTokenHandler;

/**
 * 只含有#{}的sql封装
 */
public class RawSqlSource implements SqlSource{
    private StaticSqlSource staticSqlSource;
    public RawSqlSource( MixedSqlNode mixedSqlNode){
        //处理sqlNode,#{}处理时不需要入参支持，因为他的sql以？站位
        DynamicContext context = new DynamicContext(null);
        mixedSqlNode.apply(context);
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{","}",handler);
        //真正可执行sql语句
        String sql =tokenParser.parse(context.getSql());
        //封装到static中
        staticSqlSource = new StaticSqlSource(sql,handler.getParameterMappings());
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return staticSqlSource.getBoundSql(param);
    }
}
