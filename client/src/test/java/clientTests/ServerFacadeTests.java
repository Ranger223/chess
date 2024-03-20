package clientTests;

import client.ServerFacade;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.GameData;
import model.JoinData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + Integer.toString(port));
    }

    @BeforeEach
    public void clearDB() throws ResponseException {
        facade.clearDB();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerPos() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        assertTrue(authData.getAuthToken().length() > 10);
    }
    @Test
    public void registerNeg() throws ResponseException {
        facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        Assertions.assertThrows(ResponseException.class, () -> {
            facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        });
    }
    @Test
    public void loginPos() throws ResponseException {
        facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var authData = facade.login(new UserData("player1", "password", null));
        assertTrue(authData.getAuthToken().length() > 10);
    }
    @Test
    public void loginNeg() throws ResponseException {

        Assertions.assertThrows(ResponseException.class, () -> {
            facade.login(new UserData("player1", "password", null));
        });
    }
    @Test
    public void logoutPos() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));

        Assertions.assertDoesNotThrow(() -> {
            facade.logout(authData.getAuthToken());
        });
    }
    @Test
    public void logoutNeg() throws ResponseException {
        facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        Assertions.assertThrows(ResponseException.class, () -> {
            facade.logout("player1");
        });
    }
    @Test
    public void addGamePos() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var game = facade.addGame(new GameData("gamez", null, null), authData.getAuthToken());
        assertTrue(game.getGameID() >= 1);
    }
    @Test
    public void addGameNeg() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        Assertions.assertThrows(ResponseException.class, () -> {
            var game = facade.addGame(new GameData(null, null, null), authData.getAuthToken());
        });
    }
    @Test
    public void listGamePos() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        facade.addGame(new GameData("gamez", null, null), authData.getAuthToken());
        var list = facade.listGames(authData.getAuthToken());
        assertTrue(list.length == 1);
    }
    @Test
    public void listGameNeg() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        facade.logout(authData.getAuthToken());
        Assertions.assertThrows(ResponseException.class, () -> {
            var list = facade.listGames(authData.getAuthToken());
        });
    }
    @Test
    public void joinGamePos() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var game = facade.addGame(new GameData("gamez", null, null), authData.getAuthToken());
        Assertions.assertDoesNotThrow(() -> {

        });
    }
    @Test
    public void joinGameNeg() throws ResponseException {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var game = facade.addGame(new GameData("gamez", null, null), authData.getAuthToken());

        Assertions.assertThrows(ResponseException.class, () -> {
            facade.joinGame(new JoinData(JoinData.Color.WHITE, -1), authData.getAuthToken());
        });
    }


}
