package com.lion.dao;

import com.lion.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {

        Connection c = connectionMaker.makeConnection();

        Class.forName("com.mysql.cj.jdbc.Driver");
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id,name,password) values(?,?,?)"); //sql문 템플릿
        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate(); //Mysql workbench에서 ctrl + enter와 유사
        //connection끊기
        ps.close();
        c.close();

    }

    public User findById(String id) throws SQLException {
        Connection c = connectionMaker.makeConnection();


        // Query문 작성
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        pstmt.setString(1, id);

        // Query문 실행
        ResultSet rs = pstmt.executeQuery();

        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"),
                rs.getString("password"));

        rs.close();
        pstmt.close();
        c.close();

        return user;


    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}