package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.screens.views.GameInfoView;
import edu.anjerukare.screens.views.GameOverView;

public class GameInfoListener extends ClickListener {

    private final GameInfoView gameInfoView;

    public GameInfoListener(GameInfoView gameInfoView) {
        this.gameInfoView = gameInfoView;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        switch (event.getTarget().getName()) {
            case "surrender": {

                break;
            }
            case "draw": {

                break;
            }
        }
    }
}
