package com.ygy.learn.mybatis.sql.source;

import com.ygy.learn.mybatis.config.BoundSql;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:40
 * @Description :
 */
public interface SqlSource {
    /**
     * 获取可执行的sql
     * @param param
     * @return
     */
    BoundSql getBoundSql(Object param);
}
