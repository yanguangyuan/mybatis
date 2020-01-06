package com.ygy.learn.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:53
 * @Description :参数映射
 */
@Getter
@Setter
@ToString
public class ParameterMapping {
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数类型
     */
    private Class<?> type;
    public ParameterMapping(String context){
        name=context;
    }
}
