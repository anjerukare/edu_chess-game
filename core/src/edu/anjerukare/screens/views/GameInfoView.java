package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.utils.JumpingButton;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;
import static edu.anjerukare.Assets.skin;

public class GameInfoView extends SideMenuView {

    private final Button surrenderButton;
    private final Button drawButton;

    public final static String WHITE_MOVE = "Ходят белые";
    public final static String BLACK_MOVE = "Ходят чёрные";

    public GameInfoView() {
        Image surrender = new Image(Assets.get(skin).getDrawable("surrender"));
        surrender.setTouchable(disabled);
        surrenderButton = new JumpingButton(surrender, Assets.get(skin));
        surrenderButton.setName("surrender");
        buttons.add(surrenderButton);
        Image draw = new Image(Assets.get(skin).getDrawable("draw"));
        draw.setTouchable(disabled);
        drawButton = new JumpingButton(draw, Assets.get(skin));
        drawButton.setName("draw");
        buttons.add(drawButton);

        initialize("Текущая партия" , WHITE_MOVE);
    }

    public void setButtonsDisabled(boolean disabled) {
        surrenderButton.setDisabled(disabled);
        drawButton.setDisabled(disabled);
        if (disabled) {
            surrenderButton.setTouchable(Touchable.disabled);
            drawButton.setTouchable(Touchable.disabled);
        } else {
            surrenderButton.setTouchable(enabled);
            drawButton.setTouchable(enabled);
        }
    }
}
