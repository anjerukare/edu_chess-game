package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.Piece.ROOK;

public class RookView extends PieceView {

    public RookView(Team team) {
        super(team);
        type = ROOK;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_rook");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
