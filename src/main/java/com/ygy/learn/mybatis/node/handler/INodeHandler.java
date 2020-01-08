package com.ygy.learn.mybatis.node.handler;

import com.ygy.learn.mybatis.node.ISqlNode;
import org.dom4j.Element;

import java.util.List;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 16:41
 * @Description :node处理接口
 */
public interface INodeHandler {
    /**
     * 处理
     * @param node
     * @param nodes
     */
    void handle(Element node, List<ISqlNode> nodes);
}
