package edu.anjerukare.screens.models.pieces;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.anjerukare.screens.Piece.KING;

public class King extends Piece {

    public King() {
        type = KING;
    }

    @Override
    public List<Point> getMoveLocations(Point point) {
        List<Point> locations = new ArrayList<>();
        List<Point> directions = new ArrayList<>(Arrays.asList(rookDirections));
        directions.addAll(Arrays.asList(bishopDirections));

        for (Point direction : directions)
            locations.add(new Point(point.x + direction.x, point.y + direction.y));

        return locations;
    }

    public List<Point> getCastlingLocations(Point point) {
        if (Board.instance.hasKingMoved(this)) return null;
        List<Rook> rooks = Board.instance.getCurrentPlayerRooks();
        if (rooks.isEmpty()) return null;

        List<Point> locations = new ArrayList<>();
        for (Rook rook : rooks) {
            if (!Board.instance.hasRookMoved(rook) &&
                    Board.instance.areHorizontalTilesBetweenPiecesEmpty(this, rook)) {
                locations.add(Board.instance.getPointForPiece(rook));
            }
        }

        return locations;
    }
}
