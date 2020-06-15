package lesson15;

import lesson15.dao.AccessoryDAOImpl;
import lesson15.dao.MobileDAOImpl;
import lesson15.dao.ServiceDAOImpl;
import lesson15.products.*;

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

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Удаление таблиц по необходимости повторного теста
            DBUtil.dropTable(connection, new Accessory());
            DBUtil.dropTable(connection, new Mobile());
            DBUtil.dropTable(connection, new Service());

            //  Создание 2х таблиц c применением "Batch'ей"
            statement.addBatch(DBUtil.createProductTable(new Accessory()));
            statement.addBatch(DBUtil.createProductTable(new Mobile()));
            statement.executeBatch();
            //  "Контролирруемые" коммиты с спользованием "rollback"
            System.out.println("AutoCommit is: "+ connection.getAutoCommit());
            connection.setAutoCommit(false);
            statement.executeUpdate(DBUtil.createProductTable(new Service()));

            Savepoint mySavepoint = connection.setSavepoint();

            statement.executeUpdate(DBUtil.createProductTable(new Somethings()));
            connection.rollback(mySavepoint);
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("AutoCommit is: "+ connection.getAutoCommit());

            //  operations whit a table
            AccessoryDAOImpl accessoryDAO = new AccessoryDAOImpl(connection);
            accessoryDAO.add(new Accessory("simple case", 50, "China"));
            accessoryDAO.add(new Accessory("carbon case", 95, "XPhone"));


            MobileDAOImpl mobileDAO = new MobileDAOImpl(connection);
            mobileDAO.add(new Mobile("s20", 2000, "Samsung"));
            mobileDAO.add(new Mobile("Xs", 3000, "XPhone"));

            ServiceDAOImpl serviceDAO = new ServiceDAOImpl(connection);
            serviceDAO.add(new Service("cleaning", 120, "Co. Modyars"));
            serviceDAO.add(new Service("fix", 300, "IT Fix"));
            //  read
            System.out.println(accessoryDAO.getById(new Accessory(2)));
            System.out.println(mobileDAO.getById(new Mobile(1)));
            System.out.println(serviceDAO.getById(new Service(1)));
            System.out.println(serviceDAO.getById(new Service(2)));
            //  update
            serviceDAO.updateById(new Service(1, "repairs", 320, "ИП\' Казбек & IT"));
            //  delete
            serviceDAO.deleteById(new Service(2));
            System.out.println(serviceDAO.getById(new Service(1)));
            // бросает IllegalArgumentException
            System.out.println(serviceDAO.getById(new Service(2)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //изменение
        }
    }
}