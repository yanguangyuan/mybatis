package com.ygy.learn.mybatis.sql.session;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:08
 * @Description :SqlSession 抽象工厂
 */
public interface SqlSessionFactory {
    /**
     * 打开一个sql 会话
     * @return
     */
    SqlSession openSession();
}
