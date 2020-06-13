package lesson15.dao;

import lesson15.products.Product;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.*;

/**
 * Create 08.06.2020
 *
 * @autor Evtushenko Anton
 */

@NoArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractDAO<P extends Product> {
    @NonNull
    Connection cn;

    public boolean add(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "insert into "
                        + getTableName(product)
                        + " values(DEFAULT, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public P getById(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "select * from " + getTableName(product) + " where id = ?")) {
            long id = product.getId();
            ps.setLong(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    product.setModel(resultSet.getString(2));
                    product.setPrice(resultSet.getInt(3));
                    product.setManufacturer(resultSet.getString(4));

                } else
                    throw new IllegalArgumentException(String.format("Продукт %s с таким id не существует", product.getClass().getSimpleName()));
            }
            return product;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean updateById(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "update " + getTableName(product) + " set model = ?, price = ?, manufacturer = ?" +
                        " where id= ?")
        ) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.setInt(4, product.getId());
            ps.execute();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean deleteById(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "delete from " + getTableName(product) + " where id = ?")
        ) {
            ps.setInt(1, product.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private String getTableName(P product) {
        return product.getClass().getSimpleName().toLowerCase();
    }
}