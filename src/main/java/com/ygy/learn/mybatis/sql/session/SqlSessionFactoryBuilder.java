package com.ygy.learn.mybatis.sql.session;

import com.ygy.learn.mybatis.config.Configuration;
import com.ygy.learn.mybatis.config.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:22
 * @Description : 工厂构建
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder(InputStream inputStream){
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        this.configuration = xmlConfigBuilder.parse(inputStream);
    }
    public SqlSessionFactory build(){
        return new DefaultSqlSessionFactory(configuration);
    }
}
