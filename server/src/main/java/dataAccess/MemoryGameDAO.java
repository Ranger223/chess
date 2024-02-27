package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {
    private int nextId = 1;
    final private ArrayList<GameData> games = new ArrayList<>();
    @Override
    public void clear() throws DataAccessException {
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
