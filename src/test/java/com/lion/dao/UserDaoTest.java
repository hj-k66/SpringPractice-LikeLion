package com.lion.dao;

import com.lion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    UserDao userDao;
    User user1;
    User user2;
    User user3;


    @BeforeEach
    void setUp(){
        userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("1","김희정","asdf");
        user2 = new User("2","김희정","123");
        user3 = new User("3","김희정","qwer");
    }
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        User user = new User("0","김희정","apapap");

        userDao.deleteAll();
        Assertions.assertEquals(0,userDao.getCount());


        userDao.add(user);
        Assertions.assertEquals(1,userDao.getCount());

        User selectedUser = userDao.findById(user.getId());
        Assertions.assertEquals(user.getName(), selectedUser.getName());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        Assertions.assertEquals(0,userDao.getCount());

        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        Assertions.assertEquals(3,userDao.getCount());

    }

}