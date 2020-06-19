package lesson15.dao;

import lesson15.products.Accessory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class AccessoryDAOImplTest {
    public final String INSERT_INTO_ACCESSORY = "insert into accessory values(DEFAULT, ?, ?, ?)";
    public final String SELECT_FROM_ACCESSORY = "select * from accessory where id = ?";
    public final String UPDATE_ACCESSORY = "update accessory set model = ?, price = ?, manufacturer = ? where id= ?";
    public final String DELETE_FROM_ACCESSORY = "delete from accessory where id = ?";

    Accessory accessory = new Accessory(32, "easy case", 1200, "China");
    @InjectMocks
    AccessoryDAOImpl accessoryDAO;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSetMock;

    @Test
    void add() throws SQLException {
        when(connection.prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(12);

        assertTrue(accessoryDAO.add(new Accessory("easy case", 1200, "China")) > 0);
    }

    @Test
    void add_NPE() {
        assertThrows(NullPointerException.class, () -> accessoryDAO.add(null));
    }

    @Test
    void addErr() throws SQLException {
        when(connection.prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(RuntimeException.class, () -> accessoryDAO.add(accessory)
                , "Добавление товара '%s' -> ошибка выполнения!");
    }

    @Test
    void add_GenIdErr() throws SQLException {
        when(connection.prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);

        assertThrows(RuntimeException.class, () -> accessoryDAO.add(accessory)
                , "Ошибка генерации 'id' товара");
    }

    @Test
    void add_SQLException() throws SQLException {
        when(connection.prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).setString(anyInt(), anyString());

        assertThrows(SQLException.class, () -> accessoryDAO.add(accessory));

        verify(connection, times(1)).prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS);
        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
        verify(preparedStatement, times(0)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(0)).executeUpdate();
    }

    @Test
    void getById() throws SQLException {
        when(connection.prepareStatement(SELECT_FROM_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);

        assertEquals(accessory, accessoryDAO.getById(accessory));
    }

    @Test
    void getById_NPE() {
        assertThrows(NullPointerException.class, () -> accessoryDAO.getById(null));
    }

    @Test
    void getById_SQLException() throws SQLException {
        when(connection.prepareStatement(SELECT_FROM_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> accessoryDAO.getById(accessory));
    }

    @Test
    void getById_IllegalArgumentException() throws SQLException {
        when(connection.prepareStatement(SELECT_FROM_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSetMock);

        assertThrows(IllegalArgumentException.class, () -> accessoryDAO.getById(accessory)
                , "Продукт 'accessory' с таким id не существует!");
    }

    @Test
    void updateById() throws SQLException {
        when(connection.prepareStatement(UPDATE_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> accessoryDAO.updateById(accessory));

        verify(connection, times(1)).prepareStatement(UPDATE_ACCESSORY);
        verify(preparedStatement, times(2)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void updateById_NPE() {
        assertThrows(NullPointerException.class, () -> accessoryDAO.updateById(null));
    }

    @Test
    void updateById_RuntimeException() throws SQLException {
        when(connection.prepareStatement(UPDATE_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(RuntimeException.class, () -> accessoryDAO.updateById(accessory)
                , "Заданные параменты товара 'accessory' не изменены! Возможно они совпадают или не найдены.");
    }

    @Test
    void deleteById() throws SQLException {
        when(connection.prepareStatement(DELETE_FROM_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> accessoryDAO.deleteById(accessory));
    }

    @Test
    void deleteById_NPE() {
        assertThrows(NullPointerException.class, () -> accessoryDAO.deleteById(null));
    }

    @Test
    void deleteById_RuntimeException() throws SQLException {
        when(connection.prepareStatement(DELETE_FROM_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(RuntimeException.class, () -> accessoryDAO.deleteById(accessory)
                ,"Удаление товара 'accessory' -> ошибка выполнения!");
    }

    @Test
    void deleteById_SQLException() throws SQLException {
        when(connection.prepareStatement(DELETE_FROM_ACCESSORY)).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).setInt(anyInt(), anyInt());

        assertThrows(SQLException.class, () -> accessoryDAO.deleteById(accessory));
    }
}