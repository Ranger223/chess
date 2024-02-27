package dataAccess;

import model.AuthData;

public interface AuthDAO {
    void createAuth(String username) throws DataAccessException;

    AuthData getAuth() throws DataAccessException;

    void deleteAuth(AuthData auth) throws DataAccessException;
}
