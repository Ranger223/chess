package dataAccessTests;

import chess.ChessGame;
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
    public void testClear() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.clear();
        });
    }

    @Test
    public void testUpdateGamePos() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        GameData game2 = new GameData();
        game2.setGame(new ChessGame());

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.updateGame(game2, gameDAO.getGame(game.getGameID()).getGameID());
        });
    }

    @Test
    public void testUpdateGameNeg() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        GameData game2 = new GameData();
        game2.setGame(new ChessGame());

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameDAO.updateGame(game2, -1);
        });
    }

    @Test
    public void testAddWhiteUserPos() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.addWhiteUser(gameDAO.getGame(game.getGameID()).getGameID(), "test");
        });
    }

    @Test
    public void testAddWhiteUserNeg() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameDAO.addWhiteUser(66666669, "test");
        });
    }
    @Test
    public void testAddBlackUserPos() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.addBlackUser(gameDAO.getGame(game.getGameID()).getGameID(), "test");
        });
    }

    @Test
    public void testAddBlackUserNeg() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameDAO.addBlackUser(69, "test");
        });
    }

    @Test
    public void testGetGamePos() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        GameData game2 = new GameData();
        game2.setGame(new ChessGame());

        Assertions.assertNotNull(gameDAO.getGame(gameDAO.getGame(game.getGameID()).getGameID()));
    }

    @Test
    public void testGetGameNeg() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");
        gameDAO.createGame(game);

        GameData game2 = new GameData();
        game2.setGame(new ChessGame());

        Assertions.assertNull(gameDAO.getGame(-22));
    }

    @Test
    public void testCreateGamePos() throws DataAccessException {
        GameData game = new GameData();
        game.setGameName("Thegame");

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.createGame(game);
        });
    }

    @Test
    public void testCreateGameNeg() throws DataAccessException {
        GameData game = new GameData();

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameDAO.createGame(game);
        });
    }

}
