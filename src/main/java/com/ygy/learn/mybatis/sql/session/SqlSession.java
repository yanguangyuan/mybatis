package com.ygy.learn.mybatis.sql.session;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:06
 * @Description :对外提供sql操作接口
 */
public interface SqlSession {
    /**
     * 查询单个
     *
     * @param statementId
     * @param param
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId, Object param);

    /**
     * 查询多个
     *
     * @param statementId
     * @param param
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String statementId, Object param);
}
