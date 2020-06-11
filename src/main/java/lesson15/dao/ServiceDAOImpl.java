package lesson15.dao;

import lesson15.products.Service;

import java.sql.Connection;

/**
 * Create 08.06.2020
 *
 * @autor Evtushenko Anton
 */

public class ServiceDAOImpl extends AbstractDAO<Service> {

    public ServiceDAOImpl(Connection connection) {
        super(connection);
    }
}
