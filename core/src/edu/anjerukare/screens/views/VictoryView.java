package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;
import static com.badlogic.gdx.utils.Align.bottom;
import static edu.anjerukare.Assets.bigFont;
import static edu.anjerukare.Assets.smallFont;

public class VictoryView extends Table {

    public enum GameResult { CHECKMATE, STALEMATE }

    public GameResult result;
    public Team team;

    public VictoryView() {
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
                    headerText = "Победили белые!";
                    descriptionText = "Королю чёрных был поставлен мат";
                } else {
                    headerText = "Победили чёрные!";
                    descriptionText = "Королю белых был поставлен мат";
                }
                break;
            case STALEMATE:
                headerText = "Объявлена ничья";
                descriptionText = "Текущий игрок не имеет ходов";
                break;
        }

        Label header = new Label(headerText, new LabelStyle(Assets.get(bigFont), null));
        add(header).height(40);
        row();

        Label description = new Label(descriptionText, new LabelStyle(Assets.get(smallFont), null));
        add(description);
        row();

        TypingLabel reset = new TypingLabel("{WAIT}{JUMP=0.4;0.05;0.5}\nСыграть снова?\n{ENDJUMP}",
                new LabelStyle(Assets.get(smallFont), null));
        reset.setAlignment(bottom);
        add(reset).height(120);
    }
}
