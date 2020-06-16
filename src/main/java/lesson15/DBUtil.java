package lesson15;

import lesson15.products.Product;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@NoArgsConstructor
public class DBUtil {

    public static <P extends Product> boolean dropTable(Connection connection, P type) {

        try (Statement statement = connection.createStatement()
        ) {
            String tableName = type.getClass().getSimpleName().toLowerCase();
            return statement.execute("DROP TABLE IF EXISTS " + tableName);

        } catch (SQLException throwables) {
//              AscasfAfAsczczxc
        }
        return false;
    }

    // этот метод для демонстрации "Batch'ей по заданию"
    public static <P extends Product> String createProductTable(P product) {
        String tableName = product.getClass().getSimpleName().toLowerCase();

            return String.format(
                    "CREATE TABLE %s(\n" +
                    "id SERIAL PRIMARY KEY,\n" +
                    "model VARCHAR(20) NOT NULL,\n" +
                    "price INTEGER NOT NULL,\n" +
                    "manufacturer VARCHAR (20) NOT NULL" +
                    ");", tableName
            );
    }
}
