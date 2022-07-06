package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public void createUsersTable() {
        String ddl = "create table if not exists kata.Users(Id bigint not null auto_increment primary key, \n" +
                "Name varchar(32) not null, \n" +
                "LastName varchar(64) not null, \n" +
                "Age smallint(3) not null);";
        if(Util.getConnection() == null) {
            Util.connect();
        }
        try (Statement stat = Util.getConnection().createStatement()) {
            stat.executeUpdate(ddl);
//            System.out.println("Table created");
        } catch(SQLException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("Database connection not established...");
        }
    }

    public void dropUsersTable() {
        String ddl = "drop table if exists kata.Users;";
        if(Util.getConnection() == null) {
            Util.connect();
        }
        try(Statement stat = Util.getConnection().createStatement()) {
            stat.executeUpdate(ddl);
//            System.out.println("Table dropped");
        } catch(SQLException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("Database connection not established...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        //User user = new User(name, lastName, age);
        String dml = "insert into kata.Users (Name, LastName, Age) values (?, ?, ?);";
        if(Util.getConnection() == null) {
            Util.connect();
        }
        try {
            PreparedStatement prepStat = Util.getConnection().prepareStatement(dml);
            prepStat.setString(1, name);
            prepStat.setString(2, lastName);
            prepStat.setByte(3, age);
            prepStat.executeUpdate();
            System.out.println(String.format("User с именем – %s добавлен в базу данных", name));
        } catch(SQLException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("Database connection not established...");
        }
    }

    public void removeUserById(long id) {
        String dml = "delete from kata.Users where Id = ?;";
        if(Util.getConnection() == null) {
            Util.connect();
        }
        try {
            PreparedStatement prepStat = Util.getConnection().prepareStatement(dml);
            prepStat.setLong(1, id);
            prepStat.executeUpdate();
//            System.out.println("Row deleted");
        } catch(SQLException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("Database connection not established...");
        }
    }

    public List<User> getAllUsers() {
        List<User> userLst = new ArrayList<>();
        String dml = "select * from kata.Users;";
        if(Util.getConnection() == null) {
            Util.connect();
        }
        try {
            PreparedStatement prepStat = Util.getConnection().prepareStatement(dml);
            ResultSet res = prepStat.executeQuery();
            while (res.next()) {
                User user = new User(res.getString("name"), res.getString("lastname"), res.getByte("age"));
                user.setId(res.getLong("id"));
                userLst.add(user);
            }
        } catch(SQLException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("Database connection not established...");
        }
        return userLst;
    }

    public void cleanUsersTable() {
        String ddl = "truncate kata.Users;";
        if(Util.getConnection() == null) {
            Util.connect();
        }
        try(Statement stat = Util.getConnection().createStatement()) {
            stat.executeUpdate(ddl);
//            System.out.println("Table truncated");
        } catch(SQLException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("Database connection not established...");
        }
    }
}
