package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.utils.SideMenuManager;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.DrawConfirmView;
import edu.anjerukare.screens.views.GameOverView;
import edu.anjerukare.screens.views.PawnPromotingView;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static edu.anjerukare.screens.enums.Team.WHITE;
import static edu.anjerukare.screens.views.DrawConfirmView.ASK_BLACK;
import static edu.anjerukare.screens.views.DrawConfirmView.ASK_WHITE;
import static edu.anjerukare.screens.views.GameOverView.GameResult.SURRENDER;

public class GameInfoListener extends ClickListener {

    private final SideMenuManager sideMenuManager;
    private final Board board;
    private final BoardView boardView;

    public GameInfoListener(SideMenuManager sideMenuManager, Board board, BoardView boardView) {
        this.sideMenuManager = sideMenuManager;
        this.board = board;
        this.boardView = boardView;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        switch (event.getTarget().getName()) {
            case "surrender": {
                GameOverView gameOverView = sideMenuManager.getView("gameOver");
                boardView.setOverlapped(true);
                gameOverView.setResult(SURRENDER);
                gameOverView.setTeam(board.getOtherPlayerTeam());
                sideMenuManager.pushView("gameOver");
                break;
            }
            case "draw": {
                DrawConfirmView drawConfirmView = sideMenuManager.getView("drawConfirm");
                boardView.setOverlapped(true);
                if (board.getOtherPlayerTeam() == WHITE)
                    drawConfirmView.setLabelText(ASK_WHITE);
                else
                    drawConfirmView.setLabelText(ASK_BLACK);
                sideMenuManager.pushView("drawConfirm");
                break;
            }
        }
    }
}
