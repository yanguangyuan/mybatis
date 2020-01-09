package com.ygy.learn.mybatis.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:49
 * @Description : 解析的具体sql和参数
 */
@AllArgsConstructor
@Getter
public class BoundSql {
    /**
     * 具体执行sql
     */
    private String sql;
    /**
     * 执行参数
     */
    private List<ParameterMapping> params;

}
