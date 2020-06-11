package lesson15.dao;

import lesson15.connection.ConnectionManager;
import lesson15.connection.ConnectionManagerJdbcImpl;
import lesson15.products.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create 08.06.2020
 *
 * @autor Evtushenko Anton
 */

public abstract class AbstractDAO<P extends Product> {
    ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    public void add(P product) {
        String tableName = product.getClass().getSimpleName().toLowerCase();
        try (Connection cn = connectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "insert into " + tableName + " values(DEFAULT, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public P getById(P product) {
        try (Connection cn = connectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "select * from " + getTableName(product) + " where id = ?")) {
            long id = product.getId();
            ps.setLong(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    product.setModel(resultSet.getString(2));
                    product.setPrice(resultSet.getInt(3));
                    product.setManufacturer(resultSet.getString(4));

                } else throw new IllegalArgumentException(String.format("Продукт %s с таким id не существует", product.getClass().getSimpleName()));
            }
            return product;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateById(P product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "update " + getTableName(product) + " set model = ?, price = ?, manufacturer = ?" +
                             " where id= ?")
        ) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.setInt(4, product.getId());
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(P product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "delete from " + getTableName(product) + " where id = ?")
        ) {
            ps.setInt(1, product.getId());
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getTableName(P product) {
        return product.getClass().getSimpleName().toLowerCase();
    }
}