package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    private static MemoryUserDAO userDAO;
    private static ArrayList<UserData> users = new ArrayList<>();

    public static MemoryUserDAO getInstance() {
        if(userDAO == null) {
            userDAO = new MemoryUserDAO();
        }
        return userDAO;
    }
    @Override
    public void createUser(UserData user) throws DataAccessException {
        user = new UserData(user.getUsername(), user.getPassword(), user.getEmail());
        users.add(user);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for(UserData user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
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
        users.clear();
    }

}
