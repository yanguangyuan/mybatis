package com.ygy.learn.mybatis.utils;

import com.ygy.learn.mybatis.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 15:25
 * @Description :dom4j读取文档操作
 */
@Slf4j
public class DocumentUtil {
    /**
     * 通过流获取document
     * @param inputStream
     * @return
     */
    public static Document readDocument(InputStream inputStream) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(inputStream);
        } catch (DocumentException e) {
            log.error("read Document fail ,{}",e.getMessage(),e);
            throw new MyException("read Document fail");
        }
    }
}
