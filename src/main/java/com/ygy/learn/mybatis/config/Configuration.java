package com.ygy.learn.mybatis.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:38
 * @Description : mybatis 全部的配置信息，包含mybatis.xml中的信息和其中配置的mapper解析信息
 */

@ToString
public class Configuration {

    /**
     * 所有配置的mapper.xml的标签信息，key = namespace+id;value = 信息
     */
    @Getter
    private Map<String, MappedStatement> mappedStatements = new HashMap<>(100);
    @Getter
    @Setter
    private DataSource dataSource;

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public void addMappedStatement(MappedStatement mappedStatement){
        mappedStatements.put(mappedStatement.getId(),mappedStatement);
    }
    public MappedStatement getMappedStatement(String id){
        return mappedStatements.get(id);
    }

}
