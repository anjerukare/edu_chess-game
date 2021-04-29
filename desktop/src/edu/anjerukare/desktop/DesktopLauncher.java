package edu.anjerukare.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import edu.anjerukare.Chess;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		/* equals: config.setTitle("Шахматы") */
		config.setTitle("\u0428\u0430\u0445\u043c\u0430\u0442\u044b");
		config.setWindowedMode(800, 480);
		new Lwjgl3Application(new Chess(), config);
	}
}
