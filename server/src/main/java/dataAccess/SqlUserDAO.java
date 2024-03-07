package dataAccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.SQLException;


public class SqlUserDAO implements UserDAO{
    private static SqlUserDAO userDAO;

    public SqlUserDAO(){
        try {
            configureDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static SqlUserDAO getInstance() {
        if(userDAO == null) {
            userDAO = new SqlUserDAO();
        }
        return userDAO;
    }
    @Override
    public void createUser(UserData user) throws DataAccessException {
        var statement = "INSERT INTO users (username,password, email) VALUES (?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        var statement = "SELECT * FROM users WHERE username=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                try (var rs = preparedStatement.executeQuery()) {
                    if(rs.next()) {
                        var rsUsername = rs.getString("username");
                        var rsPassword = rs.getString("password");
                        var rsEmail = rs.getString("email");
                        return new UserData(rsUsername, rsPassword, rsEmail);
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean validateUser(UserData user) throws DataAccessException {
        if(user.getPassword().equals(getUser(user.getUsername()).getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        var statement = "DELETE FROM users";
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
            CREATE TABLE IF NOT EXISTS  users (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(username)
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
