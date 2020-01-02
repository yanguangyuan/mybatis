package com.ygy.learn.mybatis.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 14:00
 * @Description :
 */
public class ConnectFactory {

    /**
     * 获取连接
     * @param param
     * @return
     * @throws SQLException
     */
    public static Connection getConnect(ConnectParam param) throws SQLException {
        return DriverManager.getConnection(param.getUrl(), param.getUsername(), param.getPassword());
    }
}
