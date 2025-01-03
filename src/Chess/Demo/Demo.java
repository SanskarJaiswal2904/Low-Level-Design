package Chess.Demo;

import java.util.ArrayList;
import java.util.List;

enum PieceColor {
    WHITE, BLACK
}

enum StatusOfGame{

        }

enum PieceType {
    PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
}

class GameStatus {
    public boolean isCheck(Board board, PieceColor currentPlayer) {
        // Logic to check if the current player's king is in check.
        return false; // Placeholder
    }

    public boolean isCheckmate(Board board, PieceColor currentPlayer) {
        // Logic to check if the current player's king is in checkmate.
        return false; // Placeholder
    }

    public boolean isStalemate(Board board, PieceColor currentPlayer) {
        // Logic to check if the game is in stalemate.
        return false; // Placeholder
    }

    public boolean isDraw(Board board) {
        // Logic to determine if the game is a draw (e.g., insufficient material).
        return false; // Placeholder
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
        board.displayBoard();

        Move move1 = new Move(6, 0, 4, 0); // White pawn moves 2 steps
        board.movePiece(move1);

        Move move2 = new Move(1, 0, 3, 0); // Black pawn moves 2 steps
        board.movePiece(move2);

        Move move3 = new Move(7, 1, 5, 2); // White knight moves
        board.movePiece(move3);

        board.displayBoard();
    }
}


/*
*
* To implement the functionality to check for Check, Checkmate, Stalemate, and Draw, follow these steps:


---

1. Check if the King is in Check

A king is in check if it is under attack by any opponent piece.

Steps:

Locate the king's position on the board.

Check if any opponent piece has a valid move that targets the king's position.


Code:

public boolean isCheck(Board board, PieceColor currentPlayer) {
    int kingX = -1, kingY = -1;

    // Find the king's position
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

    // Check if any opponent piece can attack the king
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


---

2. Checkmate

A player is in checkmate if:

1. The king is in check.


2. The king cannot escape by moving to a valid position.


3. No other piece can block or capture the attacking piece.



Code:

public boolean isCheckmate(Board board, PieceColor currentPlayer) {
    if (!isCheck(board, currentPlayer)) {
        return false;
    }

    // Check if the king has any valid moves to escape
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = board.getPiece(i, j);
            if (piece != null && piece.color == currentPlayer) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        Move move = new Move(i, j, x, y);
                        if (piece.isValidMove(move, board)) {
                            // Simulate the move
                            Piece original = board.getPiece(x, y);
                            board.movePiece(move);
                            boolean stillInCheck = isCheck(board, currentPlayer);
                            board.undoMove(move, original); // Revert the move

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


---

3. Stalemate

A player is in stalemate if:

1. The king is NOT in check.


2. The player has no legal moves available.



Code:

public boolean isStalemate(Board board, PieceColor currentPlayer) {
    if (isCheck(board, currentPlayer)) {
        return false;
    }

    // Check if the player has any valid moves
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


---

4. Draw

A game is a draw if:

1. Insufficient material to checkmate (e.g., only kings left).


2. Repeated moves (threefold repetition, which requires additional tracking).


3. Fifty-move rule (50 moves without a pawn move or capture).



Code for Insufficient Material:

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

    // Check for insufficient material
    if (pieces.size() == 2) {
        return true; // Only two kings
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


---

Integration Notes:

After every move, evaluate the game state using these methods.

Combine isCheck, isCheckmate, isStalemate, and isDraw in GameStatus for comprehensive evaluation.

To include a GameStatus class that checks conditions like check, checkmate, stalemate, or draw, you can:

1. Create the GameStatus class:

This class will evaluate the board's state after every move and determine the current status of the game.



2. Integrate GameStatus in the ChessGame class:

After every move, call the GameStatus methods to evaluate the status of the game.





---

Steps:

1. Create GameStatus class:

Define methods to evaluate: isCheck(), isCheckmate(), isStalemate(), and isDraw().



2. Modify ChessGame:

Add an instance of GameStatus.

After each move, call methods from GameStatus to check and update the game's state.



3. Modify Board:

Ensure the board has a method to determine all valid moves for a given color. This will help the GameStatus class detect stalemate or checkmate.





---

Example Code Integration Points:

Add the GameStatus class:
Add a new class in the same file:

class GameStatus {
    public boolean isCheck(Board board, PieceColor currentPlayer) {
        // Logic to check if the current player's king is in check.
        return false; // Placeholder
    }

    public boolean isCheckmate(Board board, PieceColor currentPlayer) {
        // Logic to check if the current player's king is in checkmate.
        return false; // Placeholder
    }

    public boolean isStalemate(Board board, PieceColor currentPlayer) {
        // Logic to check if the game is in stalemate.
        return false; // Placeholder
    }

    public boolean isDraw(Board board) {
        // Logic to determine if the game is a draw (e.g., insufficient material).
        return false; // Placeholder
    }
}

In ChessGame, after movePiece:

GameStatus gameStatus = new GameStatus();
PieceColor currentPlayer = PieceColor.WHITE; // Track current player.

if (gameStatus.isCheck(board, currentPlayer)) {
    System.out.println("Check!");
} else if (gameStatus.isCheckmate(board, currentPlayer)) {
    System.out.println("Checkmate!");
} else if (gameStatus.isStalemate(board, currentPlayer)) {
    System.out.println("Stalemate!");
} else if (gameStatus.isDraw(board)) {
    System.out.println("Draw!");
}

// Switch player turn.
currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
* */