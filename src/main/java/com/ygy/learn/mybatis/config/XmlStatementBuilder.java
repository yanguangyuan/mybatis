package com.ygy.learn.mybatis.config;


import com.ygy.learn.mybatis.sql.source.SqlSource;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;

/**
 * @author : yanguangyuan
 * @Date : 2020/1/8 16:18
 * @Description :解析xml statement
 */
@Slf4j
public class XmlStatementBuilder {
    private Configuration configuration;

    public XmlStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element element, String namespace) {
        String id = element.attributeValue("id");
        if (id == null || id.length() == 0) {
            return;
        }
        //一个CRUD标签对应一个MapperStatement对象
        String statementId = namespace + "." + id;
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveType(parameterType);

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveType(resultType);

        SqlSource sqlSource = createSqlSource(element);

        MappedStatement mappedStatement = new MappedStatement(statementId, parameterTypeClass, resultTypeClass, sqlSource);
        configuration.addMappedStatement(mappedStatement);
    }

    /**
     * 解析CRUD标签中的sql脚本信息,将xmlsql信息封装为对象
     * SqlSource 都是为了对外提供getBoundSql
     * <p>
     * DynamicSqlSource 的SqlNode 解析发生在[每一次]调用getBoundSql时，因为${}需要拼接
     * RawSqlSource 的SqlNode解析发生在第一次调用getBoundSql时 因为#{}都用？站位，最后传入参数就行；
     *
     * @param element
     * @return
     */
    private SqlSource createSqlSource(Element element) {
        XmlScriptBuilder scriptBuilder = new XmlScriptBuilder();
        return scriptBuilder.parse(element);

    }


    private Class<?> resolveType(String resultType) {
        try {
            return Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
