package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.Chess;

public class MainMenuListener extends ClickListener {

    private final Chess game;

    public MainMenuListener() {
        game = (Chess) Gdx.app.getApplicationListener();
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        String targetName = event.getTarget().getName();
        if (targetName == null) return;

        switch (targetName) {
            case "start":
                game.getScreenManager().pushScreen("game", "fallingBars");
                break;
            case "about":
                game.getScreenManager().pushScreen("about", "fallingBars");
                break;
        }
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        game.getScreenManager().pushScreen("game", "fallingBars");
        return true;
    }
}
