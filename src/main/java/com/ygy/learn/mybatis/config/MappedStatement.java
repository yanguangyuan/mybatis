package com.ygy.learn.mybatis.config;

import com.ygy.learn.mybatis.sql.source.SqlSource;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MappedStatement {
    /**
     * id
     */
    private String id;
    /**
     * 返回类型
     */
    private Class<?> resultType;
    /**
     * 参数
     */
    private Class<?> parameterType;
    /**
     * jdbc执行类型？
     */
    private SqlSource sqlSource;
}
