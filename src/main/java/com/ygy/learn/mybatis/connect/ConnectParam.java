package com.ygy.learn.mybatis.connect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 14:04
 * @Description :
 */
@Getter
@Setter
@ToString
public class ConnectParam {
    private String url;
    private String username;
    private String password;
}
