package edu.anjerukare.screens.models.pieces;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.models.Move;
import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static edu.anjerukare.screens.enums.PieceType.PAWN;

public class Pawn extends Piece {

    public Pawn() {
        type = PAWN;
    }

    @Override
    public List<Point> getMoveLocations(Point point) {
        List<Point> locations = new ArrayList<>();

        int forwardDirection = Board.instance.currentPlayer.forward;
        Point forwardOne = new Point(point.x, point.y + forwardDirection);
        if (Board.instance.getPieceAt(forwardOne) == null)
            locations.add(forwardOne);

        Point forwardTwo = new Point(point.x, point.y + 2 * forwardDirection);
        if (!Board.instance.hasPawnMoved(this) && Board.instance.getPieceAt(forwardOne) == null
                && Board.instance.getPieceAt(forwardTwo) == null)
            locations.add(forwardTwo);

        Point forwardLeft = new Point(point.x - 1, point.y + forwardDirection);
        if (Board.instance.getPieceAt(forwardLeft) != null)
            locations.add(forwardLeft);

        Point forwardRight = new Point(point.x + 1, point.y + forwardDirection);
        if (Board.instance.getPieceAt(forwardRight) != null)
            locations.add(forwardRight);

        return locations;
    }

    public Point getEnPassantLocation(Point point) {
        Move lastMove = Board.instance.getLastMove();
        if (lastMove == null) return null;
        if (lastMove.piece.type != PAWN || Math.abs(lastMove.from.y - lastMove.to.y) != 2)
            return null;

        Point leftOne = new Point(point.x - 1, point.y);
        Piece pieceAtLeft = Board.instance.getPieceAt(leftOne);
        Point rightOne = new Point(point.x + 1, point.y);
        Piece pieceAtRight = Board.instance.getPieceAt(rightOne);
        if (pieceAtLeft == null && pieceAtRight == null) return null;
        if (pieceAtLeft != lastMove.piece && pieceAtRight != lastMove.piece) return null;

        if (!Board.instance.doesPieceBelongToCurrentPlayer(pieceAtLeft) &&
                pieceAtLeft == lastMove.piece) {
            int forwardDirection = Board.instance.currentPlayer.forward;
            leftOne.y += forwardDirection;
            return leftOne;
        }

        if (!Board.instance.doesPieceBelongToCurrentPlayer(pieceAtRight) &&
                pieceAtRight == lastMove.piece) {
            int forwardDirection = Board.instance.currentPlayer.forward;
            rightOne.y += forwardDirection;
            return rightOne;
        }

        return null;
    }
}
