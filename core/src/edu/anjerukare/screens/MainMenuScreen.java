package edu.anjerukare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import edu.anjerukare.Assets;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.listeners.MainMenuListener;
import edu.anjerukare.screens.utils.ManagedScreenAdapter;

import static edu.anjerukare.Assets.*;

public class MainMenuScreen extends ManagedScreenAdapter {

    private final Chess game;
    private final Stage stage;

    public MainMenuScreen() {
        game = (Chess) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(800, 480));
        addInputProcessor(stage);
        stage.addListener(new MainMenuListener());

        TypingLabel label = new TypingLabel("{JUMP=0.4;0.05;0.5}\nнажмите любую кнопку, чтобы начать\n{ENDJUMP}",
                new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE));
        label.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        stage.addActor(label);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public Color getClearColor() {
        return COLOR_BLACK;
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
