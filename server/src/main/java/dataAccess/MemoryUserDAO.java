package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    private int nextId = 1;
    final private ArrayList<UserData> users = new ArrayList<>();
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
}