package edu.anjerukare.screens.views.pieces;

import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.PieceType.PAWN;

public class PawnView extends PieceView {

    public PawnView(Team team) {
        super(team);
        type = PAWN;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_pawn");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
