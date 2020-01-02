package com.ygy.learn.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:38
 * @Description : mybatis 全部的配置信息，包含mybatis.xml中的信息和其中配置的mapper解析信息
 */
@Getter
@Setter
@ToString
public class Configuration {

    /**
     * 所有配置的mapper.xml的标签信息，key = namespace+id;value = 信息
     */
    private Map<String,MappedStatement> mappedStatements;

}
