package lesson15.dao;

import lesson15.products.Product;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create 08.06.2020
 *
 * @autor Evtushenko Anton
 */

@NoArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractDAO<P extends Product> {
    private static Logger LOGGER_DAO = LoggerFactory.getLogger(AccessoryDAOImpl.class);

    @NonNull
    Connection cn;

    public Integer add(P product) {

        try (PreparedStatement ps = cn.prepareStatement(
                "insert into "
                        + getTableName(product)
                        + " values(DEFAULT, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.executeUpdate();

            try (ResultSet generatedKey = ps.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }

        } catch (SQLException e) {
            LOGGER_DAO.error(e + " in 'add()' method");
        }
        return null;
    }

    public P getById(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "select * from " + getTableName(product) + " where id = ?")) {

            ps.setLong(1, product.getId());

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    product.setModel(resultSet.getString(2));
                    product.setPrice(resultSet.getInt(3));
                    product.setManufacturer(resultSet.getString(4));

                } else
                    throw new IllegalArgumentException(String.format("Продукт '%s' с таким id не существует", product.getClass().getSimpleName()));
            }
            return product;

        } catch (SQLException e) {
            LOGGER_DAO.error(e + " in 'getByID()' method");
        } catch (IllegalArgumentException e) {
            LOGGER_DAO.info(e + " in 'getByID()' method");
        }
        return null;
    }

    public Integer updateById(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "update " + getTableName(product) + " set model = ?, price = ?, manufacturer = ?" +
                        " where id= ?")
        ) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.setInt(4, product.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER_DAO.error(e + " in 'updateById()' method");
        }
        return null;
    }

    public Integer deleteById(P product) {
        try (PreparedStatement ps = cn.prepareStatement(
                "delete from " + getTableName(product) + " where id = ?")
        ) {
            ps.setInt(1, product.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER_DAO.error(e + " in 'deleteById()' method");
        }
        return null;
    }

    private String getTableName(P product) {
        return product.getClass().getSimpleName().toLowerCase();
    }
}