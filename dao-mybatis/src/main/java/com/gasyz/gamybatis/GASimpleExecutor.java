package com.gasyz.gamybatis;

import com.gasyz.mybatis.user.domain.User;

import java.sql.*;

/**
 * Created by gaoang on 2018/4/9.
 */
public class GASimpleExecutor implements GAExecutor {

    public <T> T query(String statement, String parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User test = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://47.98.133.87:3306/fruit?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "AABd37TdizrlCzlJL8x!");
            preparedStatement = connection.prepareStatement(String.format(statement, Long.parseLong(parameter)));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                test = new User();
                test.setId(rs.getLong(1));
                test.setUsername(rs.getString("username"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (T)test;
    }
}
