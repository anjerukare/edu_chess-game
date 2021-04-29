package edu.anjerukare.screens.models;

import edu.anjerukare.screens.utils.Point;

public class Move {

    public Point from;
    public Point to;
    public Piece piece;

    public Move() {
    }

    public Move(Point from, Point to, Piece piece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }
}
