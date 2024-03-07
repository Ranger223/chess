package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SqlGameDAO implements GameDAO{

    private static SqlGameDAO gameDAO;
    private static ArrayList<GameData> games = new ArrayList<>();

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
        return null;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        return null;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `gameid` int NOT NULL AUTO_INCREMENT,
              `whiteusername` varchar(256),
              `blackusername` varchar(256),
              `gamename` varchar(128) NOT NULL,
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
