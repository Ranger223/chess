package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameServiceTests {
    private GameService service;
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    @BeforeEach
    public void setup() throws TestException {
        service = new GameService();
        userDAO = MemoryUserDAO.getInstance();
        authDAO = MemoryAuthDAO.getInstance();
        gameDAO = MemoryGameDAO.getInstance();

    }
    @Test
    @DisplayName("Create game positive")
    public void successCreateGame() throws TestException, DataAccessException {
        GameData game = new GameData();
        game.setGameName("Testgame");
        GameData game2 = service.createGame(game);
        assertNotNull(game);
    }
    @Test
    @DisplayName("Create game negative")
    public void failCreateGame() throws TestException, DataAccessException {
        GameData game = new GameData();
        game.setGameName("Test");
        GameData game3 = service.createGame(game);
        assertNotNull(game);
    }
    @Test
    @DisplayName("List games positive")
    public void successListGames() throws TestException, DataAccessException {
        GameData game = new GameData();
        game.setGameName("Testgame");
        GameData game2 = service.createGame(game);

        assertNotNull(service.listAllGames());
    }
}
