package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.anjerukare.Assets;

import java.util.ArrayList;

import static com.badlogic.gdx.utils.Align.center;
import static edu.anjerukare.Assets.*;

public abstract class SideMenuView extends Table {

    protected Label header, label;
    protected final ArrayList<Button> buttons = new ArrayList<>();

    protected void initialize(String headerText, String labelText) {
        initialize(headerText, labelText, false);
    }

    protected void initialize(String headerText, String labelText, boolean buildButtonsVertically) {
        header = new Label(headerText, new LabelStyle(Assets.get(bigFont), COLOR_LIGHT_WHITE));
        header.setAlignment(center);
        add(header).padBottom(16).row();
        label = new Label(labelText, new LabelStyle(Assets.get(smallFont), COLOR_WHITE));
        label.setAlignment(center);
        add(label).padBottom(24).row();
        Table buttonsTable = new Table();
        for (Button button : buttons) {
            if (buildButtonsVertically)
                buttonsTable.add(button).pad(0, 8, 8, 8).row();
            else
                buttonsTable.add(button).pad(0, 8, 0, 8);
        }
        add(buttonsTable);
    }

    public void setHeaderText(String headerText) {
        header.setText(headerText);
    }

    public void setLabelText(String labelText) {
        label.setText(labelText);
    }
}
