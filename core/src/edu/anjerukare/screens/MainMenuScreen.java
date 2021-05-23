package edu.anjerukare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import edu.anjerukare.Assets;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.listeners.MainMenuListener;
import edu.anjerukare.screens.utils.JumpingButton;
import edu.anjerukare.screens.utils.ManagedScreenAdapter;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;
import static com.badlogic.gdx.utils.Align.center;
import static edu.anjerukare.Assets.*;

public class MainMenuScreen extends ManagedScreenAdapter {

    private final Chess game;
    private final Stage stage;
    private final Table root, body;

    public MainMenuScreen() {
        game = (Chess) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(800, 480));
        addInputProcessor(stage);
        stage.addListener(new MainMenuListener());

        root = new Table();
        root.setFillParent(true);
        root.setTouchable(enabled);
        root.setName("start");
        stage.addActor(root);

        body = new Table();
        body.setFillParent(true);
        body.top().padTop(128);
        stage.addActor(body);

        Image gameLogo = new Image(Assets.get(skin).getDrawable("logo"));
        gameLogo.setTouchable(disabled);
        body.add(gameLogo).padBottom(77).row();

        TypingLabel label = new TypingLabel("{JUMP=0.4;0.05;0.5}\nнажмите любую кнопку, чтобы начать\n{ENDJUMP}",
                new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE));
        label.setTouchable(disabled);
        label.setAlignment(center);
        body.add(label).padBottom(60).row();

        Button aboutButton = new JumpingButton("Об игре",
                new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE), Assets.get(skin));
        aboutButton.setName("about");
        aboutButton.padLeft(16).padRight(16);
        body.add(aboutButton);
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
