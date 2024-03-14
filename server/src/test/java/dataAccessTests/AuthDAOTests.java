package dataAccessTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthDAOTests {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;
    @BeforeEach
    public void setUp() {
        authDAO = new SqlAuthDAO();
        gameDAO = new SqlGameDAO();
        userDAO = new SqlUserDAO();

        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }

    @Test
    public void testClear() throws DataAccessException {
        AuthData auth = new AuthData("test", "token");
        authDAO.createAuth(auth);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.clear();
        });
    }

    @Test
    public void testCreateAuthPos() throws DataAccessException {
        AuthData auth = new AuthData("test", "token");


        Assertions.assertDoesNotThrow(() -> {
            authDAO.createAuth(auth);
        });
    }

    @Test
    public void testCreateAuthNeg() throws DataAccessException {
        AuthData auth = new AuthData(null, "token");

        Assertions.assertThrows(RuntimeException.class, () -> {
            authDAO.createAuth(auth);
        });
    }

    @Test
    public void testGetAuthPos() throws DataAccessException {
        AuthData auth = new AuthData("test", "token");
        authDAO.createAuth(auth);

        Assertions.assertNotNull(authDAO.getAuth("token"));
    }

    @Test
    public void testGetAuthNeg() throws DataAccessException {
        AuthData auth = new AuthData("test", "token");
        authDAO.createAuth(auth);
        Assertions.assertNull(authDAO.getAuth("yomama"));
    }
}
