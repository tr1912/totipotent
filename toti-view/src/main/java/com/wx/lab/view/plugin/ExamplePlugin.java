package com.wx.lab.view.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * MyBatis 插件
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component
public class ExamplePlugin implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamplePlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        for (Object arg : invocation.getArgs()) {
            System.out.println("参数：" + arg);
        }
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)){
            Object object = invocation.getArgs()[1];
            if (object != null){
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                String tableName = jsonObject.getString("tableName");
                String baseTableName = jsonObject.getString("baseTableName");
                if (!StringUtils.isEmpty(tableName) && !StringUtils.isEmpty(baseTableName)){
                    // 都不为空的情况
//                    MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(),
//                            new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
//                    msObject.setValue("sqlSource.boundSql.sql", mSql);
                    // 获取当前连接执行器

                }
            }
        }




        //0.sql参数获取
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        //1.获取sqlId
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        Configuration configuration = mappedStatement.getConfiguration();

        //获取真实的sql语句
        String sql = getSql(configuration, boundSql, sqlId, 0);
        LOGGER.info(sql);


        Invocation newInvocation = new Invocation(invocation.getTarget(),invocation.getMethod(),invocation.getArgs());
        BeanUtils.copyProperties(invocation, newInvocation);
        Object newParameter = null;
        if (invocation.getArgs().length > 1) {
            newParameter = newInvocation.getArgs()[1];
        }
        MappedStatement newMappedStatement = (MappedStatement)newInvocation.getArgs()[0];
        modify(newMappedStatement,"sqlCommandType",SqlCommandType.SELECT);
        // BoundSql newBoundSql = newMappedStatement.getBoundSql(newParameter);
        BoundSql newBoundSql = newMappedStatement.getSqlSource().getBoundSql(newParameter);
        SqlSource sqlSource = newMappedStatement.getSqlSource();
        SqlNode tsqlNode = new TextSqlNode("SHOW TABLES LIKE 'user_org_002%'");
        List<SqlNode> sqlNodes = new ArrayList<>();
        sqlNodes.add(tsqlNode);
        SqlNode sqlNode = new MixedSqlNode(sqlNodes);
        SqlSource sqlSource1 = new DynamicSqlSource(configuration,sqlNode);
        modify(newBoundSql,"sql","SHOW TABLES LIKE 'user_org_005%'");
        modify(newMappedStatement,"sqlSource",sqlSource1);
        System.out.println(newBoundSql.getSql());
        Object proceed = newInvocation.proceed();
        System.out.println(proceed);

        System.out.println("方法：" + invocation.getMethod());
        System.out.println("目标对象：" + invocation.getTarget());
        Object result = invocation.proceed();

        //只获取第一个数据
        if (result instanceof List){
            System.out.println("原集合数据：" + result);
            System.out.println("只获取第一个对象");
            List list = (List)result;
            return Arrays.asList(list.get(0));
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    private static String getSql(Configuration configuration, BoundSql boundSql,
                                 String sqlId, long time) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId);
        str.append(":");
        str.append(sql);
        return str.toString();
    }

    private static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                MetaObject metaObject = configuration
                        .newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql
                                .getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        sql = sql.replaceFirst("\\?", "缺失");
                    }//打印出缺失，提醒该参数缺失并防止错位
                }
            }
        }
        return sql;
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    private void resetSql(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        BoundSql boundSql = (BoundSql) args[5];
        if(!StringUtils.isEmpty(boundSql.getSql())) {
            modify(boundSql,"sql","");
        }
    }
    private static void modify(Object object, String fieldName, Object newFieldValue){
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            if(!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(object, newFieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
