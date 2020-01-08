package com.ygy.learn.mybatis.config;

import com.ygy.learn.mybatis.node.*;
import com.ygy.learn.mybatis.node.handler.INodeHandler;
import com.ygy.learn.mybatis.sql.source.DynamicSqlSource;
import com.ygy.learn.mybatis.sql.source.RawSqlSource;
import com.ygy.learn.mybatis.sql.source.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 16:36
 * @Description :CRUD标签内容解析
 */
public class XmlScriptBuilder {
    private boolean isDynamic = false;

    private Map<String, INodeHandler> nodeHandlerMap = new HashMap<>(5);

    public XmlScriptBuilder() {
        this.nodeHandlerMap.put("if", new IfNodeHandler());
    }

    public SqlSource parse(Element element) {
        MixedSqlNode rootSqlNode = parseDynamicTags(element);
        if (isDynamic) {
            return new DynamicSqlSource(rootSqlNode);
        } else {
            return new RawSqlSource(rootSqlNode);
        }
    }

    /**
     * 解析sqlnode
     *
     * @param element
     * @return
     */
    private MixedSqlNode parseDynamicTags(Element element) {
        int count = element.nodeCount();
        List<ISqlNode> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Node node = element.node(i);
            //是纯文本
            if (node instanceof Text) {
                String sqlText = node.getText().trim();
                if("".equals(sqlText)){
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if (textSqlNode.isDynamic()) {
                    //设置是否动态为true;
                    isDynamic = true;
                    list.add(new TextSqlNode(sqlText));
                } else {
                    list.add(new StaticTextSqlNode(sqlText));
                }
            } else if (node instanceof Element) {
                INodeHandler nodeHandler = nodeHandlerMap.get(node.getName());
                nodeHandler.handle((Element) node,list);
                isDynamic = true;
            }
        }
        return new MixedSqlNode(list);
    }
    private class IfNodeHandler implements INodeHandler {
        @Override
        public void handle(Element node, List<ISqlNode> nodes) {
            //封装成ifsqlnode;
            String test = node.attributeValue("test");
            MixedSqlNode ifChildren = parseDynamicTags(node);
            nodes.add(new IfSqlNode(test, ifChildren));
        }
    }
}
