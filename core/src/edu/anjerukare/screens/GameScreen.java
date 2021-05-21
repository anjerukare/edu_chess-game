package edu.anjerukare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.anjerukare.Assets;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.listeners.*;
import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.utils.ManagedScreenAdapter;
import edu.anjerukare.screens.utils.SideMenuManager;
import edu.anjerukare.screens.views.*;

import static com.badlogic.gdx.utils.Align.center;
import static edu.anjerukare.Assets.*;

public class GameScreen extends ManagedScreenAdapter {

    private final Chess game;
    private final Stage stage;

    private final Table root = new Table();

    public GameScreen() {
        game = (Chess) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(800, 480));
        addInputProcessor(stage);

        Board board = new Board();
        BoardView boardView = new BoardView();

        PawnPromotingView pawnPromotingView = new PawnPromotingView();
        pawnPromotingView.setPosition((stage.getWidth() - boardView.getWidth()) / 2,
                (stage.getHeight() - boardView.getHeight()) / 2);

        SideMenuManager sideMenuManager = new SideMenuManager();
        GameInfoView gameInfoView = new GameInfoView();
        gameInfoView.addListener(new GameInfoListener(sideMenuManager, board, boardView));
        sideMenuManager.addView("gameInfo", gameInfoView);
        GameOverView gameOverView = new GameOverView();
        GameOverListener gameOverListener = new GameOverListener(sideMenuManager, board, boardView);
        gameOverView.addListener(gameOverListener);
        sideMenuManager.addView("gameOver", gameOverView);
        DrawConfirmView drawConfirmView = new DrawConfirmView();
        drawConfirmView.addListener(new DrawConfirmListener(sideMenuManager, boardView));
        sideMenuManager.addView("drawConfirm", drawConfirmView);

        BoardListener boardListener = new BoardListener(board, boardView, pawnPromotingView,
                sideMenuManager);
        boardView.addListener(boardListener);
        pawnPromotingView.addListener(new PawnPromotingListener(board, boardView, pawnPromotingView,
                boardListener, sideMenuManager));

        root.setFillParent(true);
        root.padTop(44);
        initializeTable(boardView, sideMenuManager);

        stage.addActor(root);
        stage.addActor(pawnPromotingView);
        stage.addActor(gameOverView);
    }

    private void initializeTable(BoardView boardView, SideMenuManager sideMenuManager) {
        LabelStyle labelStyle = new LabelStyle(Assets.get(bigFont), COLOR_LIGHT_WHITE);

        Table digits = new Table();
        for (int i = 0; i < 8; ++i) {
            Label digit = new Label(Character.toString((char) ('8' - i)), labelStyle);
            digit.setAlignment(center);
            digits.add(digit).prefSize(44);
            digits.row();
        }
        root.add(digits);

        root.add(boardView).prefSize(boardView.getWidth(), boardView.getHeight());
        GameInfoView gameInfoView = sideMenuManager.getView("gameInfo");
        Cell<SideMenuView> sideMenuCell = root.add(gameInfoView);
        sideMenuManager.setViewsCell(sideMenuCell);
        sideMenuCell.padLeft(48).prefWidth(240);

        root.row();
        root.add();

        Table letters = new Table();
        for (int i = 0; i < 8; ++i) {
            Label letter = new Label(Character.toString((char) ('A' + i)), labelStyle);
            letter.setAlignment(center);
            letters.add(letter).prefSize(44);
        }
        root.add(letters);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public Color getClearColor() {
        return COLOR_BLACK;
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
