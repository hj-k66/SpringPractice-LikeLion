package com.lion.dao;

import com.lion.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();

            StatementStrategy strategy = new DeleteAllStrategy();
            ps = strategy.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }



    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            c = connectionMaker.makeConnection();

            ps = c.prepareStatement("SELECT count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

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
        User user = null;
        if(rs.next()){
            user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));
        }

        rs.close();
        pstmt.close();
        c.close();

        if(user==null){
            throw new EmptyResultDataAccessException(1);
        }

        return user;


    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}