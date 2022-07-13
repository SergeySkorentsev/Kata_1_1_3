package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private static Connection conn = null;
    private static SessionFactory sessionFactory = null;
    public static void connect() {
        try {
            //Class.forName ("com.mysql.cj.jdbc.Driver");
            String userName = "root";
            String password = "qwert12345";
            String url = "jdbc:mysql://localhost:3306/mysql";
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database Connection Established...");
        } catch (ClassCastException e) {
            System.out.println ("ERROR: " + e.getMessage());
        } catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conn;
    }
    public static void close() {
        if (conn != null) {
            try {
                conn.close ();
                System.out.println ("Database connection terminated... ");
            } catch (Exception ex) {
                System.out.println ("Error in connection termination!");
            }
        }
    }

    public static void connectH() {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mysql");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "qwert12345");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperties(settings);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(User.class)
                    .getMetadataBuilder()
                    .build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeH() {
        if(sessionFactory != null) {
            try {
                sessionFactory.close();
                System.out.println ("Database Hibernate connection terminated... ");
            } catch (Exception e) {
                System.out.println ("Error in connection termination!");
            }
        }
    }
}
