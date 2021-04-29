package edu.anjerukare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.listeners.BoardListener;
import edu.anjerukare.screens.listeners.PawnPromotingListener;
import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.utils.ManagedScreenAdapter;
import edu.anjerukare.screens.views.BoardView;
import edu.anjerukare.screens.views.PawnPromotingView;

public class GameScreen extends ManagedScreenAdapter {

    private final Chess game;
    private final Stage stage;

    public GameScreen() {
        game = (Chess) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(800, 480));
        addInputProcessor(stage);

        Board board = new Board();
        BoardView boardView = new BoardView();
        boardView.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        PawnPromotingView pawnPromotingView = new PawnPromotingView();
        pawnPromotingView.setPosition((stage.getWidth() - boardView.getWidth()) / 2,
                (stage.getHeight() - boardView.getHeight()) / 2);

        boardView.addListener(new BoardListener(board, boardView, pawnPromotingView));
        pawnPromotingView.addListener(new PawnPromotingListener(board, boardView, pawnPromotingView));

        stage.addActor(boardView);
        stage.addActor(pawnPromotingView);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
