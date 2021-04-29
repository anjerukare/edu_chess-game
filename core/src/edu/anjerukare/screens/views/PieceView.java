package edu.anjerukare.screens.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import edu.anjerukare.screens.Piece;
import edu.anjerukare.screens.Team;

abstract public class PieceView extends Actor {

    public TextureRegion textureRegion;
    public Piece type;
    public Team team;

    public PieceView(Team team) {
        this.team = team;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        batch.setColor(color.r, color.g, color.b, getColor().a);
        batch.draw(textureRegion, getX(), getY());
    }
}
