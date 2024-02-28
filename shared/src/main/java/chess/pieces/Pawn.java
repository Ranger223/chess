package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Pawn implements PieceMovesCalculator{
    private HashSet<ChessMove> possibleMoves = new HashSet<>();
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            moveForward(board, myPosition);
            moveForLeft(board, myPosition);
            moveForRight(board, myPosition);
        } else {
            moveBackward(board, myPosition);
            moveBackLeft(board, myPosition);
            moveBackRight(board, myPosition);
        }
        return possibleMoves;
    }

    private void moveForward(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        if(myPosition.getRow() == 2) {
            while(tempRow < 4) {
                tempRow++;
                tempPosit = new ChessPosition(tempRow, tempCol);
                if(board.getPiece(tempPosit) != null) {break;}
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        } else {
            tempRow++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                pawnMoveOne(myPosition, tempPosit);
            }
        }
    }
    private void moveBackward(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        if(myPosition.getRow() == 7) {
            while(tempRow > 5) {
                tempRow--;
                tempPosit = new ChessPosition(tempRow, tempCol);
                if(board.getPiece(tempPosit) != null) {break;}
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        } else {
            tempRow--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                pawnMoveOne(myPosition, tempPosit);
            }
        }
    }
    private void moveForLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow++;
        tempCol--;
        tempPosit = new ChessPosition(tempRow, tempCol);
        inBoundsCheckAndMove(board, myPosition, tempPosit);
    }

    private void inBoundsCheckAndMove(ChessBoard board, ChessPosition myPosition, ChessPosition tempPosit) {
        if(myPosition.getRow() < 8 && myPosition.getRow() > 1 && myPosition.getColumn() < 8 && myPosition.getColumn() > 1) {
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    pawnMoveOne(myPosition, tempPosit);
                }
            }
        }
    }

    private void moveForRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow++;
        tempCol++;
        tempPosit = new ChessPosition(tempRow, tempCol);
        inBoundsCheckAndMove(board, myPosition, tempPosit);
    }
    private void moveBackLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow--;
        tempCol--;
        tempPosit = new ChessPosition(tempRow, tempCol);
        inBoundsCheckAndMove(board, myPosition, tempPosit);
    }
    private void moveBackRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        tempRow--;
        tempCol++;
        tempPosit = new ChessPosition(tempRow, tempCol);
        inBoundsCheckAndMove(board, myPosition, tempPosit);

    }

    private void pawnMoveOne(ChessPosition myPosition, ChessPosition tempPosit) {
        if (tempPosit.getRow() == 8 || tempPosit.getRow() == 1) {
            possibleMoves.add(new ChessMove(myPosition, tempPosit, ChessPiece.PieceType.QUEEN));
            possibleMoves.add(new ChessMove(myPosition, tempPosit, ChessPiece.PieceType.BISHOP));
            possibleMoves.add(new ChessMove(myPosition, tempPosit, ChessPiece.PieceType.ROOK));
            possibleMoves.add(new ChessMove(myPosition, tempPosit, ChessPiece.PieceType.KNIGHT));
        } else {
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
}
