package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class Queen implements PieceMovesCalculator{
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

        while(tempRow < 8) {
            tempRow++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
    private void moveBackward(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempRow > 1) {
            tempRow--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
    private void moveLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempCol > 1) {
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
    private void moveRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempCol < 8) {
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
    private void moveForLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempRow < 8 && tempCol > 1) {
            tempRow++;
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }



    private void moveForRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempRow < 8 && tempCol < 8) {
            tempRow++;
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
    private void moveBackLeft(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempRow > 1 && tempCol > 1) {
            tempRow--;
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
    private void moveBackRight(ChessBoard board, ChessPosition myPosition) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();

        while(tempRow > 1 && tempCol < 8) {
            tempRow--;
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
                break;
            }
            possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
        }
    }
}
