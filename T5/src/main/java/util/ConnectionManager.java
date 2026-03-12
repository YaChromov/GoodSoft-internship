package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (var inputStream = ConnectionManager.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            PROPERTIES.load(inputStream);
            Class.forName(PROPERTIES.getProperty("db.driver"));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации БД", e);
        }
    }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.username"),
                PROPERTIES.getProperty("db.password")
        );
    }
}