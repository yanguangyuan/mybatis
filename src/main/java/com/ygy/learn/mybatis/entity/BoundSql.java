package com.ygy.learn.mybatis.entity;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:49
 * @Description : 解析的具体sql和参数
 */
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
