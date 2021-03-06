package com.gasyz.mybatis.mapper;


import com.gasyz.mybatis.user.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    @Select("SELECT * FROM user")
    List<User> selectUser();
}