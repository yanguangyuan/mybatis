package com.ygy.learn.mybatis.sql.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import lombok.AllArgsConstructor;

/**
 * 处理存文本sql
 */
@AllArgsConstructor
public class TextSqlNode implements SqlNode {
    private String sqlText;
    @Override
    public void apply(DynamicContext dynamicContext) {

    }

    /**
     * 判断该文本是否是动态的，${} 需要每次解析，所以是动态的
     * @return
     */
    public boolean isDynamic(){
        return sqlText.contains("${");
    }
}
