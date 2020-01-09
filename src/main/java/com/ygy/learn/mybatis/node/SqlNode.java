package com.ygy.learn.mybatis.node;

import com.ygy.learn.mybatis.sql.DynamicContext;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/2 15:48
 * @Description :
 */
public interface SqlNode {
    /**
     * 执行
     * @param dynamicContext 山下文
     */
    void apply(DynamicContext dynamicContext);
}
