package edu.anjerukare.screens.models;

import edu.anjerukare.screens.enums.PieceType;
import edu.anjerukare.screens.utils.Point;

import java.util.List;

abstract public class Piece {

    public PieceType type;
    protected static final Point[] rookDirections = {new Point(0, 1), new Point(1, 0),
            new Point(0, -1), new Point(-1, 0)};
    protected static final Point[] bishopDirections = {new Point(1, 1), new Point(1, -1),
            new Point(-1, -1), new Point(-1, 1)};

    abstract public List<Point> getMoveLocations(Point point);
}
