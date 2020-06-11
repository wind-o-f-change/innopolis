package lesson15.dao;

import lesson15.products.Accessory;

import java.sql.Connection;

/**
 * Create 08.06.2020
 *
 * @autor Evtushenko Anton
 */

public class AccessoryDAOImpl extends AbstractDAO<Accessory> {

    public AccessoryDAOImpl(Connection connection) {
        super(connection);
    }
}
