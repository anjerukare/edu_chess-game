package edu.anjerukare.screens.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;

public class JumpingButton extends Button {

    private boolean visuallyPressed;

    public JumpingButton(Actor child, Skin skin) {
        super(child, skin);
    }

    public JumpingButton(String innerLabelText, LabelStyle labelStyle, Skin skin) {
        super(skin);
        Label label = new Label(innerLabelText, labelStyle);
        label.setTouchable(disabled);
        add(label);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isPressed()) press();
        else release();
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
