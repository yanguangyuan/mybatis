package com.ygy.learn.mybatis.sql.session;

import com.ygy.learn.mybatis.config.Configuration;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:10
 * @Description :默认的抽象工厂实现
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
