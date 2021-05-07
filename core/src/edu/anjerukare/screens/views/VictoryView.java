package edu.anjerukare.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;

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
    }

    public void show() {
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
    }
}
