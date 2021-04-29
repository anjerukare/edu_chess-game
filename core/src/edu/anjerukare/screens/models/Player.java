package edu.anjerukare.screens.models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public final List<Piece> pieces = new ArrayList<>();
    public final List<Piece> capturedPieces = new ArrayList<>();

    public int forward;

    public Player(boolean positiveMovement) {
        if (positiveMovement) forward = 1;
        else forward = -1;
    }
}
