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

//public abstract class AbstractDAO<P extends Product, D extends AbstractDAO<P, ?>> {
public abstract class AbstractDAO<P extends Product> {
    ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    public boolean add(P product) {
        String tableName = product.getClass().getSimpleName().toLowerCase();
        try (Connection cn = connectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "insert into " + tableName + " values(DEFAULT, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            return ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public P getById(P product) {
        try (Connection cn = connectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "select * from " + getTableName(product) + " where id= ?")) {
            ps.setLong(1, product.getId());

            try (ResultSet resultSet = ps.getResultSet()) {
                if (resultSet.next()) {
                    product.setModel(resultSet.getString(2));
                    product.setPrice(resultSet.getInt(3));
                    product.setPrice(resultSet.getInt(4));
                }
            }
            return product;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean updateById(P product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "update " + getTableName(product) + " set model = ?, price = ?, manufacturer = ?" +
                             " where id= ?")
        ) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.setInt(4, product.getId());
            return ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean deleteMobileById(P product){
        try (Connection connection = connectionManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(
                "delete from "+ getTableName(product) +" where id = ?"
        )) {
            ps.setInt(1, product.getId());
            return ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private String getTableName(P product) {
        String prodName = product.getClass().getSimpleName();
        String tableName = null;

        if (prodName.startsWith("Accessories")) tableName = "accessories";
        else if (prodName.startsWith("Mobile")) tableName = "mobile";
        else if (prodName.startsWith("Service")) tableName = "service";
        else throw new IllegalArgumentException(String.format("Таблицы товара \"%s\" не существует", prodName));
        return tableName;
    }
}
