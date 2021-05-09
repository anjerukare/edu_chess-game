package edu.anjerukare.screens.listeners;

import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.VictoryView;

import static com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly;

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
        boardView.overlapped = false;
        boardView.setTouchable(childrenOnly);
    }
}
