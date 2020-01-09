package com.ygy.learn.mybatis.oo;

import com.ygy.learn.mybatis.entity.UserDO;
import com.ygy.learn.mybatis.io.Resources;
import com.ygy.learn.mybatis.sql.session.SqlSession;
import com.ygy.learn.mybatis.sql.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/9 09:55
 * @Description :
 */
public class OoTest {

    @Test
    void sessionTest() {
        SqlSession sqlSession = new SqlSessionFactoryBuilder(Resources.getResourcesAsStream("/mybatis-config.xml")).build().openSession();
        UserDO query = new UserDO();
        query.setUsername("'admin'");
        sqlSession.selectOne("com.ygy.learn.mybatis.UserMapper.getById", query);
    }
}
