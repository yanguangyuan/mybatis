package com.ygy.learn.mybatis.executor;

import com.ygy.learn.mybatis.config.Configuration;
import com.ygy.learn.mybatis.config.MappedStatement;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:35
 * @Description : sql 执行器 接口，交由他具体执行sql
 */
public interface Executor {
    /**
     * 查询
     *
     * @param mappedStatement 获取SQL语句和入参出参类型信息
     * @param configuration   获取数据源连接处信息
     * @param param           获取入参类型
     * @return
     */
    <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param);
}
