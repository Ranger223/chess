package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

    private static ArrayList<GameData> games = new ArrayList<>();
    private static MemoryGameDAO gameDAO;

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

    }

    @Override
    public void createGame(GameData game) throws DataAccessException {
        games.add(game);
    }
}
