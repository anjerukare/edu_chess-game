package edu.anjerukare.screens.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static edu.anjerukare.Assets.*;
import static edu.anjerukare.screens.views.TileView.State.*;

public class TileView extends Actor {

    public enum State {
        DEFAULT, SELECTED, MOVEAVAILABLE, CAPTUREAVAILABLE, CASTLINGAVAILABLE, ENPASSANTAVAILABLE
    }

    public PieceView piece;
    public final Color color;
    public State state = DEFAULT;

    private final static ShapeRenderer renderer = new ShapeRenderer();

    public TileView(Color color, PieceView piece) {
        setSize(44, 44);
        this.color = color;
        this.piece = piece;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        switch (state) {
            case DEFAULT:
                renderer.setColor(color);
                break;
            case SELECTED: case MOVEAVAILABLE:
                renderer.setColor(color == COLOR_WHITE ? COLOR_LIGHT_GREEN : COLOR_DARK_GREEN);
                break;
            case CAPTUREAVAILABLE: case ENPASSANTAVAILABLE:
                renderer.setColor(color == COLOR_WHITE ? COLOR_LIGHT_RED : COLOR_DARK_RED);
                break;
            case CASTLINGAVAILABLE:
                renderer.setColor(color == COLOR_WHITE ? COLOR_LIGHT_PURPLE : COLOR_DARK_PURPLE);
                break;
        }
        renderer.rect(getX(), getY(), 44, 44);
        renderer.end();

        batch.begin();
    }
}
