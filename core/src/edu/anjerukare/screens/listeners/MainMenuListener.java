package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import edu.anjerukare.Chess;

public class MainMenuListener extends InputListener {

    private final Chess game;

    public MainMenuListener() {
        game = (Chess) Gdx.app.getApplicationListener();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        game.getScreenManager().pushScreen("game", "fallingBars");
        return true;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        game.getScreenManager().pushScreen("game", "fallingBars");
        return true;
    }
}
