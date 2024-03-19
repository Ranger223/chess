package client;

import exception.ResponseException;
import model.UserData;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;
    private UserData user;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "help" -> help();
                case "quit" -> "quit";
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logout();
//                case "create game" -> createGame(params);
//                case "list games" -> listGames();
//                case "join game" -> joinGame(params);
//                case "join observer" -> joinObserver(params);
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length == 3) {
            user = new UserData(params[0], params[1], params[2]);
            authToken = server.registerUser(user).getAuthToken();
            if(authToken == null) {
                return "That user has already been registered\n";
            } else {
                return String.format("You signed in as %s.", user.getUsername());
            }
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length == 2) {
            user = new UserData(params[0], params[1], null);
            authToken = server.login(user).getAuthToken();
            if(authToken == null) {
                return "Error logging in\n";
            } else {
                return String.format("You signed in as %s.", user.getUsername());
            }
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String logout() throws ResponseException {
        server.logout(authToken);
        authToken = null;
        return "Successfully logged " + user.getUsername() + " out.\n";
    }

    public String help() {
        if(authToken == null) {
            return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to play chess
                quit - playing chess
                help - with possible commands
                """;
        } else {
            return """
                create <NAME> - a game
                list - games
                join <ID> [WHITE|BLACK|<empty>] - a game
                observe <ID> - a game
                logout - when you are done
                quit - playing chess
                help - with possible commands
                """;
        }
    }

    public boolean loggedIn() {
        return authToken != null;
    }
}
