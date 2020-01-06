package com.ygy.learn.mybatis.sql;

import java.util.HashMap;

/**
 * sql拼接上下文，通过此对象讲解析出来的sqlNode信息拼接到一起
 */
public class DynamicContext {
    private StringBuilder sb = new StringBuilder();
    private HashMap<String,Object> bindings = new HashMap<>();
    public String getSql(){
        return sb.toString();
    }
    public void appendSql(String sql){
        sb.append(sql).append(" ");
    }
    public void addBinding(String name,Object param){
        bindings.put(name,param);
    }
}
