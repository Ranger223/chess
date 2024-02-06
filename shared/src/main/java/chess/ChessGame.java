package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board = new ChessBoard();
    ChessGame.TeamColor currTurn = TeamColor.WHITE;


    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition){
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        var currPiece = board.getPiece(startPosition);
        if(currPiece == null) {return null;}

        for(ChessMove m : currPiece.pieceMoves(board, startPosition)) {
            if(!isFutureCheck(currPiece.getTeamColor(), m)) {
                validMoves.add(m);
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        var startPosit = move.getStartPosition();
        var endPosit = move.getEndPosition();

        if(board.getPiece(startPosit).getTeamColor() != this.getTeamTurn()) {
            throw new InvalidMoveException();
        }

        var validMoves = validMoves(startPosit);

        if(validMoves.isEmpty()) {
            throw new InvalidMoveException();
        } else {
            if(validMoves.contains(move)) {
                if(board.getPiece(endPosit) == null) {
                    board.addPiece(endPosit, board.getPiece(startPosit));
                    board.removePiece(startPosit);
                    if(this.getTeamTurn() == TeamColor.WHITE) {
                        this.setTeamTurn(TeamColor.BLACK);
                    } else {
                        this.setTeamTurn(TeamColor.WHITE);
                    }
                } else {
                    board.removePiece(endPosit);
                    board.addPiece(endPosit, board.getPiece(startPosit));
                    board.removePiece(startPosit);
                    if(this.getTeamTurn() == TeamColor.WHITE) {
                        this.setTeamTurn(TeamColor.BLACK);
                    } else {
                        this.setTeamTurn(TeamColor.WHITE);
                    }
                }
            } else {
                throw new InvalidMoveException();
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessPosition> enemyMoves = new ArrayList<>();
        ChessPosition kingPosit = null;
        if(teamColor == TeamColor.WHITE) {
            for(int i = 1; i <= 8; i++) {
                for(int j = 1; j <= 8; j++) {
                    ChessPosition currPosit = new ChessPosition(i, j);
                    ChessPiece currPiece = board.getPiece(currPosit);
                    if(currPiece != null) {
                        if(currPiece.getTeamColor() != TeamColor.WHITE) {
                            for(ChessMove m : currPiece.pieceMoves(board, currPosit)) {
                                enemyMoves.add(m.getEndPosition());
                            }
                        }
                        else if(currPiece.getPieceType() == ChessPiece.PieceType.KING) {
                            System.out.println("found white king");
                            kingPosit = new ChessPosition(i, j);
                        }
                    }
                }
            }
            if(kingPosit != null && enemyMoves.contains(kingPosit)) {
                System.out.println("white king in check!");
                return true;
            } else {
                return false;
            }
        } else {

            for(int i = 1; i <= 8; i++) {
                for(int j = 1; j <= 8; j++) {
                    ChessPosition currPosit = new ChessPosition(i, j);
                    ChessPiece currPiece = board.getPiece(currPosit);
                    if(currPiece != null) {
                        if(currPiece.getTeamColor() != TeamColor.BLACK) {
                            for(ChessMove m : currPiece.pieceMoves(board, currPosit)) {
                                enemyMoves.add(m.getEndPosition());
                            }
                        }
                        else if(currPiece.getPieceType() == ChessPiece.PieceType.KING) {
                            System.out.println("found black king");
                            kingPosit = new ChessPosition(i, j);
                        }
                    }
                }
            }
            if(kingPosit != null && enemyMoves.contains(kingPosit)) {
                System.out.println("black king in check!");
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    private boolean isFutureCheck(TeamColor teamColor, ChessMove move){
        ChessBoard tempBoard = board.clone();
        try {
            simulateMove(move);
        } catch (Exception e){
            System.out.println("Failed in future check");
            e.printStackTrace();
        }

        if(isInCheck(teamColor)) {
            board = tempBoard.clone();
            return true;
        } else {
            board = tempBoard.clone();
            return false;
        }
    }

    private void simulateMove(ChessMove move) throws InvalidMoveException {
        var startPosit = move.getStartPosition();
        var endPosit = move.getEndPosition();

        if(board.getPiece(startPosit).getTeamColor() != this.getTeamTurn()) {
            throw new InvalidMoveException();
        }

        if(board.getPiece(endPosit) == null) {
            board.addPiece(endPosit, board.getPiece(startPosit));
            board.removePiece(startPosit);
//            if(this.getTeamTurn() == TeamColor.WHITE) {
//                this.setTeamTurn(TeamColor.BLACK);
//            } else {
//                this.setTeamTurn(TeamColor.WHITE);
//            }
        } else {
            board.removePiece(endPosit);
            board.addPiece(endPosit, board.getPiece(startPosit));
            board.removePiece(startPosit);
//            if(this.getTeamTurn() == TeamColor.WHITE) {
//                this.setTeamTurn(TeamColor.BLACK);
//            } else {
//                this.setTeamTurn(TeamColor.WHITE);
//            }
        }
    }
}
