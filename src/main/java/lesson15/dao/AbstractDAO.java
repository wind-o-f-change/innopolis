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
 * Create by Evtushenko Anton
 */

@NoArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractDAO<P extends Product> {
    private final Logger LOGGER_DAO = LoggerFactory.getLogger(this.getClass());

    @NonNull
    Connection cn;

    public Integer add(P product) throws SQLException {
        String message = " got in 'add()' method";
        LOGGER_DAO.debug(product.toString() + message);

        try (PreparedStatement ps = cn.prepareStatement(
                "insert into "
                        + getTableName(product)
                        + " values(DEFAULT, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            if (ps.executeUpdate() < 1)
                throw new RuntimeException(String.format(
                        "Добавление товара '%s' -> ошибка выполнения!"
                        , getTableName(product)));

            try (ResultSet generatedKeySet = ps.getGeneratedKeys()) {
                if (generatedKeySet.next()) {
                    return generatedKeySet.getInt(1);
                } else throw new RuntimeException("Ошибка генерации 'id' товара");
            }

        } catch (SQLException e) {
            LOGGER_DAO.error(e + message);
            throw e;
        } catch (RuntimeException e) {
            LOGGER_DAO.error(e.getMessage(), e);
            throw e;
        }
    }

    public P getById(P product) throws SQLException {
        String message = " got in 'getById()' method";
        LOGGER_DAO.debug(product.toString());

        try (PreparedStatement ps = cn.prepareStatement(
                "select * from " + getTableName(product) + " where id = ?")) {

            ps.setLong(1, product.getId());

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    product.setModel(resultSet.getString(2));
                    product.setPrice(resultSet.getInt(3));
                    product.setManufacturer(resultSet.getString(4));

                } else
                    throw new IllegalArgumentException(String.format(
                            "Продукт '%s' с таким id не существует!"
                            , getTableName(product)));
            }
            return product;

        } catch (SQLException e) {
            LOGGER_DAO.error(e + message);
            throw e;
        } catch (RuntimeException e) {
            LOGGER_DAO.error(e.getMessage(), e);
            throw e;
        }
    }

    public void updateById(P product) throws SQLException {
        String message = " got in 'updateById()' method";
        LOGGER_DAO.debug(product.toString() + message);

        try (PreparedStatement ps = cn.prepareStatement(
                "update " + getTableName(product) + " set model = ?, price = ?, manufacturer = ?" +
                        " where id= ?")
        ) {
            ps.setString(1, product.getModel());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getManufacturer());
            ps.setInt(4, product.getId());

            if (ps.executeUpdate() < 1)
                throw new RuntimeException(String.format(
                        "Заданные параменты товара '%s' не изменены! Возможно они совпадают или не найдены."
                        , getTableName(product)));

        } catch (SQLException e) {
            LOGGER_DAO.error(e + message);
            throw e;
        } catch (RuntimeException e) {
            LOGGER_DAO.error(e.getMessage(), e);
            throw e;
        }
    }

    public void deleteById(P product) throws SQLException {
        String message = " got in 'deleteById()' method";
        LOGGER_DAO.debug(product.toString() + message);

        try (PreparedStatement ps = cn.prepareStatement(
                "delete from " + getTableName(product) + " where id = ?")
        ) {
            ps.setInt(1, product.getId());
            if (ps.executeUpdate() < 1)
                throw new RuntimeException(String.format(
                        "Удаление товара '%s' -> ошибка выполнения!"
                        , getTableName(product)));

        } catch (SQLException e) {
            LOGGER_DAO.error(e + message);
            throw e;
        } catch (RuntimeException e) {
            LOGGER_DAO.error(e.getMessage(), e);
            throw e;
        }
    }

    private String getTableName(P product) {
        return product.getClass().getSimpleName().toLowerCase();
    }
}