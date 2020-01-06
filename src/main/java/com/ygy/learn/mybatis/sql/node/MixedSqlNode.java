package com.ygy.learn.mybatis.sql.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 混合sqlNode
 */
@AllArgsConstructor
public class MixedSqlNode implements SqlNode {
    /**
     * 封装接口集合信息，及包含多个或多级节点
     */
    private List<SqlNode> sqlNodes;

    @Override
    public void apply(DynamicContext dynamicContext) {
        sqlNodes.forEach(sqlNode->sqlNode.apply(dynamicContext));
    }
}
