package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTests {
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
        UserData user = new UserData("test", "pass", "email");
        userDAO.createUser(user);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.clear();
        });
    }

    @Test
    public void testCreateUserPos() throws DataAccessException {
        UserData user = new UserData("test", "pass", "email");

        Assertions.assertDoesNotThrow(() -> {
            userDAO.createUser(user);
        });
    }

    @Test
    public void testCreateUserNeg() throws DataAccessException {
        UserData user = new UserData(null, "pass", "email");
        Assertions.assertThrows(RuntimeException.class, () -> {
            userDAO.createUser(user);
        });
    }

    @Test
    public void testGetUserPos() throws DataAccessException {
        UserData user = new UserData("test", "pass", "email");
        userDAO.createUser(user);

        Assertions.assertNotNull(userDAO.getUser("test"));
    }

    @Test
    public void testGetUserNeg() throws DataAccessException {
        UserData user = new UserData("test", "pass", "email");
        userDAO.createUser(user);
        Assertions.assertNull(userDAO.getUser("yomama"));
    }
}
