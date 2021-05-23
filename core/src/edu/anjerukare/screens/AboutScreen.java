package edu.anjerukare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.anjerukare.Assets;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.listeners.AboutListener;
import edu.anjerukare.screens.utils.JumpingButton;
import edu.anjerukare.screens.utils.ManagedScreenAdapter;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static com.badlogic.gdx.utils.Align.center;
import static edu.anjerukare.Assets.*;

public class AboutScreen extends ManagedScreenAdapter {

    private final Chess game;
    private final Stage stage;
    private final Table root;

    private final Skin skin;
    private final LabelStyle primaryLabelStyle;
    private final LabelStyle secondaryLabelStyle;

    public AboutScreen() {
        game = (Chess) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(800, 480));
        addInputProcessor(stage);
        stage.addListener(new AboutListener());

        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        skin = Assets.get(Assets.skin);
        primaryLabelStyle = new LabelStyle(Assets.get(smallFont), COLOR_LIGHT_WHITE);
        secondaryLabelStyle = new LabelStyle(Assets.get(smallFont), COLOR_WHITE);

        buildRootTable();
    }

    private void buildRootTable() {
        Label title = new Label("Об игре", new LabelStyle(Assets.get(bigFont), COLOR_LIGHT_WHITE));
        title.setAlignment(center);
        root.add(title).padBottom(26).row();

        Label description = new Label(
                "Hotseat-версия классических шахмат для двух игроков, " +
                "не требующая каких-либо знаний правил игры, так как доступные ходы " +
                "отображаются непосредственно после проверки на правильность.",
                primaryLabelStyle
        );
        description.setWrap(true);
        description.setAlignment(center);
        root.add(description).prefWidth(520).padBottom(18).row();

        Label email = new Label("Почта для обратной связи: anjerukare@gmail.com", primaryLabelStyle);
        email.setAlignment(center);
        root.add(email).padBottom(34).row();

        root.add(getSpecialThanksTable()).padBottom(26).row();

        root.add(getButtonsTable());
    }

    private Table getSpecialThanksTable() {
        Table table = new Table();

        Label thanks = new Label("Выражаю особую благодарность этим проектам:", primaryLabelStyle);
        thanks.setAlignment(center);
        table.add(thanks).padBottom(22).row();

        table.add(getAuthorsTable()).padBottom(22).row();

        Label clickable = new Label("(Надписи кликабельны)", primaryLabelStyle);
        clickable.setAlignment(center);
        table.add(clickable).row();

        return table;
    }

    private Table getButtonsTable() {
        Table buttons = new Table();

        Button backButton = new JumpingButton("Вернуться", primaryLabelStyle, skin);
        backButton.setName("back");
        backButton.padLeft(16).padRight(16);
        buttons.add(backButton).padRight(16);

        Image github = new Image(skin.getDrawable("github"));
        github.setTouchable(disabled);
        Button githubButton = new JumpingButton(github, skin);
        githubButton.setName("github");
        buttons.add(githubButton);

        return buttons;
    }

    private Table getAuthorsTable() {
        Table authorsTable = new Table();

        authorsTable.add(getAuthorsTableItem("Pixel Art Chess", "PAC",
                "Ajay Karat", "СС0 1.0 License", "CC010"));
        authorsTable.add(getAuthorsTableItem("Screen Transition", "ST",
                "Zeh Fernando", "Mit License", "MIT"));
        authorsTable.add(getAuthorsTableItem("Typing Label", "TL",
                "Rafael Skoberg", "Mit License", "MIT"));
        authorsTable.add(getAuthorsTableItem("Screen Manager", "SM",
                "crykn a.k.a. damios", "Apache-2.0 License", "A20L"));

        return authorsTable;
    }

    private Table getAuthorsTableItem(String projectName, String projectNameShort,
                                      String author, String licenseName, String licenseNameShort) {
        Table table = new Table();
        table.padLeft(18).padRight(18);

        Label projectLabel = new Label(projectName, primaryLabelStyle);
        projectLabel.setAlignment(center);
        projectLabel.setName(projectNameShort);
        table.add(projectLabel).padBottom(8).row();

        Label authorLabel = new Label(author, secondaryLabelStyle);
        authorLabel.setAlignment(center);
        table.add(authorLabel).padBottom(8).row();

        Label licenseLabel = new Label(licenseName, secondaryLabelStyle);
        licenseLabel.setAlignment(center);
        licenseLabel.setName(licenseNameShort);
        table.add(licenseLabel);

        return table;
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
