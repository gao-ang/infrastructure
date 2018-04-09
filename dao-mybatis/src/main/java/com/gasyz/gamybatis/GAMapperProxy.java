package com.gasyz.gamybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by gaoang on 2018/4/9.
 */
public class GAMapperProxy implements InvocationHandler {
    private GASqlSession gaSqlSession;

    public GAMapperProxy(GASqlSession gaSqlSession) {
        this.gaSqlSession = gaSqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().getName().equals(GAConfiguration.UserMapperXml.namespace)) {
            String sql = GAConfiguration.UserMapperXml.methodSqlMapping.get(method.getName());
            return gaSqlSession.selectOne(sql,String.valueOf(args[0]));
        }
        return method.invoke(this,args);
    }
}
