package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {

    private static String databaseURL = "jdbc:mysql://localhost:3306/tahwissa";
    private static String user = "root";
    private static String password = "";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static Connection c;

    public MyConnection() {
    }

    public static Connection getInstance() {
        if (c != null) {
            return c;
        }
        try {
            c = DriverManager.getConnection(databaseURL, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
