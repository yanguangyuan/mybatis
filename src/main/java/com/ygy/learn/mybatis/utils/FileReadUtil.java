package com.ygy.learn.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 14:52
 * @Description :
 */
@Slf4j
public class FileReadUtil {
    /**
     * 读取properties文件 转为Properties 对象
     *
     * @param path
     * @return
     */
    public static Properties readProperties(String path) {
        Properties result = new Properties();
        try {
            result.load(FileReadUtil.class.getResourceAsStream(path));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("读取文件异常", e);
        }
        return result;
    }

    public static Element readXml(String path){
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(FileReadUtil.class.getResourceAsStream(path));
            return document.getRootElement();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("读取文件异常", e);
        }

    }


}
