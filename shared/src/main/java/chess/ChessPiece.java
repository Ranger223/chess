package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;
    //Hash set for possible moves
    private HashSet<ChessMove> possibleMoves = new HashSet<ChessMove>();
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type && Objects.equals(possibleMoves, that.possibleMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type, possibleMoves);
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int tempRow = myPosition.getRow();
        int tempCol = myPosition.getColumn();
        ChessPosition tempPosit = new ChessPosition(tempRow, tempCol);

        switch(board.getPiece(myPosition).getPieceType()) {
            case KING:
                moveForward(board, myPosition, false);
                moveBackward(board, myPosition, false);
                moveLeft(board, myPosition, false);
                moveRight(board, myPosition, false);
                moveForLeft(board, myPosition, false);
                moveForRight(board, myPosition, false);
                moveBackLeft(board, myPosition, false);
                moveBackRight(board, myPosition, false);
                break;
            case PAWN:
                moveForward(board, myPosition, false);
                break;
            case ROOK:
                moveForward(board, myPosition, true);
                moveBackward(board, myPosition, true);
                moveLeft(board, myPosition, true);
                moveRight(board, myPosition, true);
                break;
            case QUEEN:
                moveForward(board, myPosition, true);
                moveBackward(board, myPosition, true);
                moveLeft(board, myPosition, true);
                moveRight(board, myPosition, true);
                moveForLeft(board, myPosition, true);
                moveForRight(board, myPosition, true);
                moveBackLeft(board, myPosition, true);
                moveBackRight(board, myPosition, true);
                break;
            case BISHOP:
                moveForRight(board, myPosition, true);
                moveForLeft(board, myPosition, true);
                moveBackLeft(board, myPosition, true);
                moveBackRight(board, myPosition, true);
                break;
            case KNIGHT:
                break;
            case null:
                break;
            default:
                //blank
        }

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
                    if(board.getPiece(tempPosit) != null) {break;}
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            tempRow++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
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
                    if(board.getPiece(tempPosit) != null) {break;}
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            tempRow--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
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
                    if(board.getPiece(tempPosit) != null) {break;}
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
                    if(board.getPiece(tempPosit) != null) {break;}
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
                    if(board.getPiece(tempPosit) != null) {break;}
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            tempRow++;
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
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
                    if(board.getPiece(tempPosit) != null) {break;}
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            tempRow++;
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
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
                    if(board.getPiece(tempPosit) != null) {break;}
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            tempRow--;
            tempCol--;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
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
                    if(board.getPiece(tempPosit) != null) {break;}
                    possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
                }
            }
        } else {
            tempRow--;
            tempCol++;
            tempPosit = new ChessPosition(tempRow, tempCol);
            if(board.getPiece(tempPosit) == null) {
                possibleMoves.add(new ChessMove(myPosition, tempPosit, null));
            }
        }
    }
}
