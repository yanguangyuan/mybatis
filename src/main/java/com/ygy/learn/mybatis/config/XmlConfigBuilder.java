package com.ygy.learn.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ygy.learn.mybatis.exception.MyException;
import com.ygy.learn.mybatis.io.Resources;
import com.ygy.learn.mybatis.utils.DocumentUtil;
import com.ygy.learn.mybatis.utils.FileReadUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 15:37
 * @Description :xml的配置构建
 */
@Slf4j
public class XmlConfigBuilder {
    @Getter
    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) {
        Document document = DocumentUtil.readDocument(inputStream);
        Element root = document.getRootElement();
        parserConfiguration(root);
        return configuration;
    }

    private void parserConfiguration(Element root) {
        Element environments = root.element("environments");
        handleEnvironments(environments);

        Element mappers = root.element("mappers");
        handleMappers(mappers);
    }
    @SuppressWarnings("unchecked")
    private void handleMappers(Element mappers) {
        List<Element> list = mappers.elements("mapper");
        list.forEach(this::handleMapper);
    }

    private void handleMapper(Element mapper) {
        String resource = mapper.attributeValue("resource");
        InputStream inputStream = Resources.getResourcesAsStream(resource);
        Document mapperDocument = DocumentUtil.readDocument(inputStream);
        XmlMapperBuilder mapperBuilder = new XmlMapperBuilder(configuration);
        mapperBuilder.parse(mapperDocument.getRootElement());
    }

    private void handleEnvironments(Element environments) {
        Element environment = environments.element("environment");
        handleDataSource(environment);
    }

    /**
     * 处理datasource
     *
     * @param environment
     */
    @SuppressWarnings("unchecked")
    private void handleDataSource(Element environment) {
        Element dsElement = environment.element("dataSource");
        String type = dsElement.attributeValue("type");
        if ("druid".equals(type)) {
            List<Element> propertyElement = dsElement.elements();
            Properties properties = new Properties();
            int count = propertyElement.size();
            for (int i = 0; i < count; i++) {
                Element node = propertyElement.get(i);
                properties.put(node.attributeValue("name"), node.attributeValue("value"));
            }
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configuration.setDataSource(dataSource);
        } else {
            throw new MyException("Only support Druid DataSource!");
        }
    }
}
