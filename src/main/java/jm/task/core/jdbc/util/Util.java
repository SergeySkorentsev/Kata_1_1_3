package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    private static Connection conn = null;
    public static void connect() {
        try {
            Class.forName ("com.mysql.cj.jdbc.Driver");
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
}
