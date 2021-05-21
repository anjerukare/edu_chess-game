package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.utils.JumpingButton;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;
import static com.badlogic.gdx.utils.Align.bottom;
import static edu.anjerukare.Assets.*;
import static edu.anjerukare.screens.enums.Team.WHITE;
import static edu.anjerukare.screens.views.GameOverView.GameResult.*;

public class GameOverView extends SideMenuView {

    public enum GameResult { CHECKMATE, STALEMATE, SURRENDER }

    private GameResult result = CHECKMATE;
    private Team team = WHITE;

    public GameOverView() {
        Label buttonLabel = new Label("Сыграть снова",
                new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE));
        buttonLabel.setTouchable(disabled);
        Button resetButton = new JumpingButton(buttonLabel, Assets.get(skin));
        resetButton.padLeft(16).padRight(16);
        resetButton.setName("reset");
        buttons.add(resetButton);

        initialize();
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
        }
    }
}
