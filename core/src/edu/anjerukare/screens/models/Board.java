package edu.anjerukare.screens.models;

import com.badlogic.gdx.Gdx;
import edu.anjerukare.screens.Piece;
import edu.anjerukare.screens.Team;
import edu.anjerukare.screens.models.pieces.*;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static edu.anjerukare.screens.Piece.*;

public class Board {

    public static Board instance;

    private final edu.anjerukare.screens.models.Piece[][] pieces = new edu.anjerukare.screens.models.Piece[8][8];
    private final List<Pawn> movedPawns = new ArrayList<>();
    private final List<King> movedKings = new ArrayList<>();
    private final List<Rook> movedRooks = new ArrayList<>();
    private final List<Move> moveLog = new ArrayList<>();

    private final Player white = new Player(true);
    private final Player black = new Player(false);
    public Player currentPlayer = white;
    public Player otherPlayer = black;

    public Board() {
        instance = this;

        addPiece(new Rook(), white, 0, 0);
        addPiece(new Knight(), white, 0, 1);
        addPiece(new Bishop(), white, 0, 2);
        addPiece(new Queen(), white, 0, 3);
        addPiece(new King(), white, 0, 4);
        addPiece(new Bishop(), white, 0, 5);
        addPiece(new Knight(), white, 0, 6);
        addPiece(new Rook(), white, 0, 7);
        for (int j = 0; j < 8; ++j)
            addPiece(new Pawn(), white, 1, j);

        addPiece(new Rook(), black, 7, 0);
        addPiece(new Knight(), black, 7, 1);
        addPiece(new Bishop(), black, 7, 2);
        addPiece(new Queen(), black, 7, 3);
        addPiece(new King(), black, 7, 4);
        addPiece(new Bishop(), black, 7, 5);
        addPiece(new Knight(), black, 7, 6);
        addPiece(new Rook(), black, 7, 7);
        for (int j = 0; j < 8; ++j)
            addPiece(new Pawn(), black, 6, j);
    }

    public void addPiece(edu.anjerukare.screens.models.Piece piece, Player player, int row, int col) {
        player.pieces.add(piece);
        pieces[row][col] = piece;
    }

    public Point getPointForPiece(edu.anjerukare.screens.models.Piece piece) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (pieces[i][j] == piece) return new Point(j, i);
            }
        }
        return null;
    }

    public edu.anjerukare.screens.models.Piece getPieceAt(Point point) {
        if (point.x > 7 || point.y > 7 || point.x < 0 || point.y < 0)
            return null;
        return pieces[point.y][point.x];
    }

    public boolean doesPieceBelongToCurrentPlayer(edu.anjerukare.screens.models.Piece piece) {
        return currentPlayer.pieces.contains(piece);
    }

    public void move(edu.anjerukare.screens.models.Piece piece, Point targetPoint) {
        if (piece.type == PAWN && !hasPawnMoved((Pawn) piece))
            movedPawns.add((Pawn) piece);
        if (piece.type == KING && !hasKingMoved((King) piece))
            movedKings.add((King) piece);
        if (piece.type == ROOK && !hasRookMoved((Rook) piece))
            movedRooks.add((Rook) piece);

        Point point = getPointForPiece(piece);
        pieces[point.y][point.x] = null;
        pieces[targetPoint.y][targetPoint.x] = piece;

        moveLog.add(new Move(point, targetPoint, piece));
    }

    public boolean hasPawnMoved(Pawn pawn) {
        return movedPawns.contains(pawn);
    }

    public List<Point> getMovesForPiece(edu.anjerukare.screens.models.Piece piece) {
        Point point = getPointForPiece(piece);
        List<Point> locations = piece.getMoveLocations(point);

        locations.removeIf(p -> p.x > 7 || p.y > 7 || p.x < 0 || p.y < 0);
        locations.removeIf(p -> isFriendlyPieceAt(p));

        return locations;
    }

    public boolean isFriendlyPieceAt(Point point) {
        edu.anjerukare.screens.models.Piece piece = getPieceAt(point);

        if (piece == null)
            return false;
        return currentPlayer.pieces.contains(piece);
    }

    public void capturePieceAt(Point point) {
        edu.anjerukare.screens.models.Piece pieceToCapture = getPieceAt(point);
        if (pieceToCapture.type == KING)
            Gdx.app.log("Game", "Current player wins!");
        currentPlayer.capturedPieces.add(pieceToCapture);
        pieces[point.y][point.x] = null;
    }

    public void promotePawn(Pawn pawn, Piece typeToPromote) {
        Point point = getPointForPiece(pawn);
        switch (typeToPromote) {
            case QUEEN:
                currentPlayer.pieces.remove(pawn);
                addPiece(new Queen(), currentPlayer, point.y, point.x);
                break;
            case KNIGHT:
                currentPlayer.pieces.remove(pawn);
                addPiece(new Knight(), currentPlayer, point.y, point.x);
                break;
            case BISHOP:
                currentPlayer.pieces.remove(pawn);
                addPiece(new Bishop(), currentPlayer, point.y, point.x);
                break;
            case ROOK:
                currentPlayer.pieces.remove(pawn);
                addPiece(new Rook(), currentPlayer, point.y, point.x);
                break;
            default:
                throw new RuntimeException("Wrong piece type to promote");
        }
    }

    public boolean hasKingMoved(King king) {
        return movedKings.contains(king);
    }

    public boolean hasRookMoved(Rook rook) {
        return movedRooks.contains(rook);
    }

    public List<Rook> getCurrentPlayerRooks() {
        List<Rook> rooks = new ArrayList<>();
        for (edu.anjerukare.screens.models.Piece piece : currentPlayer.pieces) {
            if (piece.type == ROOK) rooks.add((Rook) piece);
        }
        return rooks;
    }

    /**
     * Checks emptiness of horizontal tiles between pieces.
     */
    public boolean areHorizontalTilesBetweenPiecesEmpty(edu.anjerukare.screens.models.Piece pieceA, edu.anjerukare.screens.models.Piece pieceB) {
        Point pointA = getPointForPiece(pieceA);
        Point pointB = getPointForPiece(pieceB);
        int dj = pointA.x < pointB.x ? 1 : -1;

        Point point = new Point();
        for (int j = pointA.x + dj; j != pointB.x; j += dj) {
            point.x = j;
            point.y = pointA.y;
            if (getPieceAt(point) != null) return false;
        }
        return true;
    }

    public void castle(King king, Rook rook) {
        Point kingPosition = getPointForPiece(king);
        Point rookPosition = getPointForPiece(rook);

        int direction = kingPosition.x < rookPosition.x ? 1 : -1;
        kingPosition.x += 2 * direction;
        move(king, kingPosition);
        rookPosition.x = kingPosition.x - direction;
        move(rook, rookPosition);
    }

    public Move getLastMove() {
        if (moveLog.isEmpty()) return null;
        return moveLog.get(moveLog.size() - 1);
    }

    public Team getCurrentPlayerTeam() {
        return currentPlayer == white ? Team.WHITE : Team.BLACK;
    }

    public void passTurnToNextPlayer() {
        Player tempPlayer = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tempPlayer;
    }
}
