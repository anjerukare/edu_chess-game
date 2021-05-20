package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;
import static com.badlogic.gdx.utils.Align.bottom;
import static edu.anjerukare.Assets.*;

public class GameOverView extends Table {

    public enum GameResult { CHECKMATE, STALEMATE }

    public GameResult result;
    public Team team;

    public GameOverView() {
        super();
        setFillParent(true);
        setVisible(false);
        setTouchable(enabled);
    }

    public void show() {
        clearChildren();

        setVisible(true);
        String headerText = "", descriptionText = "";
        switch (result) {
            case CHECKMATE:
                if (team == Team.WHITE) {
                    headerText = "Победа белых!";
                    descriptionText = "Чёрным был поставлен мат";
                } else {
                    headerText = "Победа чёрных!";
                    descriptionText = "Белым был поставлен мат";
                }
                break;
            case STALEMATE:
                headerText = "Объявлена ничья";
                descriptionText = "Партия закончилась патом";
                break;
        }

        Label header = new Label(headerText,
                new LabelStyle(Assets.get(bigFont), COLOR_PURE_WHITE));
        add(header).height(40);
        row();

        Label description = new Label(descriptionText,
                new LabelStyle(Assets.get(smallFont), COLOR_PURE_WHITE));
        add(description);
        row();

        TypingLabel reset = new TypingLabel("{WAIT}{JUMP=0.4;0.05;0.5}\nСыграть снова?\n{ENDJUMP}",
                new LabelStyle(Assets.get(smallFont), COLOR_PURE_WHITE));
        reset.setAlignment(bottom);
        add(reset).height(120);
    }
}