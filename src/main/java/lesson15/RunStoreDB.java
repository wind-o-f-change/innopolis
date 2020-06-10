package lesson15;

import lesson15.dao.AccessoryDAOImpl;
import lesson15.products.Accessory;
import lesson15.products.Mobile;
import lesson15.products.Service;

import java.sql.*;

/**
 * Create 07.06.2020
 *
 * @autor Evtushenko Anton
 */

public class RunStoreDB {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "4444";

    public static void main(String[] args) throws SQLException {
        // Создание таблиц, применение "Batch'ей" по заданию
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.addBatch(DBUtil.createProductTable( "accessories"));
            statement.addBatch(DBUtil.createProductTable("mobile"));
            statement.executeBatch();

            // "Контролирруемые" коммиты с "rollback"
            connection.setAutoCommit(false);
            statement.executeUpdate(DBUtil.createProductTable("service"));

            Savepoint mySavepoint = connection.setSavepoint();

            statement.executeUpdate(DBUtil.createProductTable("qwerty"));
            connection.rollback(mySavepoint);
            connection.commit();
            connection.setAutoCommit(true);

            Accessory simple = new Accessory("simple", 50, "China");
            Accessory carbon = new Accessory("carbon", 95, "XPhone");

            AccessoryDAOImpl accessoryDAO = new AccessoryDAOImpl();


            Mobile samsung = new Mobile("s20", 2000, "Samsung");
            Mobile xPhone = new Mobile("Xs", 3000, "XPhone");

            Service cleaning = new Service("cleaning", 120, "Co. Modyars");
            Service fix = new Service("fix", 300, "FixPrice)");
        }


    }
}