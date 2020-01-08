package com.ygy.learn.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 16:05
 * @Description :处理mapper.xml
 */
@Slf4j
public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    @SuppressWarnings("unchecked")
    public void parse(Element mapperElement) {
        final String namespace = mapperElement.attributeValue("namespace");
        List<Element> selectElements = mapperElement.elements("select");
        for (Element select : selectElements) {
            XmlStatementBuilder statementBuilder = new XmlStatementBuilder(configuration);
            statementBuilder.parse(select,namespace);
        }
    }
}
