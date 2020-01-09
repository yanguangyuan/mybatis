package com.ygy.learn.mybatis.sql.session;

import com.ygy.learn.mybatis.config.Configuration;
import com.ygy.learn.mybatis.config.MappedStatement;
import com.ygy.learn.mybatis.exception.MyException;
import com.ygy.learn.mybatis.executor.SimpleExecutor;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:08
 * @Description : sql对外接口默认实现
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<Object> list = this.selectList(statementId, param);
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
        if (mappedStatement == null) {
            throw new MyException("No this Statement");
        }
        return new SimpleExecutor().query(mappedStatement, configuration, param);
    }
}
