package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.utils.SideMenuManager;
import edu.anjerukare.screens.views.BoardView;

import static edu.anjerukare.screens.views.GameInfoView.WHITE_MOVE;

public class GameOverListener extends ClickListener {

    private final SideMenuManager sideMenuManager;
    private final Board board;
    private final BoardView boardView;

    public GameOverListener(SideMenuManager sideMenuManager, Board board, BoardView boardView) {
        this.sideMenuManager = sideMenuManager;
        this.board = board;
        this.boardView = boardView;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (event.getTarget().getName().equals("reset")) {
            board.reset();
            boardView.resetPieces();
            sideMenuManager.getView("gameInfo").setLabelText(WHITE_MOVE);
            sideMenuManager.pushView("gameInfo");
            boardView.setOverlapped(false);
        }
    }
}
