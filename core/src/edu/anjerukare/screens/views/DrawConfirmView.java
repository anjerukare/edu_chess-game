package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.utils.JumpingButton;

import static edu.anjerukare.Assets.COLOR_LIGHT_WHITE;
import static edu.anjerukare.Assets.smallFont;

public class DrawConfirmView extends SideMenuView {

    public final static String ASK_WHITE = "Белые согласны на ничью?";
    public final static String ASK_BLACK = "Чёрные согласны на ничью?";

    public DrawConfirmView() {
        LabelStyle labelStyle = new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE);
        Skin skin = Assets.get(Assets.skin);

        Button yesButton = new JumpingButton("Да", labelStyle, skin);
        yesButton.padLeft(16).padRight(16);
        yesButton.setName("yes");
        buttons.add(yesButton);

        Button noButton = new JumpingButton("Нет", labelStyle, skin);
        noButton.padLeft(16).padRight(16);
        noButton.setName("no");
        buttons.add(noButton);

        initialize("Подтверждение", "");
    }
}
