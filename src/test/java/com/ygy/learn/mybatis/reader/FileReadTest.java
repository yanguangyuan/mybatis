package com.ygy.learn.mybatis.reader;

import com.ygy.learn.mybatis.utils.FileReadUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;


/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 09:51
 * @Description :
 */
@Slf4j
public class FileReadTest {

    @Test
    void readPropertiesTest() throws Exception {
        String path = getClass().getResource("/db.properties").getPath();
        log.info(path);
        System.out.println(FileReadUtil.readProperties(path));
    }

    @Test
    void readXmlTest() throws Exception {
        String path = getClass().getResource("/userMapper.xml").getPath();
        log.info(path);
        Element element = FileReadUtil.readXml(path);
        log.info(element.toString());
    }

}
