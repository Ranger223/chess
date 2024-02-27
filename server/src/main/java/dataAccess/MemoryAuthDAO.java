package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {
    private int nextId = 1;
    final private HashMap<Integer, AuthData> authTokens = new HashMap<>();
    @Override
    public void createAuth(String username) throws DataAccessException {

    }

    @Override
    public AuthData getAuth() throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(AuthData auth) throws DataAccessException {

    }
}
