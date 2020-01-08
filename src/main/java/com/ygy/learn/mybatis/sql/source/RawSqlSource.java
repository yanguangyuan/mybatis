package com.ygy.learn.mybatis.sql.source;

import com.ygy.learn.mybatis.entity.BoundSql;
import com.ygy.learn.mybatis.sql.DynamicContext;
import com.ygy.learn.mybatis.sql.SqlSourceParser;
import com.ygy.learn.mybatis.node.MixedSqlNode;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 11:40
 * @Description :动态sqlsource封装 只含有#{}的sql封装
 */
public class RawSqlSource implements SqlSource {
    private SqlSource staticSqlSource;

    public RawSqlSource(MixedSqlNode mixedSqlNode) {
        //处理sqlNode,#{}处理时不需要入参支持，因为他的sql以？站位
        DynamicContext context = new DynamicContext(null);
        mixedSqlNode.apply(context);
        // 通过SqlSourceParser去解析SqlSource中的#{}
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        // 将解析的结果，最终封装成StaticSqlSource
        staticSqlSource = sqlSourceParser.parse(context.getSql());
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return staticSqlSource.getBoundSql(param);
    }
}
