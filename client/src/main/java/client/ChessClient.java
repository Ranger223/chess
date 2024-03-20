package client;

import exception.ResponseException;
import model.GameData;
import model.JoinData;
import model.UserData;
import ui.*;

import java.util.Arrays;
import java.util.Objects;

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
            if(loggedIn()) {
                return switch (cmd) {
                    case "help" -> help();
                    case "quit" -> "Goodbye!";
                    case "logout" -> logout();
                    case "create" -> createGame(params);
                    case "list" -> listGames();
                    case "join" -> joinGame(params);
                    default -> help();
                };
            } else {
                return switch (cmd) {
                    case "help" -> help();
                    case "quit" -> "Goodbye!";
                    case "register" -> register(params);
                    case "login" -> login(params);
                    default -> help();
                };
            }

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

    public String listGames() throws ResponseException {

        try {
            StringBuilder response = new StringBuilder();
            GameData[] gamesArray = server.listGames(authToken);
            for(GameData game : gamesArray) {
                response.append(game.getGameID()).append(": ").append(game.getGameName()).append("\nPlayers: WHITE[ ").append(game.getWhiteUsername()).append(" ] BLACK[ ").append(game.getBlackUsername()).append(" ]\n\n");
            }
            return response.toString();
        } catch(Exception e) {
            return "Error listing games\n";
        }
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length == 1) {
            GameData game = new GameData(params[0], null, null);
            try {
                GameData temp = server.addGame(game, authToken);
                return String.format("Game %s created! GameID: %d.\n", game.getGameName(), temp.getGameID());
            } catch(Exception e) {
                return "Error creating game\n";
            }
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length == 1 || params.length == 2) {
            JoinData data;
            String color;
            if(params.length == 1) {
                data = new JoinData(null, Integer.parseInt(params[0]));
                color = "observer";
            } else {
                if(Objects.equals(params[1], "black")) {
                    data = new JoinData(JoinData.Color.BLACK, Integer.parseInt(params[0]));
                    color = "black";
                } else if (Objects.equals(params[1], "white")) {
                    data = new JoinData(JoinData.Color.WHITE, Integer.parseInt(params[0]));
                    color = "white";
                } else {
                    throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK|<empty>]");
                }
            }

            try {
                server.joinGame(data, authToken);
                ChessBoardUI.run();
                return String.format("You have joined game %s as %s.\n", params[0], color);

            } catch(Exception e) {
                return "Error joining game\n";
            }
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK|<empty>]");
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
