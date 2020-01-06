package com.ygy.learn.mybatis.sql.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import com.ygy.learn.mybatis.utils.OgnlUtils;
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
    private MixedSqlNode sqlNode;
    @Override
    public void apply(DynamicContext dynamicContext) {
        boolean evalute = OgnlUtils.evaluateBoolean(test,dynamicContext.getBindings().get("_parameter"));
        //如果条件判断通过
        if(evalute){
            sqlNode.apply(dynamicContext);
            dynamicContext.appendSql(dynamicContext.getSql());
        }
    }
}
