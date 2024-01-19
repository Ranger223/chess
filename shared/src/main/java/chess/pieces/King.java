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
        moveForward(board, myPosition, false);
        moveBackward(board, myPosition, false);
        moveLeft(board, myPosition, false);
        moveRight(board, myPosition, false);
        moveForLeft(board, myPosition, false);
        moveForRight(board, myPosition, false);
        moveBackLeft(board, myPosition, false);
        moveBackRight(board, myPosition, false);
        return possibleMoves;
    }

    private void moveForward(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getRow() < 8) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN && myPosition.getRow() == 2) {
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
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }

        }
    }
    private void moveBackward(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getRow() > 1) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN && myPosition.getRow() == 7) {
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
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        }
    }
    private void moveLeft(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getColumn() > 1) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        }
    }
    private void moveRight(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getColumn() < 8) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        }
    }
    private void moveForLeft(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getRow() < 8 && myPosition.getColumn() > 1) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            tempRow++;
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            pawnCheck(board, myPosition, tempPosit);

        }
    }



    private void moveForRight(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getRow() < 8 && myPosition.getColumn() < 8) {
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
        } else {
            tempRow++;
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            pawnCheck(board, myPosition, tempPosit);
        }

    }
    private void moveBackLeft(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getRow() > 1 && myPosition.getColumn() > 1) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            tempRow--;
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            pawnCheck(board, myPosition, tempPosit);
        }
    }
    private void moveBackRight(ChessBoard board, ChessPosition myPosition, boolean cont) {
        ChessPosition tempPosit;
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        if(cont) {
            if(myPosition.getRow() > 1 && myPosition.getColumn() < 8) {
                tempRow = myPosition.getRow();
                tempCol = myPosition.getColumn();
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
        } else {
            tempRow--;
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            pawnCheck(board, myPosition, tempPosit);
        }
    }

    private void pawnCheck(ChessBoard board, ChessPosition myPosition, ChessPosition tempPosit) {
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
            if(board.getPiece(tempPosit) != null) {
                if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosit).getTeamColor()) {
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        }
    }
}
