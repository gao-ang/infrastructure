package com.gasyz.gamybatis;

import com.gasyz.mybatis.user.domain.User;

/**
 * Created by gaoang on 2018/4/9.
 */
public interface UserMapper {
    User selectByPrimaryKey(Long userId);
}
