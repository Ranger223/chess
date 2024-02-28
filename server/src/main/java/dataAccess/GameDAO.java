package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear();

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    GameData createGame(GameData game) throws DataAccessException;
}
