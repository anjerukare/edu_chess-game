package edu.anjerukare.screens.listeners;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.GameInfoView;
import edu.anjerukare.screens.views.GameOverView;
import edu.anjerukare.screens.views.GameOverView.GameResult;

import static edu.anjerukare.screens.views.GameOverView.GameResult.CHECKMATE;

public class GameOverListener extends AnyKeyListener {

    private final GameOverView gameOverView;

    private final Board board;
    private final BoardView boardView;
    private final GameInfoView gameInfoView;

    public GameOverListener(GameOverView gameOverView, Board board, BoardView boardView,
                            GameInfoView gameInfoView) {
        this.gameOverView = gameOverView;
        this.board = board;
        this.boardView = boardView;
        this.gameInfoView = gameInfoView;
    }

    @Override
    public void pressed() {
        board.reset();
        boardView.resetPieces();
        gameOverView.setVisible(false);
        boardView.setOverlapped(false);
    }

    public void showViewWith(GameResult gameResult) {
        boardView.setOverlapped(true);
        gameOverView.result = gameResult;
        if (gameResult == CHECKMATE)
            gameOverView.team = board.getOtherPlayerTeam();
        gameOverView.show();
    }
}
