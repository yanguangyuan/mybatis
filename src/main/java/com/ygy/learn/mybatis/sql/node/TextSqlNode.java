package com.ygy.learn.mybatis.sql.node;

import com.ygy.learn.mybatis.sql.DynamicContext;
import com.ygy.learn.mybatis.utils.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理存文本sql,包含${}
 */
@AllArgsConstructor
@Slf4j
public class TextSqlNode implements SqlNode {
    private String sqlText;

    /**
     * 处理${}
     * @param dynamicContext 山下文
     */
    @Override
    public void apply(DynamicContext dynamicContext) {
        BindingTokenParser handler = new BindingTokenParser(dynamicContext);
        GenericTokenParser tokenParser = new GenericTokenParser("${","}",handler);
        //真正可执行sql语句
        String sql =tokenParser.parse(sqlText);
        //拼接到上下文中
        dynamicContext.appendSql(sql);

    }

    /**
     * 判断该文本是否是动态的，${} 需要每次解析，所以是动态的
     * @return
     */
    public boolean isDynamic(){
        return sqlText.contains("${");
    }

    private static class BindingTokenParser implements TokenHandler {
        private DynamicContext context;

        public BindingTokenParser(DynamicContext context) {
            this.context = context;
        }

        /**
         * expression：比如说${username}，那么expression就是username username也就是Ognl表达式
         */
        @Override
        public String handleToken(String expression) {
            Object paramObject = context.getBindings().get("_parameter");
            if (paramObject == null) {
                // context.getBindings().put("value", null);
                return "";
            } else if (SimpleTypeRegistry.isSimpleType(paramObject.getClass())) {
                // context.getBindings().put("value", paramObject);
                return String.valueOf(paramObject);
            }
            // 使用Ognl api去获取相应的值
            Object value = OgnlUtils.getValue(expression, paramObject);
            return value == null ? "" : String.valueOf(value);
        }

    }
}
