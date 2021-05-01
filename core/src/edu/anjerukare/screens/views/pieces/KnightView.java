package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.PieceType.KNIGHT;

public class KnightView extends PieceView {

    public KnightView(Team team) {
        super(team);
        type = KNIGHT;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_knight");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
