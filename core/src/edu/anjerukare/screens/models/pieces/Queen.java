package edu.anjerukare.screens.models.pieces;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.anjerukare.screens.Piece.QUEEN;

public class Queen extends Piece {

    public Queen() {
        type = QUEEN;
    }

    @Override
    public List<Point> getMoveLocations(Point point) {
        List<Point> locations = new ArrayList<>();
        List<Point> directions = new ArrayList<>(Arrays.asList(rookDirections));
        directions.addAll(Arrays.asList(bishopDirections));

        for (Point direction : directions) {
            for (int i = 1; i < 8; ++i) {
                Point nextPoint = new Point(point.x + i * direction.x, point.y + i * direction.y);
                locations.add(nextPoint);
                if (Board.instance.getPieceAt(nextPoint) != null) break;
            }
        }

        return locations;
    }
}
