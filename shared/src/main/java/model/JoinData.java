package model;


public record JoinData(Color playerColor, int gameID){
    public enum Color {
        WHITE,
        BLACK
    }
}
