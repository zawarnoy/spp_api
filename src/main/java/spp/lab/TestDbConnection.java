package spp.lab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class TestDbConnection {

    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Properties properties;

    public static void main(String[] args) {

        try {
            Class.forName(DATABASE_DRIVER);
            Connection connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
        }
        return properties;
    }
}
