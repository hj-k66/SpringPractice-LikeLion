package com.lion.dao;

import com.lion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao(new AwsConnectionMaker());
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        String id = "16";
        User user = new User(id,"do","okokok");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("do", selectedUser.getName());
    }

}