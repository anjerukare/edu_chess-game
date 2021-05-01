package edu.anjerukare.screens.models.pieces;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static edu.anjerukare.screens.enums.PieceType.ROOK;

public class Rook extends Piece {

    public Rook() {
        type = ROOK;
    }

    @Override
    public List<Point> getMoveLocations(Point point) {
        List<Point> locations = new ArrayList<>();

        for (Point direction : rookDirections) {
            for (int i = 1; i < 8; ++i) {
                Point nextPoint = new Point(point.x + i * direction.x, point.y + i * direction.y);
                locations.add(nextPoint);
                if (Board.instance.getPieceAt(nextPoint) != null) break;
            }
        }

        return locations;
    }
}
