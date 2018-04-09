package com.gasyz.gamybatis;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoang on 2018/4/9.
 */
public class GAConfiguration {

    /**
     * Proxy.newProxyInstance参数解释
     *  classLoader 用来加载.class文件
     * interfaces  生成代理类所含有的方法
     *  h 具体的代理执行者
     */
    public <T> T getMapper(Class<T> clazz,GASqlSession gaSqlSession) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new GAMapperProxy(gaSqlSession));
    }

    /**
     * xml解析好了
     */
    static class UserMapperXml {
        public static final String namespace = "com.gasyz.gamybatis.UserMapper";

        public static final Map<String,String> methodSqlMapping = new HashMap<String, String>();

        static {
            methodSqlMapping.put("selectByPrimaryKey","select * from user where id =%d");
        }
    }
}
