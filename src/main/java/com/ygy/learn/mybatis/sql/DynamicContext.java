package com.ygy.learn.mybatis.sql;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * sql拼接上下文，通过此对象讲解析出来的sqlNode信息拼接到一起
 * @author yanguangyuan
 */
@Getter
@Setter
public class DynamicContext {
    private StringBuilder sb = new StringBuilder();
    private HashMap<String,Object> bindings = new HashMap<>();
    public String getSql(){
        return sb.toString();
    }

    /**
     * 将参数封装
     * @param param
     */
    public DynamicContext(Object param){
        bindings.put("_parameter",param);
    }
    public void appendSql(String sql){
        sb.append(sql).append(" ");
    }
    public void addBinding(String name,Object param){
        bindings.put(name,param);
    }
}
