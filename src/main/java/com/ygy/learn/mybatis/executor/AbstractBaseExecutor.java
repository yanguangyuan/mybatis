package com.ygy.learn.mybatis.executor;

import com.ygy.learn.mybatis.config.BoundSql;
import com.ygy.learn.mybatis.config.Configuration;
import com.ygy.learn.mybatis.config.MappedStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:39
 * @Description : 基础的抽象执行器
 */
public abstract class AbstractBaseExecutor implements Executor {

    private Map<String, List<Object>> oneLevelCache = new HashMap<>(100);


    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param) {
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(param);
        //判断缓存
        List<Object> results = oneLevelCache.get(boundSql.getSql());
        if (results != null) {
            return (List<T>) results;
        }

        // 查询数据库
        results = queryFromDataBase(mappedStatement, configuration, param, boundSql);

        oneLevelCache.put(boundSql.getSql(), results);
        return (List<T>) results;

    }

    /**
     * 具体查询数据库
     *
     * @param mappedStatement
     * @param configuration
     * @param param
     * @param boundSql
     * @return
     */
    public abstract List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql);

}
