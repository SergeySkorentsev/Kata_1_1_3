package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import java.io.Serializable;
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
            session.createSQLQuery("create table if not exists kata.users(Id bigint not null auto_increment primary key, \n" +
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
            session.createSQLQuery("drop table if exists kata.users;").executeUpdate();
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
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
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
            User user = session.get(User.class, id);
            session.delete(user);
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
            userList = session.createCriteria(User.class).list();
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
            session.createQuery("delete from User").executeUpdate();
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
