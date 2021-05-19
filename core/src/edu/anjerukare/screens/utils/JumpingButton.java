package edu.anjerukare.screens.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class JumpingButton extends Button {

    public JumpingButton(Actor child, Skin skin) {
        super(child, skin);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor child = getChild(0);
                child.setY(child.getY() - 4);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor child = getChild(0);
                child.setY(child.getY() + 4);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
