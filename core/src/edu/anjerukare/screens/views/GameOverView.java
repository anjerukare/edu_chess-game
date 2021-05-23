package edu.anjerukare.screens.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.utils.JumpingButton;

import static edu.anjerukare.Assets.COLOR_LIGHT_WHITE;
import static edu.anjerukare.screens.enums.Team.WHITE;
import static edu.anjerukare.screens.views.GameOverView.GameResult.CHECKMATE;

public class GameOverView extends SideMenuView {

    public enum GameResult { CHECKMATE, STALEMATE, SURRENDER, DRAW }

    private GameResult result = CHECKMATE;
    private Team team = WHITE;

    public GameOverView() {
        Skin skin = Assets.get(Assets.skin);
        BitmapFont smallFont = Assets.get(Assets.smallFont);

        Button resetButton = new JumpingButton("Сыграть снова",
                new LabelStyle(smallFont, COLOR_LIGHT_WHITE), skin);
        resetButton.padLeft(16).padRight(16);
        resetButton.setName("reset");
        buttons.add(resetButton);

        Button backToMenuButton = new JumpingButton("Вернуться в меню",
                new LabelStyle(smallFont, COLOR_LIGHT_WHITE), skin);
        backToMenuButton.padLeft(16).padRight(16);
        backToMenuButton.setName("backToMenu");
        buttons.add(backToMenuButton);

        initialize("", "", true);
    }

    public void setResult(GameResult result) {
        this.result = result;
        updateLabels();
    }

    public void setTeam(Team team) {
        this.team = team;
        updateLabels();
    }

    private void updateLabels() {
        switch (result) {
            case CHECKMATE:
                if (team == WHITE) {
                    setHeaderText("Победа белых!");
                    setLabelText("Чёрным был поставлен мат");
                } else {
                    setHeaderText("Победа чёрных!");
                    setLabelText("Белым был поставлен мат");
                }
                break;
            case STALEMATE:
                setHeaderText("Объявлена ничья");
                setLabelText("Партия закончилась патом");
                break;
            case SURRENDER:
                if (team == WHITE) {
                    setHeaderText("Победа белых!");
                    setLabelText("Чёрные сдались");
                } else {
                    setHeaderText("Победа чёрных!");
                    setLabelText("Белые сдались");
                }
                break;
            case DRAW:
                setHeaderText("Объявлена ничья");
                setLabelText("По решению игроков");
                break;
        }
    }
}
