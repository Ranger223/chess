package chess;

import chess.pieces.*;

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
    private HashSet<ChessMove> possibleMoves = new HashSet<>();
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

        switch(board.getPiece(myPosition).getPieceType()) {
            case KING:
                King king = new King();
                return king.pieceMoves(board, myPosition);
            case PAWN:
                Pawn p = new Pawn();
                return p.pieceMoves(board, myPosition);
            case ROOK:
                Rook r = new Rook();
                return r.pieceMoves(board, myPosition);
            case QUEEN:
                Queen q = new Queen();
                return q.pieceMoves(board, myPosition);
            case BISHOP:
                Bishop b = new Bishop();
                return b.pieceMoves(board, myPosition);
            case KNIGHT:
                Knight k = new Knight();
                return k.pieceMoves(board, myPosition);
            default:
                //blank
        }

        return possibleMoves;

    }
}
