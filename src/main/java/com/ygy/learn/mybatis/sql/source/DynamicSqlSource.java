package com.ygy.learn.mybatis.sql.source;

import com.ygy.learn.mybatis.entity.BoundSql;
import com.ygy.learn.mybatis.sql.DynamicContext;
import com.ygy.learn.mybatis.sql.SqlSourceParser;
import com.ygy.learn.mybatis.node.ISqlNode;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 11:40
 * @Description :动态sqlsource封装 含有${}
 */
public class DynamicSqlSource implements SqlSource{
    private ISqlNode rootSqlNode;

    public DynamicSqlSource(ISqlNode rootSqlNode){
        this.rootSqlNode = rootSqlNode;
    }

    /**
     * 每次进行解析
     * @param param
     * @return
     */
    @Override
    public BoundSql getBoundSql(Object param) {
        //将参数封装进上下文
        DynamicContext dynamicContext = new DynamicContext(param);
        //解析
        rootSqlNode.apply(dynamicContext);
        //第一步解决可${}问题
        String sql = dynamicContext.getSql();
        //解决#{}问题
        SqlSourceParser parser = new SqlSourceParser();
        SqlSource sqlSource = parser.parse(sql);
        return sqlSource.getBoundSql(param);
    }
}
