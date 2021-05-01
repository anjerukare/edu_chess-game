package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.PieceType.BISHOP;

public class BishopView extends PieceView {

    public BishopView(Team team) {
        super(team);
        type = BISHOP;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_bishop");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
