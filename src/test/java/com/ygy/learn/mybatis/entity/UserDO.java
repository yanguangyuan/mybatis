package com.ygy.learn.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
* describe: 用户基础表
* @author : yanguangyuan
* @date: 2019-08-30 04:01:36.562
**/
@Getter
@Setter
@ToString
public class UserDO {
    /**
     * 主键 uuid
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 系统
     */
    private Integer system;
    /**
     * 0 未删除 1已删除
     */
    private Boolean deleted;
    /**
     * 0 禁用 1 启用
     */
    private Boolean status;
    /**
     * 上次登陆时间
     */
    private Date last_login_time;
    /**
     * 注册时间
     */
    private Date register_time;
    /**
     * 密码更新时间
     */
    private Date password_update_time;
    /**
     * 数据创建时间
     */
    private Date create_time;
    /**
     * 数据更新时间
     */
    private Date update_time;
    /**
     * 创建人
     */
    private String create_by;
    /**
     * 更新人
     */
    private String update_by;
}