package lesson15;

import lesson15.products.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private DBUtil() {
    }

    public static <T extends Product> boolean renewTable(Connection connection, T type) {

        try (Statement statement = connection.createStatement()
        ) {
            String tableName = type.getClass().getSimpleName().toLowerCase();

            return statement.execute(String.format("\"DROP TABLE IF EXISTS %s;\"\n" +
                    "create table %s (\n) id SERIAL PRIMARY KEY NOT NULL, \n" +
                    "model VARCHAR(20) NOT NULL \n price INTEGER NOT NULL \n" +
                    "manufacturer VARCHAR (20) NOT NULL; \n", tableName, tableName)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    // этот метод для демонстрации "Batch'ей по заданию"
    public static String createProductTable(String name) {

            return String.format(
                    "CREATE TABLE %s(\n" +
                    "id SERIAL PRIMARY KEY,\n" +
                    "model VARCHAR(20) NOT NULL,\n" +
                    "price INTEGER NOT NULL,\n" +
                    "manufacturer VARCHAR (20) NOT NULL" +
                    ");", name
            );
    }
}
