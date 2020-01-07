package com.ygy.learn.mybatis.ognl;

import com.ygy.learn.mybatis.entity.UserDO;
import com.ygy.learn.mybatis.utils.OgnlUtils;
import org.junit.jupiter.api.Test;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/7 14:09
 * @Description :
 */
public class OgnlTest {

    @Test
    void test(){
        UserDO userDO = new UserDO();
        userDO.setUsername("用户名");
        System.out.println(OgnlUtils.getValue("username",userDO));
    }
}
