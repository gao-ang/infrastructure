package com.gasyz.gamybatis;

/**
 * Created by gaoang on 2018/4/9.
 */
public class GASqlSession {

    private GAConfiguration gaConfiguration;

    private GAExecutor gaExecutor;

    public GASqlSession(GAConfiguration gaConfiguration, GAExecutor gaExecutor) {
        this.gaConfiguration = gaConfiguration;
        this.gaExecutor = gaExecutor;
    }

    public <T> T getMapper(Class<T> clazz) {
        return gaConfiguration.getMapper(clazz,this);
    }

    /**
     *
     * @param statement
     * @param parameter
     * @param <T>
     * @return
     */
    public <T> T selectOne(String statement,String parameter) {
        return gaExecutor.query(statement,parameter);
    }
}
