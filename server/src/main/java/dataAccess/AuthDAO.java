package dataAccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuth(AuthData auth) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    boolean deleteAuth(String authToken) throws DataAccessException;

    void clear();
}
