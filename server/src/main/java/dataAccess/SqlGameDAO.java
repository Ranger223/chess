package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SqlGameDAO implements GameDAO{

    private static SqlGameDAO gameDAO;


    public SqlGameDAO() {
        try {
            configureDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static SqlGameDAO getInstance() {
        if(gameDAO == null) {
            gameDAO = new SqlGameDAO();
        }
        return gameDAO;
    }
    @Override
    public void clear() {
        var statement = "DELETE FROM games";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        var statement = "SELECT * FROM games";
        ArrayList<GameData> games = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        var rsGameID = rs.getInt("gameid");
                        var rsGameName = rs.getString("gamename");
                        var rsWhiteUserName = rs.getString("whiteusername");
                        var rsBlackUserName = rs.getString("blackusername");
                        var rsChessGame = rs.getString("game");
                        GameData game = new GameData(rsGameID, rsGameName, rsWhiteUserName, rsBlackUserName, new Gson().fromJson(rsChessGame, ChessGame.class));
                        games.add(game);
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return games;
    }

    @Override
    public void updateGame(GameData game, int gameID) throws DataAccessException {
        GameData tempGame = getGame(gameID);
        if (tempGame != null) {
            var statement = "UPDATE games SET game=? WHERE gameid=?";
            try (var conn = DatabaseManager.getConnection()) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setObject(1, new Gson().toJson(game.getGame()));
                    preparedStatement.setInt(2, game.getGameID());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new DataAccessException("There is no game with this ID");
        }
    }
    @Override
    public void addWhiteUser(int gameID, String username) throws DataAccessException {
        if(getGame(gameID) == null) {
            throw new DataAccessException("No game exists with that gameID");
        }
        var statement = "UPDATE games SET whiteusername=? WHERE gameid=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBlackUser(int gameID, String username) throws DataAccessException {
        if(getGame(gameID) == null) {
            throw new DataAccessException("No game exists with that gameID");
        }
        var statement = "UPDATE games SET blackusername=? WHERE gameid=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get a GameData object from the SQL database
    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        var statement = "SELECT * FROM games WHERE gameid=?";

        int rsGameID = 0;
        String rsGameName = null;
        String rsWhiteUserName = null;
        String rsBlackUserName = null;
        String rsChessGame = null;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                preparedStatement.setInt(1, gameID);
                try (var rs = preparedStatement.executeQuery()) {
                    if(!rs.next()) {
                        return null;
                    }
                    rs.beforeFirst();
                    while (rs.next()) {
                        rsGameID = rs.getInt("gameid");
                        rsGameName = rs.getString("gamename");
                        rsWhiteUserName = rs.getString("whiteusername");
                        rsBlackUserName = rs.getString("blackusername");
                        rsChessGame = rs.getString("game");
                    }

                    return new GameData(rsGameID, rsGameName, rsWhiteUserName, rsBlackUserName, new Gson().fromJson(rsChessGame, ChessGame.class));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        if(game.getGameName() == null) {
            throw new DataAccessException("No name has been assigned to this game");
        }
        var statement = "INSERT INTO games (gamename, game) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, game.getGameName());
                preparedStatement.setString(2, new Gson().toJson(game.getGame()));
                preparedStatement.executeUpdate();

                try (var result = preparedStatement.getGeneratedKeys()) {
                    if (result.next()) {
                        int newGameID = result.getInt(1);
                        game.setGameID(newGameID);
                    } else {
                        throw new SQLException("No Game ID was generated");
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `gameid` int NOT NULL AUTO_INCREMENT,
              `whiteusername` varchar(256),
              `blackusername` varchar(256),
              `gamename` varchar(128),
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameid`)
            )
            """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}