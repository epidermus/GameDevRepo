package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.PongGame;
import com.mygdx.game.B2D.PresentationPractice.B2DPong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = PongGame.SCREEN_HEIGHT;
		config.width = PongGame.SCREEN_WIDTH;
		config.title = "Pong - Full Version";
		new LwjglApplication(new B2DPong(), config);
	}
}
