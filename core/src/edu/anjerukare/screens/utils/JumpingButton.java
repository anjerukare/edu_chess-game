package edu.anjerukare.screens.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class JumpingButton extends Button {

    private boolean visuallyPressed;

    public JumpingButton(Actor child, Skin skin) {
        super(child, skin);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                press();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                release();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                release();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (isPressed()) press();
            }
        });
    }

    private void press() {
        if (!visuallyPressed) {
            Actor child = getChild(0);
            child.setY(child.getY() - 4);
        }
        visuallyPressed = true;
    }

    private void release() {
        if (visuallyPressed) {
            Actor child = getChild(0);
            child.setY(child.getY() + 4);
        }
        visuallyPressed = false;
    }
}
