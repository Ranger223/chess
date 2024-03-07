package dataAccess;

import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class SqlAuthDAO implements AuthDAO{
    private static SqlAuthDAO authDAO;

    public SqlAuthDAO() {
        try {
            configureDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static SqlAuthDAO getInstance() {
        if(authDAO == null) {
            authDAO = new SqlAuthDAO();
        }
        return authDAO;
    }
    @Override
    public AuthData createAuth(AuthData auth) throws DataAccessException {
        var statement = "INSERT INTO authtokens (username, authtoken) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, auth.getUsername());
                preparedStatement.setString(2, auth.getAuthToken());
                preparedStatement.executeUpdate();
                return auth;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        var statement = "SELECT * FROM authtokens WHERE authtoken=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, authToken);
                try (var rs = preparedStatement.executeQuery()) {
                    if(rs.next()) {
                        var rsUsername = rs.getString("username");
                        var rsAuthToken = rs.getString("authtoken");
                        return new AuthData(rsUsername, rsAuthToken);
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM authtokens WHERE authtoken = ?";
        if(getAuth(authToken) == null) {
            return false;
        }
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        var statement = "DELETE FROM authtokens";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authtokens (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `authtoken` varchar(256) NOT NULL,
              PRIMARY KEY (`id`)
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
