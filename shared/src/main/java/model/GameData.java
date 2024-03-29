package model;

import chess.ChessGame;



public class GameData {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGame game;

    public GameData() {

    }
    public GameData(int gameID, String gameName, String whiteUsername, String blackUsername, ChessGame game) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.game = game;
    }

    public GameData(String gameName, String whiteUsername, String blackUsername) {
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
