package edu.anjerukare.screens.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static edu.anjerukare.screens.views.TileView.State.*;

public class TileView extends Actor {

    public enum State {
        DEFAULT, SELECTED, MOVEAVAILABLE, CAPTUREAVAILABLE, CASTLINGAVAILABLE, ENPASSANTAVAILABLE
    }

    public final static Color COLOR_WHITE = Color.valueOf("e1e1e1");
    public final static Color COLOR_BLACK = Color.valueOf("1e1e1e");
    public final static Color COLOR_LIGHT_GREEN = new Color(COLOR_WHITE).lerp(Color.valueOf("2ecc71"), .75f);
    public final static Color COLOR_DARK_GREEN = new Color(COLOR_BLACK).lerp(Color.valueOf("2ecc71"), .75f);
    public final static Color COLOR_LIGHT_RED = new Color(COLOR_WHITE).lerp(Color.valueOf("e74c3c"), .75f);
    public final static Color COLOR_DARK_RED = new Color(COLOR_BLACK).lerp(Color.valueOf("e74c3c"), .75f);
    public final static Color COLOR_LIGHT_PURPLE = new Color(COLOR_WHITE).lerp(Color.valueOf("9b59b6"), .75f);
    public final static Color COLOR_DARK_PURPLE = new Color(COLOR_BLACK).lerp(Color.valueOf("9b59b6"), .75f);

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
