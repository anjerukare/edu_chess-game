package edu.anjerukare.screens.models.pieces;

import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static edu.anjerukare.screens.enums.PieceType.KNIGHT;

public class Knight extends Piece {

    public Knight() {
        type = KNIGHT;
    }

    @Override
    public List<Point> getMoveLocations(Point point) {
        List<Point> locations = new ArrayList<>();

        locations.add(new Point(point.x - 1, point.y + 2));
        locations.add(new Point(point.x + 1, point.y + 2));

        locations.add(new Point(point.x + 2, point.y + 1));
        locations.add(new Point(point.x + 2, point.y - 1));

        locations.add(new Point(point.x + 1, point.y - 2));
        locations.add(new Point(point.x - 1, point.y - 2));

        locations.add(new Point(point.x - 2, point.y - 1));
        locations.add(new Point(point.x - 2, point.y + 1));

        return locations;
    }
}
