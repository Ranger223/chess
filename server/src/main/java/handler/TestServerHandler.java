package handler;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.JoinData;
import model.UserData;
import service.DuplicateException;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import service.DuplicateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TestServerHandler {
    public UserService userService = new UserService();
    public GameService gameService = new GameService();
    public Gson gson = new Gson();

    public Object registerHandler(Request req, Response res) {
        UserData user;
        user = gson.fromJson(req.body(), UserData.class);

        try {
            if(userService.userExists(user)) {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            if(user.getPassword() == null || user.getUsername() == null) {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            AuthData authData = userService.register(user);
            res.status(200);
            return gson.toJson(authData);
        } catch (Exception e) {
            System.out.println("Error in register handler!!");
            e.printStackTrace();
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }

    }

    public Object loginHandler(Request req, Response res) {
        UserData user;
        user = gson.fromJson(req.body(), UserData.class);

        try {
            if(!userService.userExists(user)) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            AuthData authData = userService.login(user);
            if(authData == null) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            res.status(200);
            return gson.toJson(authData);
        } catch (Exception e) {
            System.out.println("Error in login handler!!");
            e.printStackTrace();
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

    public Object logoutHandler(Request req, Response res) {
        String token = req.headers("authorization");

        try {
            if(!userService.logout(token)) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            res.status(200);
            return "{}";

        } catch (Exception e) {
            System.out.println("Error in logout handler!!");
            e.printStackTrace();
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

    public Object createGameHandler(Request req, Response res) {
        GameData game = gson.fromJson(req.body(), GameData.class);
        String token = req.headers("authorization");

        try {
            if (!userService.authExists(token)) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            GameData gameData = gameService.createGame(game);
            res.status(200);
            HashMap<String, Integer> response = new HashMap<>();
            response.put("gameID", game.getGameID());
            return gson.toJson(response);
        } catch (Exception e) {
            System.out.println("Error in create game handler!!");
            e.printStackTrace();
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

    public Object listGamesHandler(Request req, Response res) {
        String token = req.headers("authorization");
        try {
            if (!userService.authExists(token)) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            res.status(200);
            HashMap<String, Collection<GameData>> response = new HashMap<>();
            response.put("games", gameService.listAllGames());
            return gson.toJson(response);
        } catch (Exception e) {
            System.out.println("Error in list game handler!!");
            e.printStackTrace();
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

    public Object joinGameHandler(Request req, Response res) {
        JoinData joinData = gson.fromJson(req.body(), JoinData.class);
        String token = req.headers("authorization");

        try {
            if (!userService.authExists(token)) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            if (gameService.getGame(joinData.gameID()) == null) {
                res.status(400);
                return "{ \"message\": \"Error: bad request\" }";
            }
            gameService.updateGame(joinData, token);
            res.status(200);
            return "{}";
        } catch (Exception e) {
            if (e instanceof DuplicateException) {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            } else {
                System.out.println("Error in create game handler!!");
                e.printStackTrace();
                res.status(500);
                return "{ \"message\": \"Error: description\" }";
            }

        }
    }

    public Object clearHandler(Request req, Response res) {
        userService.clear();
        gameService.clear();
        res.status(200);
        return "{}";
    }
}
