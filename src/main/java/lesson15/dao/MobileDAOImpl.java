package lesson15.dao;

import lesson15.products.Mobile;

import java.sql.Connection;

/**
 * Create 08.06.2020
 *
 * @autor Evtushenko Anton
 */

public class MobileDAOImpl extends AbstractDAO<Mobile> {

    public MobileDAOImpl(Connection connection) {
        super(connection);
    }
}
