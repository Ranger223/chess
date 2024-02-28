package service;

import dataAccess.*;

import model.*;

import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService() {
        userDAO = MemoryUserDAO.getInstance();
        authDAO = MemoryAuthDAO.getInstance();
    }
    public AuthData register(UserData user) throws DataAccessException {
        userDAO.createUser(user);
        AuthData auth = new AuthData(user.getUsername(), UUID.randomUUID().toString());
        return authDAO.createAuth(auth);
    }
    public AuthData login(UserData user) throws DataAccessException {
        if(userDAO.validateUser(user)) {
            AuthData auth = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            return authDAO.createAuth(auth);
        }
        return null;
    }
    public void logout(UserData user) {

    }
    public boolean userExists(UserData user) throws DataAccessException {
        if(userDAO.getUser(user.getUsername()) != null) {
            return true;
        }
        return false;
    }
    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
