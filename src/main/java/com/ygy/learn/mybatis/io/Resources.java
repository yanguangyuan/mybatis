package com.ygy.learn.mybatis.io;

import java.io.InputStream;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 14:51
 * @Description :获取资源
 */
public class Resources {
    /**
     * 获取资源路径，将其转为流
     * @param location
     * @return
     */
    public static InputStream getResourcesAsStream(String location){
        return Resources.class.getResourceAsStream(location);
    }
}
