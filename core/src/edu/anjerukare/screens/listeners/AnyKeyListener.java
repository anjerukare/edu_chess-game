package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class AnyKeyListener extends InputListener {

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        pressed();
        return true;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        pressed();
        return true;
    }

    public abstract void pressed();
}
