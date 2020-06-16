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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class AccessoryDAOImplTest {
    public final String INSERT_INTO_ACCESSORY = "insert into accessory values(DEFAULT, ?, ?, ?)";
    public final String SELECT_FROM_ACCESSORY = "select * from accessory where id = ?";
    public final String UPDATE_ACCESSORY = "update accessory set model = ?, price = ?, manufacturer = ? where id= ?";
    public final String DELETE_FROM_ACCESSORY = "delete from accessory where id = ?";

    Accessory accessory = new Accessory(32,"easy case", 1200, "China");
    @InjectMocks
    AccessoryDAOImpl accessoryDAO;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void add() throws SQLException {
        when(connection.prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).setString(anyInt(), anyString());

        assertFalse(accessoryDAO.add(accessory));

        verify(connection, times(1)).prepareStatement(INSERT_INTO_ACCESSORY, PreparedStatement.RETURN_GENERATED_KEYS);
        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
        verify(preparedStatement, times(0)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(0)).execute();
    }

    @Test
    void getById() throws SQLException {
        when(connection.prepareStatement(SELECT_FROM_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSetMock);

        //  IllegalArgumentException
        assertThrows(IllegalArgumentException.class, ()->accessoryDAO.getById(new Accessory(2)));

        when(resultSetMock.next()).thenReturn(true);
        //  Equals
        assertEquals(accessory, accessoryDAO.getById(accessory));
        //  NPE
        assertThrows(NullPointerException.class, ()-> accessoryDAO.getById(null));
        //  null
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(accessoryDAO.getById(accessory));
    }

    @Test
    void updateById() throws SQLException {
        when(connection.prepareStatement(UPDATE_ACCESSORY)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenThrow(SQLException.class);

        assertFalse(accessoryDAO.updateById(accessory));

        verify(connection, times(1)).prepareStatement(UPDATE_ACCESSORY);
        verify(preparedStatement, times(2)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    void deleteById() throws SQLException {
        when(connection.prepareStatement(DELETE_FROM_ACCESSORY)).thenReturn(preparedStatement);

        //  NPE
        assertThrows(NullPointerException.class, () -> accessoryDAO.deleteById(null));

        // False
        assertFalse(accessoryDAO.deleteById(accessory));
    }
}