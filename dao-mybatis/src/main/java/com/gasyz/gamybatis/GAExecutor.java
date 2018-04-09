package com.gasyz.gamybatis;

/**
 * Created by gaoang on 2018/4/9.
 */
public interface GAExecutor {
    <T> T query(String statement, String parameter);
}
