package lesson15.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerJdbcImpl implements ConnectionManager {

    //    private static final String URL = "jdbc:mysql://localhost:3306/sys?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
//    private static final String URL = "jdbc:mysql://localhost:3306/sys?useUnicode=true&serverTimezone=UTC";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    //    private static final String USERNAME = "root";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "4444";
    public static ConnectionManager INSTANCE;

    private ConnectionManagerJdbcImpl() {
    }

    public static synchronized ConnectionManager getInstance() {
        if(INSTANCE == null)
            INSTANCE = new ConnectionManagerJdbcImpl();
        return INSTANCE;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}