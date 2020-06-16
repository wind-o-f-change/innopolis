package lesson15;

import lesson15.dao.AccessoryDAOImpl;
import lesson15.dao.MobileDAOImpl;
import lesson15.dao.ServiceDAOImpl;
import lesson15.products.Accessory;
import lesson15.products.Mobile;
import lesson15.products.Service;
import lesson15.products.Somethings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Create Evtushenko Anton
 */

public class RunStoreDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "4444";

    public static void main(String[] args) {

        Logger DB_LOGGER = LogManager.getLogger("RunStoreDB");
        Logger logger = LogManager.getLogger("example_logger");
        Logger logger2 = LogManager.getLogger("logger2");


        DB_LOGGER.trace("it's trace     DB_LOGGER");
        DB_LOGGER.debug("it's debug     DB_LOGGER");
        DB_LOGGER.info("it's info       DB_LOGGER");
        DB_LOGGER.warn("it's warn       DB_LOGGER");
        DB_LOGGER.error("it's error     DB_LOGGER");
        DB_LOGGER.fatal("it's fatal     DB_LOGGER");

        logger.trace("it's trac     1_logger_1");
        logger.debug("it's debug    1_logger_1");
        logger.info("it's info      1_logger_1");
        logger.warn("it's warn      1_logger_1");
        logger.error("it's error    1_logger_1");
        logger.fatal("it's fatal    1_logger_1");

        logger2.trace("it's trace   2_logger_2");
        logger2.debug("it's debug   2_logger_2");
        logger2.info("it's info     2_logger_2");
        logger2.warn("it's warn     2_logger_2");
        logger2.error("it's error   2_logger_2");
        logger2.fatal("it's fatal   2_logger_2");

//
//        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//             Statement statement = connection.createStatement()) {
//
//            // Удаление таблиц по необходимости повторного теста
//            DBUtil.dropTable(connection, new Accessory());
//            DBUtil.dropTable(connection, new Mobile());
//            DBUtil.dropTable(connection, new Service());
//
//            //  Создание 2х таблиц c применением "Batch'ей"
//            statement.addBatch(DBUtil.createProductTable(new Accessory()));
//            statement.addBatch(DBUtil.createProductTable(new Mobile()));
//            statement.executeBatch();
//            //  "Контролирруемые" коммиты с спользованием "rollback"
//            DB_LOGGER.info("AutoCommit is: " + connection.getAutoCommit());
//            connection.setAutoCommit(false);
//            statement.executeUpdate(DBUtil.createProductTable(new Service()));
//
//            Savepoint mySavepoint = connection.setSavepoint();
//
//            statement.executeUpdate(DBUtil.createProductTable(new Somethings()));
//            connection.rollback(mySavepoint);
//            connection.commit();
//            connection.setAutoCommit(true);
//            DB_LOGGER.info("AutoCommit is: " + connection.getAutoCommit());
//
//            //  operations whit a table
//            AccessoryDAOImpl accessoryDAO = new AccessoryDAOImpl(connection);
//            accessoryDAO.add(new Accessory("simple case", 50, "China"));
//            accessoryDAO.add(new Accessory("carbon case", 95, "XPhone"));
//
//
//            MobileDAOImpl mobileDAO = new MobileDAOImpl(connection);
//            mobileDAO.add(new Mobile("s20", 2000, "Samsung"));
//            mobileDAO.add(new Mobile("Xs", 3000, "XPhone"));
//
//            ServiceDAOImpl serviceDAO = new ServiceDAOImpl(connection);
//            serviceDAO.add(new Service("cleaning", 120, "Co. Modyars"));
//            serviceDAO.add(new Service("fix", 300, "IT Fix"));
//            //  read
//            DB_LOGGER.info(accessoryDAO.getById(new Accessory(2)).toString());
//            DB_LOGGER.trace(mobileDAO.getById(new Mobile(1)).toString());
//            DB_LOGGER.warn(serviceDAO.getById(new Service(1)).toString());
//            DB_LOGGER.debug(serviceDAO.getById(new Service(2)).toString());
//            //  update
//            serviceDAO.updateById(new Service(1, "repairs", 320, "ИП\' Казбек & IT"));
//            //  delete
//            serviceDAO.deleteById(new Service(2));
//            DB_LOGGER.error(serviceDAO.getById(new Service(1)).toString());
//            // бросает IllegalArgumentException
//            DB_LOGGER.info(serviceDAO.getById(new Service(2)).toString());
//
//        } catch (SQLException throwables) {
//            DB_LOGGER.error(throwables.toString());
//        }
    }
}