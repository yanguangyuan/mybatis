package com.ygy.learn.mybatis.executor;

import com.ygy.learn.mybatis.config.BoundSql;
import com.ygy.learn.mybatis.config.Configuration;
import com.ygy.learn.mybatis.config.MappedStatement;
import com.ygy.learn.mybatis.config.ParameterMapping;
import com.ygy.learn.mybatis.exception.MyException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:44
 * @Description : 简单的sql执行实现
 */
@Slf4j
public class SimpleExecutor extends AbstractBaseExecutor {
    @Override
    public List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql) {
        List<Object> result = new ArrayList<>(5);
        Connection connection = getConnection(configuration);
        String sql = boundSql.getSql();
        //可能会执行多种statement，现默认prepareStatement;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            parameterize(preparedStatement, mappedStatement, boundSql, param);
            // 执行Statement
            ResultSet resultSet = preparedStatement.executeQuery();
            handleResultSet(mappedStatement, resultSet, result);
            log.info("\nsql:\n{}\nparams:\n{},\nresult:\n{}",sql,param,result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    private void handleResultSet(MappedStatement mappedStatement, ResultSet resultSet, List<Object> results)
            throws Exception {
        Class<?> resultTypeClass = mappedStatement.getResultType();
        // 没遍历一次是一行数据，对应一个映射对象
        while (resultSet.next()) {
            Object result = resultTypeClass.newInstance();
            // 每一列，对应映射对象的一个属性
            // 列的名称要和对象的属性名称一致
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Field field = resultTypeClass.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(result, resultSet.getObject(i + 1));

            }

            results.add(result);
        }

    }

    private void parameterize(PreparedStatement prepareStatement, MappedStatement mappedStatement, BoundSql boundSql,
                              Object param) throws Exception {
        // 先判断入参类型
        Class<?> parameterTypeClass = mappedStatement.getParameterType();
        if (parameterTypeClass == Integer.class) {
            prepareStatement.setObject(1, Integer.parseInt(param.toString()));
        } else if (parameterTypeClass == String.class) {
            prepareStatement.setObject(1, param.toString());
        } else if (parameterTypeClass == Map.class) {

        } else {// 自定义对象类型
            List<ParameterMapping> parameterMappings = boundSql.getParams();

            for (int i = 0; i < parameterMappings.size(); i++) {
                // 获取#{}中的属性名称
                ParameterMapping parameterMapping = parameterMappings.get(i);

                String name = parameterMapping.getName();

                // 根据属性名称，获取入参对象中对应的属性的值
                // 要求#{}中的属性名称和入参对象中的属性名称一致
                Field field = parameterTypeClass.getDeclaredField(name);
                field.setAccessible(true);
                Object value = field.get(param);

                prepareStatement.setObject(i + 1, value);
            }
        }

    }

    /**
     * 获取数据库连接
     *
     * @param configuration
     * @return
     */
    private Connection getConnection(Configuration configuration) {
        try {
            return configuration.getConnection();
        } catch (SQLException e) {
            log.error("Get connection fail,the exception:{}", e.getMessage(), e);
            throw new MyException("Get connection fai");
        }
    }
}
