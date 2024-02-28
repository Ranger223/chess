package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO {

    private static ArrayList<GameData> games = new ArrayList<>();
    private static MemoryGameDAO gameDAO;
    private int id = 1;

    public static MemoryGameDAO getInstance() {
        if(gameDAO == null) {
            gameDAO = new MemoryGameDAO();
        }
        return gameDAO;
    }
    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        for(GameData g : games) {
            if (g.getGameID() == game.getGameID()) {
                games.remove(g);
                games.add(game);
            }
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for(GameData game : games) {
            if (game.getGameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        game.setGameID(id++);
        games.add(game);
        return game;
    }
}
