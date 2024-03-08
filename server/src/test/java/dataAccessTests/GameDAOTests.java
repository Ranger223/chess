package dataAccessTests;

import dataAccess.*;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GameDAOTests {
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
    public void testInsertBlackUsernameGood() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Testgame");
        gameDAO.createGame(game);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.addBlackUser(1, "TestUser");
        });
    }

    @Test
    public void testInsertBlackUsernameBad() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Testgame");
        gameDAO.createGame(game);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.addBlackUser(69, "TestUser");
        });
    }

}
