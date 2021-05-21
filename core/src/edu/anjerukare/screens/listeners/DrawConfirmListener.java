package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.screens.utils.SideMenuManager;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.GameOverView;
import edu.anjerukare.screens.views.PawnPromotingView;

import static com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly;
import static edu.anjerukare.screens.views.GameOverView.GameResult.DRAW;

public class DrawConfirmListener extends ClickListener {

    private final SideMenuManager sideMenuManager;
    private final BoardView boardView;

    public DrawConfirmListener(SideMenuManager sideMenuManager, BoardView boardView) {
        this.sideMenuManager = sideMenuManager;
        this.boardView = boardView;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        switch (event.getTarget().getName()) {
            case "yes":
                GameOverView gameOverView = sideMenuManager.getView("gameOver");
                gameOverView.setResult(DRAW);
                sideMenuManager.pushView("gameOver");
                break;
            case "no":
                boardView.setOverlapped(false);
                sideMenuManager.pushView("gameInfo");
                break;
        }
    }
}
