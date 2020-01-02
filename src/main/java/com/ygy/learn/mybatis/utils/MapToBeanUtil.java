package com.ygy.learn.mybatis.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 14:17
 * @Description :
 */
public class MapToBeanUtil {
    public static <T> T convert(Map map, Class<T> tClass) throws Exception {
        if (map == null || map.size() == 0) {
            return null;
        }
        T obj = tClass.newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }
}
