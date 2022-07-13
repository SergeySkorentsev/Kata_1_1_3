package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args){
        UserDao userService = new UserDaoHibernateImpl();
        Util.connectH();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Vasya", "Pupkin", (byte)25);
        userService.saveUser("Masha", "Kukushkina", (byte)15);
        userService.saveUser("Kolya", "Sidorov", (byte)16);
        userService.saveUser("Olya", "Petrova", (byte)78);
        userService.removeUserById(2);
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
        userService.cleanUsersTable();
        Util.closeH();

//        UserService userService = new UserServiceImpl();
//        Util.connect();
//        userService.createUsersTable();
//        userService.saveUser("Vasya", "Pupkin", (byte)25);
//        userService.saveUser("Masha", "Kukushkina", (byte)15);
//        userService.saveUser("Kolya", "Sidorov", (byte)16);
//        userService.saveUser("Olya", "Petrova", (byte)78);
//        userService.removeUserById(2);
//        List<User> users = userService.getAllUsers();
//        users.forEach(System.out::println);
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
//        Util.close();
    }
}
