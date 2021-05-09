package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import edu.anjerukare.Chess;

public class MainMenuListener extends AnyKeyListener {

    private final Chess game;

    public MainMenuListener() {
        game = (Chess) Gdx.app.getApplicationListener();
    }

    @Override
    public void pressed() {
        game.getScreenManager().pushScreen("game", "fallingBars");
    }
}
