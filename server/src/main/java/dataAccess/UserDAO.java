package dataAccess;

import model.UserData;

public interface UserDAO {
    void createUser(UserData user) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    boolean validateUser(UserData user) throws DataAccessException;

    void clear();
}
