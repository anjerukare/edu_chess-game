package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.PieceType.KING;

public class KingView extends PieceView {

    public KingView(Team team) {
        super(team);
        type = KING;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_king");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
