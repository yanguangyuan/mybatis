package com.ygy.learn.mybatis.sql.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 处理<if></if>
 */
@AllArgsConstructor
public class IfSqlNode implements SqlNode {
    /**
     * if的test判断信息
     */
    private String test;
    /**
     * if子节点信息
     */
    private MixedSqlNode sqlNodes;
    @Override
    public void apply(DynamicContext dynamicContext) {

    }
}
