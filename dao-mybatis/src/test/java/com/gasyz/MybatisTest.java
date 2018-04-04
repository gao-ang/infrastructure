package com.gasyz;

import com.gasyz.mybatis.mapper.UserMapper;
import com.gasyz.mybatis.user.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MybatisTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        System.out.println(111);
    }

    @Test
    public void test1() throws IOException {
        String resource = "spring-db.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectByPrimaryKey(1l);
        } finally {
            session.close();
        }
        System.out.println(111);
    }

    @Test
    public void test2() {
        User user = new User();
        user.setUsername("gaoang");
        user.setPassword("123");
        user.setGmtCreate(new Date());
        userMapper.insert(user);
        User user1 = userMapper.selectByPrimaryKey(1l);
        System.out.println(user1);
    }
}
