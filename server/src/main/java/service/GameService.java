package service;

import dataAccess.*;
import model.GameData;
import model.JoinData;


import java.util.Collection;

public class GameService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService() {
        userDAO = SqlUserDAO.getInstance();
        authDAO = SqlAuthDAO.getInstance();
        gameDAO = SqlGameDAO.getInstance();
    }

    public GameData createGame(GameData game) throws DataAccessException {
        return gameDAO.createGame(game);
    }

    public Collection<GameData> listAllGames() throws DataAccessException {
        return gameDAO.listGames();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }

    public void updateGame(JoinData joinData, String token) throws DataAccessException, DuplicateException {
        GameData game = gameDAO.getGame(joinData.gameID());

        if (joinData.playerColor() == JoinData.Color.WHITE && game.getWhiteUsername() == null) {
            game.setWhiteUsername(authDAO.getAuth(token).getUsername());
            gameDAO.addWhiteUser(game.getGameID(), game.getWhiteUsername());
        } else if (joinData.playerColor() == JoinData.Color.BLACK && game.getBlackUsername() == null) {
            game.setBlackUsername(authDAO.getAuth(token).getUsername());
            gameDAO.addBlackUser(game.getGameID(), game.getBlackUsername());
        } else if (joinData.playerColor() == null) {

        } else {
            throw new DuplicateException("This team color is already assigned");
        }
    }

    public void clear() {
        gameDAO.clear();
    }
}
