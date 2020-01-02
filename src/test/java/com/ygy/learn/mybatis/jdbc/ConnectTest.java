package com.ygy.learn.mybatis.jdbc;

import com.ygy.learn.mybatis.connect.ConnectFactory;
import com.ygy.learn.mybatis.connect.ConnectParam;
import com.ygy.learn.mybatis.utils.FileReadUtil;
import com.ygy.learn.mybatis.utils.MapToBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 11:40
 * @Description :
 */
@Slf4j
public class ConnectTest {

    private ConnectParam connectParam;

    @BeforeEach
    void init() throws Exception {
        String path = getClass().getResource("/db.properties").getPath();
        connectParam = MapToBeanUtil.convert(FileReadUtil.readProperties(path),ConnectParam.class);
    }

    @Test
    void connectTest() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = ConnectFactory.getConnect(connectParam);
        String sql = "select * from user where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "136a07f8824f4769a3e7593b762d5d48");
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            log.info(set.getString("username"));
        }
        statement.close();
        connection.close();

    }
}
