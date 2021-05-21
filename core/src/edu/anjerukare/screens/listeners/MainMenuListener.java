package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.Gdx;
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
