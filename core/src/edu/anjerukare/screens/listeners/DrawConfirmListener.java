package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.screens.utils.SideMenuManager;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.GameOverView;

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
        String targetName = event.getTarget().getName();
        if (targetName == null) return;

        switch (targetName) {
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
