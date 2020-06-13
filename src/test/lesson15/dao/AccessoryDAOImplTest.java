package lesson15.dao;

import lesson15.products.Accessory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

class AccessoryDAOImplTest {
    @Mock
    private Connection mConn;

//    @Mock
//    private PreparedStatement mPreparedStatement;

    @Mock
    private ResultSet mResultSet;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mConn = mock(Connection.class);
    }

    @Test
    void add() {
        AccessoryDAOImpl accessoryDAO = new AccessoryDAOImpl(mConn);
        assertTrue(accessoryDAO.add(new Accessory("asd",222, "asd")));
    }

    @Test
    void getById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }
}