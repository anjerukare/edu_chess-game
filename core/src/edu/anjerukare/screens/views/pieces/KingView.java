package edu.anjerukare.screens.views.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.PieceView;

import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.PieceType.KING;

public class KingView extends PieceView {

    private TextureRegion checkEffect;
    public boolean checked;

    public KingView(Team team) {
        super(team);
        type = KING;
        textureRegion = Assets.get(piecesAtlas).findRegion(
                (team == Team.WHITE? "white": "black") + "_king");
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        checkEffect = Assets.get(piecesAtlas).findRegion("king_checked");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (checked)
            batch.draw(checkEffect, getX(), getY());
    }
}
