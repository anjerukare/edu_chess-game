package edu.anjerukare.screens.models;

import edu.anjerukare.screens.enums.PieceType;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.models.pieces.*;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static edu.anjerukare.screens.enums.PieceType.*;

public class Board {

    public static Board instance;

    private final Piece[][] pieces = new Piece[8][8];
    private final List<Pawn> movedPawns = new ArrayList<>();
    private final List<King> movedKings = new ArrayList<>();
    private final List<Rook> movedRooks = new ArrayList<>();
    private final List<Move> moveLog = new ArrayList<>();

    private final Player white = new Player(true);
    private final Player black = new Player(false);
    public Player currentPlayer;
    public Player otherPlayer;

    public Board() {
        instance = this;
        reset();
    }

    public void reset() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j)
                pieces[i][j] = null;
        }

        movedPawns.clear();
        movedKings.clear();
        movedRooks.clear();
        moveLog.clear();

        white.pieces.clear();
        white.capturedPieces.clear();
        black.pieces.clear();
        black.capturedPieces.clear();
        currentPlayer = white;
        otherPlayer = black;

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

    public void addPiece(Piece piece, Player player, int row, int col) {
        player.pieces.add(piece);
        pieces[row][col] = piece;
    }

    public Point getPointForPiece(Piece piece) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (pieces[i][j] == piece) return new Point(j, i);
            }
        }
        return null;
    }

    public Piece getPieceAt(Point point) {
        if (point.x > 7 || point.y > 7 || point.x < 0 || point.y < 0)
            return null;
        return pieces[point.y][point.x];
    }

    public boolean doesPieceBelongToCurrentPlayer(Piece piece) {
        return currentPlayer.pieces.contains(piece);
    }

    public void move(Piece piece, Point targetPoint) {
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

    public List<Point> getMovesForPiece(Piece piece) {
        return getMovesForPiece(piece, true);
    }

    public List<Point> getMovesForPiece(Piece piece, boolean excludeLeadToCheck) {
        Point point = getPointForPiece(piece);
        List<Point> locations = piece.getMoveLocations(point);

        locations.removeIf(p -> p.x > 7 || p.y > 7 || p.x < 0 || p.y < 0);
        locations.removeIf(p -> arePiecesAllies(piece, getPieceAt(p)));

        if (excludeLeadToCheck)
            removeLocationsThatWillLeadToCheck(locations, piece);

        return locations;
    }

    private void removeLocationsThatWillLeadToCheck(List<Point> locations, Piece pieceToMove) {
        Iterator<Point> iterator = locations.iterator();
        while (iterator.hasNext()) {
            Point targetPoint = iterator.next();
            Point point = getPointForPiece(pieceToMove);
            Piece pieceAtTargetPoint = getPieceAt(targetPoint);
            /* move without log and adding to moved lists */
            pieces[point.y][point.x] = null;
            pieces[targetPoint.y][targetPoint.x] = pieceToMove;
            otherPlayer.pieces.remove(pieceAtTargetPoint);
            if (isCheck()) iterator.remove();
            /* reset last move */
            pieces[point.y][point.x] = pieceToMove;
            pieces[targetPoint.y][targetPoint.x] = pieceAtTargetPoint;
            if (pieceAtTargetPoint != null) otherPlayer.pieces.add(pieceAtTargetPoint);
        }
    }

    public boolean isCheck() {
        King king = getCurrentPlayerKing();
        Point kingPosition = getPointForPiece(king);
        for (Piece enemyPiece : otherPlayer.pieces) {
            if (getMovesForPiece(enemyPiece, false).contains(kingPosition))
                return true;
        }
        return false;
    }

    public King getCurrentPlayerKing() {
        for (Piece piece : currentPlayer.pieces) {
            if (piece.type == KING) return (King) piece;
        }
        return null;
    }

    public int getForwardForPiece(Piece piece) {
        Player player = getPlayerForPiece(piece);
        return player.forward;
    }

    private Player getPlayerForPiece(Piece piece) {
        for (Piece currentPlayerPiece : currentPlayer.pieces) {
            if (currentPlayerPiece.equals(piece)) return currentPlayer;
        }
        for (Piece otherPlayerPiece : otherPlayer.pieces) {
            if (otherPlayerPiece.equals(piece)) return otherPlayer;
        }
        throw new IllegalArgumentException("Player for given piece not found");
    }

    public boolean arePiecesAllies(Piece pieceA, Piece pieceB) {
        Player player = getPlayerForPiece(pieceA);
        if (pieceB == null)
            return false;
        return player.pieces.contains(pieceB);
    }

    public void capturePieceAt(Point point) {
        Piece pieceToCapture = getPieceAt(point);
        currentPlayer.capturedPieces.add(pieceToCapture);
        otherPlayer.pieces.remove(pieceToCapture);
        pieces[point.y][point.x] = null;
    }

    public void promotePawn(Pawn pawn, PieceType typeToPromote) {
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
                Rook rook = new Rook();
                addPiece(rook, currentPlayer, point.y, point.x);
                movedRooks.add(rook);
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
        for (Piece piece : currentPlayer.pieces) {
            if (piece.type == ROOK) rooks.add((Rook) piece);
        }
        return rooks;
    }

    public boolean areHorizontalTilesBetweenPiecesEmpty(Piece pieceA, Piece pieceB) {
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

    public List<Point> getCastlingMoves(King king) {
        Point kingPosition = getPointForPiece(king);
        List<Point> castlingLocations = king.getCastlingLocations(kingPosition);
        if (castlingLocations == null) return null;
        if (isCheck()) return null;

        Iterator<Point> iterator = castlingLocations.iterator();
        while (iterator.hasNext()) {
            Point castlingLocation = iterator.next();
            int direction = castlingLocation.x < kingPosition.x ? -1 : 1;
            for (int j = direction; j != 3 * direction; j += direction) {
                /* move without log and adding to moved lists */
                pieces[kingPosition.y][kingPosition.x] = null;
                pieces[kingPosition.y][kingPosition.x + j] = king;
                if (isCheck()) iterator.remove();
                /* reset last move */
                pieces[kingPosition.y][kingPosition.x + j] = null;
                pieces[kingPosition.y][kingPosition.x] = king;
            }
        }

        if (castlingLocations.isEmpty()) return null;
        return castlingLocations;
    }

    public Point getEnPassantMove(Pawn pawn) {
        Point pawnPosition = getPointForPiece(pawn);
        Point enPassantLocation = pawn.getEnPassantLocation(pawnPosition);
        if (enPassantLocation == null) return null;

        int forwardDirection = currentPlayer.forward;
        Point enPassantTarget = new Point(enPassantLocation.x, enPassantLocation.y - forwardDirection);
        Piece pieceAtTargetPoint = getPieceAt(enPassantTarget);
        /* move without log and adding to moved lists */
        pieces[pawnPosition.y][pawnPosition.x] = null;
        pieces[enPassantTarget.y][enPassantTarget.x] = pawn;
        otherPlayer.pieces.remove(pieceAtTargetPoint);
        if (isCheck()) enPassantLocation = null;
        /* reset last move */
        pieces[pawnPosition.y][pawnPosition.x] = pawn;
        pieces[enPassantTarget.y][enPassantTarget.x] = pieceAtTargetPoint;
        if (pieceAtTargetPoint != null) otherPlayer.pieces.add(pieceAtTargetPoint);

        return enPassantLocation;
    }

    public Move getLastMove() {
        if (moveLog.isEmpty()) return null;
        return moveLog.get(moveLog.size() - 1);
    }

    public Team getCurrentPlayerTeam() {
        return currentPlayer == white ? Team.WHITE : Team.BLACK;
    }

    public Team getOtherPlayerTeam() {
        return otherPlayer == white ? Team.WHITE : Team.BLACK;
    }

    public boolean hasCurrentPlayerNoMoves() {
        for (Piece piece : currentPlayer.pieces) {
            if (!getMovesForPiece(piece).isEmpty()) return false;

            if (piece.type == PAWN) {
                if (getEnPassantMove((Pawn) piece) != null) return false;
            } else if (piece.type == KING) {
                if (getCastlingMoves((King) piece) != null) return false;
            }
        }
        return true;
    }

    public void passTurnToNextPlayer() {
        Player tempPlayer = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tempPlayer;
    }
}
