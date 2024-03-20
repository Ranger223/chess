package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String[][] initialBoard = {
            {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK},
            {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN},
            {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}
    };


    public static void run() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeader(out, true);
        drawChessBoard(out, true);
        drawHeader(out, true);

        drawHeader(out, false);
        drawChessBoard(out, false);
        drawHeader(out, false);

        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
        System.out.print("\u001b[0m");
    }

    private static void drawHeader(PrintStream out, boolean white) {
        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
        for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {
            if (boardCol != 0 && boardCol != BOARD_SIZE_IN_SQUARES + 1) {
                if (white) {
                    printHeaderText(out, headers[boardCol - 1]);
                } else {
                    printHeaderText(out, headers[8 - boardCol]);
                }


            } else {
                printHeaderText(out, " ");
            }
            setBlack(out);
        }
        out.println();
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(" " + player + " ");

        setWhite(out);
    }

    private static void drawChessBoard(PrintStream out, boolean white) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out, boardRow % 2 == 0, boardRow, white);

        }
    }

    private static void drawRowOfSquares(PrintStream out, boolean inverted, int row, boolean white) {

        for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {

            if (boardCol == 0 || boardCol == BOARD_SIZE_IN_SQUARES + 1) {
                setGray(out);
                int size = 8;
                if (white) {
                    printHeaderText(out, Integer.toString(size - row));
                } else {
                    printHeaderText(out, Integer.toString(row + 1));
                }

            } else {
                if(inverted) {
                    if(boardCol % 2 == 0) {
                        setRed(out);
                    } else {
                        setBlue(out);
                    }
                } else {
                    if(boardCol % 2 == 0) {
                        setBlue(out);
                    } else {
                        setRed(out);
                    }
                }
                if (white) {
                    printPlayer(out, initialBoard[row][boardCol - 1]);
                } else {
                    printPlayer(out, initialBoard[7 - row][7 - (boardCol - 1)]);
                }


            }
        }
        setBlack(out);
        out.println();
    }

//    private void reverseBoard(String[][] board) {
//
//    }
//

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setBlue(PrintStream out) {
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player) {
        //out.print(SET_BG_COLOR_WHITE);
        if (Objects.equals(player, WHITE_PAWN) || player.equals(WHITE_ROOK) || player.equals(WHITE_KNIGHT) || player.equals(WHITE_BISHOP) || player.equals(WHITE_QUEEN) || player.equals(WHITE_KING)) {
            out.print(SET_TEXT_COLOR_WHITE);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }

        out.print(player);

        setWhite(out);
    }
}
