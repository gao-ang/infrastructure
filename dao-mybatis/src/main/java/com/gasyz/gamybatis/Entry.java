package com.gasyz.gamybatis;

import com.gasyz.mybatis.user.domain.User;

/**
 * Created by gaoang on 2018/4/9.
 */
public class Entry {
    public static void main(String[] args) {
        GASqlSession gaSqlSession = new GASqlSession(new GAConfiguration(), new GASimpleExecutor());
        UserMapper mapper = gaSqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimaryKey(1l);
        System.out.println(user.toString());
    }
}
