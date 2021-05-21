package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.utils.JumpingButton;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static edu.anjerukare.Assets.*;

public class DrawConfirmView extends SideMenuView {

    public final static String ASK_WHITE = "Белые согласны на ничью?";
    public final static String ASK_BLACK = "Чёрные согласны на ничью?";

    public DrawConfirmView() {
        LabelStyle labelStyle = new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE);
        Label yesLabel = new Label("Да", labelStyle);
        yesLabel.setTouchable(disabled);
        Button yesButton = new JumpingButton(yesLabel, Assets.get(skin));
        yesButton.padLeft(16).padRight(16);
        yesButton.setName("yes");
        buttons.add(yesButton);
        Label noLabel = new Label("Нет", labelStyle);
        noLabel.setTouchable(disabled);
        Button noButton = new JumpingButton(noLabel, Assets.get(skin));
        noButton.padLeft(16).padRight(16);
        noButton.setName("no");
        buttons.add(noButton);

        initialize("Подтверждение", "");
    }
}
