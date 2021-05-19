package edu.anjerukare.screens.views;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.utils.JumpingButton;

import static edu.anjerukare.Assets.skin;

public class GameInfoView extends SideMenuView {

    private final Button surrenderButton;
    private final Button drawButton;

    public GameInfoView() {
        NinePatch surrender = Assets.get(skin).getPatch("surrender");
        surrenderButton = new JumpingButton(new Image(surrender), Assets.get(skin));
        buttons.add(surrenderButton);
        NinePatch draw = Assets.get(skin).getPatch("draw");
        drawButton = new JumpingButton(new Image(draw), Assets.get(skin));
        buttons.add(drawButton);

        initialize("Ход белых");
    }

    public Button getSurrenderButton() {
        return surrenderButton;
    }

    public Button getDrawButton() {
        return drawButton;
    }
}
