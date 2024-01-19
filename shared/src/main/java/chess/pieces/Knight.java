package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Knight implements PieceMovesCalculator{
    private HashSet<ChessMove> possibleMoves = new HashSet<>();
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow+=2;
        tempCol++;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempRow--;
        tempCol++;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempRow -= 2;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempRow--;
        tempCol--;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempCol-=2;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempRow++;
        tempCol--;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempRow+=2;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        tempRow++;
        tempCol++;
        knightMove(board, myPosition, new ChessPosition(tempRow, tempCol));
        return possibleMoves;
    }

    private void knightMove(ChessBoard board, ChessPosition myPosition, ChessPosition tempPosit) {
        if(tempPosit.getRow() <= 8 && tempPosit.getRow() >= 1 && tempPosit.getColumn() <= 8 && tempPosit.getColumn() >= 1) {
            if(board.getPiece(tempPosit) == null || board.getPiece(tempPosit).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        }
    }
}
