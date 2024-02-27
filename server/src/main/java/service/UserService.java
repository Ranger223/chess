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
        AuthData auth = new AuthData(UUID.randomUUID().toString(), user.getUsername());
        return authDAO.createAuth(auth);
    }
    public AuthData login(UserData user) {
        return null;
    }
    public void logout(UserData user) {

    }
    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
