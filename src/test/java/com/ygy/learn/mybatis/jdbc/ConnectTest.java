package com.ygy.learn.mybatis.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.ygy.learn.mybatis.connect.ConnectFactory;
import com.ygy.learn.mybatis.connect.ConnectParam;
import com.ygy.learn.mybatis.entity.Configuration;
import com.ygy.learn.mybatis.utils.FileReadUtil;
import com.ygy.learn.mybatis.utils.MapToBeanUtil;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
        connectParam = MapToBeanUtil.convert(FileReadUtil.readProperties(path), ConnectParam.class);
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

    @Test
    void test() throws Exception{
        Configuration configuration = loadConfiguration();
        Connection connection = configuration.getConnection();
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

    /**
     * 加载配置
     *
     * @return
     */
    private Configuration loadConfiguration() {
        Configuration configuration = new Configuration();
        //读取xml配置
        String path = getClass().getResource("/mybatis-config.xml").getPath();
        Element element = FileReadUtil.readXml(path);
        Element environments = element.element("environments");
        handleEnvironments(configuration, environments);
        Element mappers = element.element("mappers");
        handleMappers(configuration, mappers);
        return configuration;
    }

    /**
     * 处理环境参数
     *
     * @param configuration
     * @param environments
     */
    private void handleEnvironments(Configuration configuration, Element environments) {
        Element environment = environments.element("environment");
        handleDataSource(configuration, environment);
    }

    /**
     * 处理datasource
     *
     * @param configuration
     * @param environment
     */
    private void handleDataSource(Configuration configuration, Element environment) {
        Element dsElement = environment.element("dataSource");
        String type = dsElement.attributeValue("type");

        String driver = "";
        String url = "";
        String username = "";
        String password = "";
        if ("druid".equals(type)) {
            List<Element> properties = dsElement.elements();
            int count = properties.size();
            for (int i = 0; i < count; i++) {
                Element node =  properties.get(i);
                String name = node.attributeValue("name");
                String value = node.attributeValue("value");
                log.info(name+"--"+value);
                if ("driver".equals(name)) {
                    driver = value;
                } else if ("url".equals(name)) {
                    url = value;
                } else if ("username".equals(name)) {
                    username=value;
                } else if ("password".equals(name)) {
                    password=value;
                }
            }
            DruidDataSource dataSource =new DruidDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            configuration.setDataSource(dataSource);
        }
    }

    /**
     * 处理mapper
     *
     * @param configuration
     * @param mappers
     */
    private void handleMappers(Configuration configuration, Element mappers) {
    }

}
