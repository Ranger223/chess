package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class King implements PieceMovesCalculator{
    private HashSet<ChessMove> possibleMoves = new HashSet<>();
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moveForward(board, myPosition);
        moveBackward(board, myPosition);
        moveLeft(board, myPosition);
        moveRight(board, myPosition);
        moveForLeft(board, myPosition);
        moveForRight(board, myPosition);
        moveBackLeft(board, myPosition);
        moveBackRight(board, myPosition);
        return possibleMoves;
    }

    private void moveForward(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow++;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveBackward(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow--;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempCol--;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempCol++;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveForLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow++;
        tempCol--;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveForRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow++;
        tempCol++;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveBackLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow--;
        tempCol--;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }
    private void moveBackRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow--;
        tempCol++;
        tempPosit = new ChessPosition(tempRow, tempCol);
        kingMove(board,myPosition,tempPosit);
    }

    private void kingMove(ChessBoard board, ChessPosition myPosition, ChessPosition tempPosit) {
        if(myPosition.getRow() < 8 && myPosition.getRow() > 1 && myPosition.getColumn() < 8 && myPosition.getColumn() > 1) {
            if(board.getPiece(tempPosit) == null || board.getPiece(tempPosit).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        }
    }
}
