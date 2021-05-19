package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import edu.anjerukare.Assets;

import java.util.ArrayList;

import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.right;
import static edu.anjerukare.Assets.COLOR_LIGHT_WHITE;
import static edu.anjerukare.Assets.smallFont;

public abstract class SideMenuView extends Table {

    protected Label label;
    protected final ArrayList<Button> buttons = new ArrayList<>();

    protected void initialize() {
        initialize("");
    }

    protected void initialize(String labelText) {
        label = new Label(labelText, new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE));
        label.setAlignment(center);
        add(label).prefWidth(128).padBottom(16).row();
        Table buttonsTable = new Table();
        for (Button button : buttons)
            buttonsTable.add(button).pad(8);
        add(buttonsTable);
    }

    public void setLabelText(String labelText) {
        label.setText(labelText);
    }
}
