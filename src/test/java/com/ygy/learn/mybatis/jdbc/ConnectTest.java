package com.ygy.learn.mybatis.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.ygy.learn.mybatis.connect.ConnectFactory;
import com.ygy.learn.mybatis.connect.ConnectParam;
import com.ygy.learn.mybatis.entity.Configuration;
import com.ygy.learn.mybatis.entity.MappedStatement;
import com.ygy.learn.mybatis.sql.node.*;
import com.ygy.learn.mybatis.sql.source.SqlSource;
import com.ygy.learn.mybatis.utils.FileReadUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.junit.jupiter.api.Test;

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
    void test() throws Exception{
        Configuration configuration = loadConfiguration();
        Connection connection = configuration.getConnection();
        String sql = "select * from user where user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, 1);
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
        Element element = FileReadUtil.readXml("/mybatis-home.xml");
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
        List<Element> list = mappers.elements("mapper");
        int count = list.size();
        for(int i =0;i<count;i++){
            String resource =list.get(i).attributeValue("resource");
            log.info(resource);
            Element mapperRoot = FileReadUtil.readXml(resource);
            String namespace = mapperRoot.attributeValue("namespace");
            log.info(namespace);
            List<Element> selectElements = mapperRoot.elements("select");
            for(Element select:selectElements){
                parseStatementElement(configuration,select,namespace);
            }
        }
    }
    private void parseStatementElement(Configuration configuration, Element element, String namespace){
        String id = element.attributeValue("id");
        if(id==null||id.length()==0){
            return;
        }
        //一个CRUD标签对应一个MapperStatement对象
        String statementId = namespace+"."+id;
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveType(parameterType);

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveType(resultType);

        SqlSource sqlSource = createSqlSource(element);

        MappedStatement mappedStatement = new MappedStatement(id,parameterTypeClass,resultTypeClass,sqlSource);
        configuration.addMappedStatement(mappedStatement);
    }

    /**
     * 解析CRUD标签中的sql脚本信息
     * @param element
     * @return
     */
    private SqlSource createSqlSource(Element element) {
        MixedSqlNode mixedSqlNode = parseDynamicTags(element);
        return null;
    }

    /**
     * 解析sqlnode
     * @param element
     * @return
     */
    private MixedSqlNode parseDynamicTags(Element element) {
        int count = element.nodeCount();
        List<SqlNode> list = new ArrayList<>(count);
        for(int i=0;i<count;i++){
            Node node = element.node(i);
            //是纯文本
            if(node instanceof Text){
               String sqlText =  node.getText().trim();
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if(textSqlNode.isDynamic()){
                    //设置是否动态为true;
                    isDynamic = true;
                }else{
                    list.add(new StaticTextSqlNode(sqlText));
                }
            }else if(node instanceof Element){
                String nodeName = node.getName();
                if ("if".equals(nodeName)) {
                    //封装成ifsqlnode;
                    Element ifElement = (Element) node;
                    String test = ifElement.attributeValue("test");
                    MixedSqlNode ifChildren = parseDynamicTags(ifElement);
                    list.add(new IfSqlNode(test,ifChildren));
                }
                isDynamic = true;
            }
        }
        return new MixedSqlNode(list);
    }

    private Class<?> resolveType(String resultType) {
        try {
            return Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
