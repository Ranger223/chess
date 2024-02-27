package dataAccess;

import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
    private static ArrayList<AuthData> authTokens = new ArrayList<>();

    private static MemoryAuthDAO authDAO;

    public static MemoryAuthDAO getInstance() {
        if(authDAO == null) {
            authDAO = new MemoryAuthDAO();
        }
        return authDAO;
    }
    @Override
    public AuthData createAuth(AuthData auth) throws DataAccessException {
        authTokens.add(auth);
        return auth;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for(AuthData auth : authTokens) {
            if (auth.getAuthToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) throws DataAccessException {
        for(AuthData auth : authTokens) {
            if (auth.getAuthToken().equals(authToken)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        authTokens.clear();
    }

}
