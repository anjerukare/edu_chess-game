package edu.anjerukare.screens.listeners;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.VictoryView;
import edu.anjerukare.screens.views.VictoryView.GameResult;

import static edu.anjerukare.screens.views.VictoryView.GameResult.CHECKMATE;

public class VictoryListener extends AnyKeyListener {

    private final VictoryView victoryView;

    private final Board board;
    private final BoardView boardView;

    public VictoryListener(VictoryView victoryView, Board board, BoardView boardView) {
        this.victoryView = victoryView;
        this.board = board;
        this.boardView = boardView;
    }

    @Override
    public void pressed() {
        board.reset();
        boardView.resetPieces();
        victoryView.setVisible(false);
        boardView.setOverlapped(false);
    }

    public void showViewWith(GameResult gameResult) {
        boardView.setOverlapped(true);
        victoryView.result = gameResult;
        if (gameResult == CHECKMATE)
            victoryView.team = board.getOtherPlayerTeam();
        victoryView.show();
    }
}
