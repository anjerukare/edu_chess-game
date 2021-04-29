package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.Piece.BISHOP;

public class BishopView extends PieceView {

    public BishopView(Team team) {
        super(team);
        type = BISHOP;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_bishop");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
