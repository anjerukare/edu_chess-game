package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.Chess;

public class AboutListener extends ClickListener {

    private final Chess game;

    public AboutListener() {
        game = (Chess) Gdx.app.getApplicationListener();
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        String targetName = event.getTarget().getName();
        if (targetName == null) return;

        switch (targetName) {
            case "back":
                game.getScreenManager().pushScreen("mainMenu", "fallingBars");
                break;
            case "github":
                Gdx.net.openURI("https://github.com/anjerukare/edu_chess-game");
                break;

            /* extensions and assets */
            case "PAC":
                Gdx.net.openURI("http://devilswork.shop/");
                break;
            case "ST":
                Gdx.net.openURI("https://github.com/gl-transitions/gl-transitions/blob/master/transitions/DoomScreenTransition.glsl");
                break;
            case "TL":
                Gdx.net.openURI("https://github.com/rafaskb/typing-label");
                break;
            case "SM":
                Gdx.net.openURI("https://github.com/crykn/libgdx-screenmanager");
                break;

            /* licenses */
            case "CC010":
                Gdx.net.openURI("https://creativecommons.org/publicdomain/zero/1.0/");
                break;
            case "MIT":
                Gdx.net.openURI("https://opensource.org/licenses/MIT");
                break;
            case "A20L":
                Gdx.net.openURI("https://www.apache.org/licenses/LICENSE-2.0");
                break;
        }
    }
}
