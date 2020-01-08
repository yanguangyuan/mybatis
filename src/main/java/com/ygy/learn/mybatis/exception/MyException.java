package com.ygy.learn.mybatis.exception;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 15:28
 * @Description :自定义异常
 */
public class MyException extends RuntimeException{

    public MyException(String message){
        super(message);
    }

}
