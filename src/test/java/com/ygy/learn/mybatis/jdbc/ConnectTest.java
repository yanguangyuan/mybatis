package com.ygy.learn.mybatis.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.ygy.learn.mybatis.config.Configuration;
import com.ygy.learn.mybatis.config.XmlConfigBuilder;
import com.ygy.learn.mybatis.entity.*;
import com.ygy.learn.mybatis.io.Resources;
import com.ygy.learn.mybatis.node.*;
import com.ygy.learn.mybatis.sql.source.DynamicSqlSource;
import com.ygy.learn.mybatis.sql.source.RawSqlSource;
import com.ygy.learn.mybatis.sql.source.SqlSource;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 11:40
 * @Description :
 */
@Slf4j
public class ConnectTest {

    private boolean isDynamic;

    @Test
    void test() throws Exception {

    }
    @Test
    void testSql() throws Exception{
        UserDO user = new UserDO();
        user.setId("123");
        user.setUsername("'这是个用户名'");
        XmlConfigBuilder builder = new XmlConfigBuilder();
        builder.parse(Resources.getResourcesAsStream("/mybatis-config.xml"));
        Configuration configuration = builder.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement("com.ygy.learn.mybatis.UserMapper.getById");

        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(user);
        String sql = boundSql.getSql();
        List<ParameterMapping> parameterMappings = boundSql.getParams();
        log.info("sql:{},params:{}",sql,parameterMappings);
        Connection connection = configuration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int length = parameterMappings.size();
        for(int i= 0;i<length;i++){
            String filedName = parameterMappings.get(i).getName();
            Class<?> parameterClass = mappedStatement.getParameterType();
            Field[] fields = parameterClass.getDeclaredFields();
            for(Field field:fields){
                if(field.getName().equals(filedName)){
                    field.setAccessible(true);
                    Object object = field.get(user);
                    preparedStatement.setObject(i+1,object);
                }

            }

        }
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()){
            log.info(set.getString("password"));
        }

    }



}
