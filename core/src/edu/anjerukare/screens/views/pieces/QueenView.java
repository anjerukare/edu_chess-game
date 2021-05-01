package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.PieceType.QUEEN;

public class QueenView extends PieceView {

    public QueenView(Team team) {
        super(team);
        type = QUEEN;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_queen");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
