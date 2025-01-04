package Chess.Demo;

import java.util.ArrayList;
import java.util.List;

enum PieceColor {
    WHITE, BLACK
}

enum StatusOfGame {
}

enum PieceType {
    PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
}

class GameStatus {
    public boolean isCheck(Board board, PieceColor currentPlayer) {
        int kingX = -1, kingY = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.type == PieceType.KING && piece.color == currentPlayer) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.color != currentPlayer) {
                    Move move = new Move(i, j, kingX, kingY);
                    if (piece.isValidMove(move, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Board board, PieceColor currentPlayer) {
        if (!isCheck(board, currentPlayer)) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.color == currentPlayer) {
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            Move move = new Move(i, j, x, y);
                            if (piece.isValidMove(move, board)) {
                                Piece original = board.getPiece(x, y);
                                board.movePiece(move);
                                boolean stillInCheck = isCheck(board, currentPlayer);
                                board.undoMove(move, original);
                                if (!stillInCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isStalemate(Board board, PieceColor currentPlayer) {
        if (isCheck(board, currentPlayer)) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.color == currentPlayer) {
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            Move move = new Move(i, j, x, y);
                            if (piece.isValidMove(move, board)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isDraw(Board board) {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null) {
                    pieces.add(piece);
                }
            }
        }

        if (pieces.size() == 2) {
            return true;
        }
        if (pieces.size() == 3) {
            for (Piece piece : pieces) {
                if (piece.type == PieceType.KNIGHT || piece.type == PieceType.BISHOP) {
                    return true;
                }
            }
        }
        return false;
    }
}

class Move {
    int startX, startY, endX, endY;

    public Move(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}

abstract class Piece {
    PieceColor color;
    PieceType type;

    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    abstract boolean isValidMove(Move move, Board board);

    protected boolean isPathClear(Move move, Board board) {
        int xDir = Integer.signum(move.endX - move.startX);
        int yDir = Integer.signum(move.endY - move.startY);

        int x = move.startX + xDir;
        int y = move.startY + yDir;

        while (x != move.endX || y != move.endY) {
            if (board.getPiece(x, y) != null) {
                return false;
            }
            x += xDir;
            y += yDir;
        }
        return true;
    }
}

class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        int direction = (color == PieceColor.WHITE) ? -1 : 1;
        int startRow = (color == PieceColor.WHITE) ? 6 : 1;

        if (move.endY == move.startY && move.endX == move.startX + direction &&
                board.getPiece(move.endX, move.endY) == null) {
            return true;
        }
        if (move.endY == move.startY && move.startX == startRow &&
                move.endX == move.startX + 2 * direction &&
                board.getPiece(move.startX + direction, move.startY) == null &&
                board.getPiece(move.endX, move.endY) == null) {
            return true;
        }
        if (Math.abs(move.endY - move.startY) == 1 && move.endX == move.startX + direction &&
                board.getPiece(move.endX, move.endY) != null &&
                board.getPiece(move.endX, move.endY).color != color) {
            return true;
        }
        return false;
    }
}

class Knight extends Piece {
    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        int dx = Math.abs(move.endX - move.startX);
        int dy = Math.abs(move.endY - move.startY);
        return dx * dy == 2 && (board.getPiece(move.endX, move.endY) == null ||
                board.getPiece(move.endX, move.endY).color != color);
    }
}

class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if (Math.abs(move.endX - move.startX) == Math.abs(move.endY - move.startY)) {
            return isPathClear(move, board) &&
                    (board.getPiece(move.endX, move.endY) == null ||
                            board.getPiece(move.endX, move.endY).color != color);
        }
        return false;
    }
}

class Rook extends Piece {
    public Rook(PieceColor color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if (move.endX == move.startX || move.endY == move.startY) {
            return isPathClear(move, board) &&
                    (board.getPiece(move.endX, move.endY) == null ||
                            board.getPiece(move.endX, move.endY).color != color);
        }
        return false;
    }
}

