package com.ygy.learn.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:40
 * @Description :mapper.xml解析信息，针对每一个标签信息
 */
@Getter
@Setter
@ToString
public class MappedStatement {
    /**
     * id
     */
    private String id;
    /**
     * 返回类型
     */
    private Class resultType;
    /**
     * 参数
     */
    private Class parameterType;
    /**
     * 标签值
     */
    private String value;
    /**
     * jdbc执行类型？
     */
    private SqlSource sqlSource;
}
