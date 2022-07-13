package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    @Override
    public void createUsersTable() {
        if(Util.getSessionFactory() == null) {
            Util.connectH();
        }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists kata.Users(Id bigint not null auto_increment primary key, \n" +
                    "Name varchar(32) not null, \n" +
                    "LastName varchar(64) not null, \n" +
                    "Age tinyint not null);").executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException | HibernateException e) {
            if (Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSessionFactory().openSession().getTransaction().rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    @Override
    public void dropUsersTable() {
        if(Util.getSessionFactory() == null) {
            Util.connectH();
        }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists kata.Users;").executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException | HibernateException e) {
            if (Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSessionFactory().openSession().getTransaction().rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        if(Util.getSessionFactory() == null) {
            Util.connectH();
        }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("insert into kata.Users (Name, LastName, Age) values (?, ?, ?);");
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println(String.format("User с именем – %s добавлен в базу данных", name));
        } catch (NullPointerException | HibernateException e) {
            if (Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSessionFactory().openSession().getTransaction().rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    @Override
    public void removeUserById(long id) {
        if(Util.getSessionFactory() == null) {
            Util.connectH();
        }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("delete from kata.Users where Id = ?;");
            query.setParameter(1, id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException | HibernateException e) {
            if (Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSessionFactory().openSession().getTransaction().rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        if(Util.getSessionFactory() == null) {
            Util.connectH();
        }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            userList = session.createSQLQuery("select * from kata.users;").addEntity(User.class).list();
            session.getTransaction().commit();
        } catch (ClassCastException e) {
            if (Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSessionFactory().openSession().getTransaction().rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        }
        return userList;
    }
    @Override
    public void cleanUsersTable() {
        if(Util.getSessionFactory() == null) {
            Util.connectH();
        }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("truncate kata.Users;").executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException | HibernateException e) {
            if (Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSessionFactory().openSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSessionFactory().openSession().getTransaction().rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
