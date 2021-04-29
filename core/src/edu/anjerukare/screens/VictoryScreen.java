package edu.anjerukare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.anjerukare.Assets;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.utils.ManagedScreenAdapter;

import static edu.anjerukare.Assets.bigFont;
import static edu.anjerukare.screens.Team.WHITE;

public class VictoryScreen extends ManagedScreenAdapter {

    private final Chess game;
    private final Stage stage;

    public Team winningTeam = WHITE;

    public VictoryScreen() {
        game = (Chess) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(800, 480));
        addInputProcessor(stage);
    }

    @Override
    public void show() {
        super.show();
        stage.clear();

        String victoryText;
        if (winningTeam == WHITE)
            victoryText = "Победили белые!";
        else
            victoryText = "Победили чёрные!";
        Label victoryLabel = new Label(victoryText, new LabelStyle(Assets.get(bigFont), null));
        victoryLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        stage.addActor(victoryLabel);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
