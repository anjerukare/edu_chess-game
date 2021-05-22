package edu.anjerukare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition;
import edu.anjerukare.screens.GameScreen;
import edu.anjerukare.screens.MainMenuScreen;

public class Chess extends ManagedGame<ManagedScreen, ScreenTransition> {

	public SpriteBatch batch;

	@Override
	public void create() {
		super.create();
		batch = new SpriteBatch();

		Assets.load();
		Assets.finishLoading();

		screenManager.addScreen("mainMenu", new MainMenuScreen());
		screenManager.addScreen("game", new GameScreen());

		BlendingTransition blendingTransition = new BlendingTransition(batch, .5f);
		screenManager.addScreenTransition("blending", blendingTransition);
		GLTransitionsShaderTransition fallingBarsTransition = new GLTransitionsShaderTransition(2, Interpolation.smoother);
		fallingBarsTransition.compileGLTransition(Gdx.files.internal("falling-bars.glsl").readString());
		screenManager.addScreenTransition("fallingBars", fallingBarsTransition);

		screenManager.pushScreen("mainMenu", "blending");
	}
	
	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
		batch.dispose();
	}
}
