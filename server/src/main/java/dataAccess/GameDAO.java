package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear();

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    public void addWhiteUser(int gameID, String username) throws DataAccessException;

    public void addBlackUser(int gameID, String username) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    GameData createGame(GameData game) throws DataAccessException;
}