class Queen extends Piece {
    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if (Math.abs(move.endX - move.startX) == Math.abs(move.endY - move.startY) ||
                move.endX == move.startX || move.endY == move.startY) {
            return isPathClear(move, board) &&
                    (board.getPiece(move.endX, move.endY) == null ||
                            board.getPiece(move.endX, move.endY).color != color);
        }
        return false;
    }
}

class King extends Piece {
    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        int dx = Math.abs(move.endX - move.startX);
        int dy = Math.abs(move.endY - move.startY);
        return dx <= 1 && dy <= 1 &&
                (board.getPiece(move.endX, move.endY) == null ||
                        board.getPiece(move.endX, move.endY).color != color);
    }
}

class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(PieceColor.BLACK);
            board[6][i] = new Pawn(PieceColor.WHITE);
        }
        board[0][0] = new Rook(PieceColor.BLACK);
        board[0][7] = new Rook(PieceColor.BLACK);
        board[7][0] = new Rook(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);

        board[0][1] = new Knight(PieceColor.BLACK);
        board[0][6] = new Knight(PieceColor.BLACK);
        board[7][1] = new Knight(PieceColor.WHITE);
        board[7][6] = new Knight(PieceColor.WHITE);

        board[0][2] = new Bishop(PieceColor.BLACK);
        board[0][5] = new Bishop(PieceColor.BLACK);
        board[7][2] = new Bishop(PieceColor.WHITE);
        board[7][5] = new Bishop(PieceColor.WHITE);

        board[0][3] = new Queen(PieceColor.BLACK);
        board[7][3] = new Queen(PieceColor.WHITE);

        board[0][4] = new King(PieceColor.BLACK);
        board[7][4] = new King(PieceColor.WHITE);
    }

    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return null;
        return board[x][y];
    }

    public void movePiece(Move move) {
        Piece piece = getPiece(move.startX, move.startY);
        if (piece == null || !piece.isValidMove(move, this)) {
            throw new IllegalArgumentException("Invalid move");
        }
        board[move.endX][move.endY] = piece;
        board[move.startX][move.startY] = null;
        System.out.println();
    }

    public void undoMove(Move move, Piece capturedPiece) {
        Piece piece = getPiece(move.endX, move.endY);
        board[move.startX][move.startY] = piece;
        board[move.endX][move.endY] = capturedPiece;
    }

    public void displayBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(board[i][j].type.toString().charAt(0) + " ");
                }
            }
            System.out.println();
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Board board = new Board();
        GameStatus gameStatus = new GameStatus();
        PieceColor currentPlayer = PieceColor.WHITE;

        board.displayBoard();

        Move move1 = new Move(6, 0, 4, 0);
        board.movePiece(move1);

        if (gameStatus.isCheck(board, currentPlayer)) {
            System.out.println("Check!");
        } else if (gameStatus.isCheckmate(board, currentPlayer)) {
            System.out.println("Checkmate!");
        } else if (gameStatus.isStalemate(board, currentPlayer)) {
            System.out.println("Stalemate!");
        } else if (gameStatus.isDraw(board)) {
            System.out.println("Draw!");
        }

        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        Move move2 = new Move(1, 0, 3, 0);
        board.movePiece(move2);

        if (gameStatus.isCheck(board, currentPlayer)) {
            System.out.println("Check!");
        } else if (gameStatus.isCheckmate(board, currentPlayer)) {
            System.out.println("Checkmate!");
        } else if (gameStatus.isStalemate(board, currentPlayer)) {
            System.out.println("Stalemate!");
        } else if (gameStatus.isDraw(board)) {
            System.out.println("Draw!");
        }

        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        Move move3 = new Move(7, 1, 5, 2);
        board.movePiece(move3);

        if (gameStatus.isCheck(board, currentPlayer)) {
            System.out.println("Check!");
        } else if (gameStatus.isCheckmate(board, currentPlayer)) {
            System.out.println("Checkmate!");
        } else if (gameStatus.isStalemate(board, currentPlayer)) {
            System.out.println("Stalemate!");
        } else if (gameStatus.isDraw(board)) {
            System.out.println("Draw!");
        }

        board.displayBoard();
    }
}
