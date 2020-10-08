package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.*;

public class Pong extends Game {

	// Constants for Screens
	public final static int MAIN_MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int PAUSE = 2;
	public final static int GAME_PLAY = 3;
	public final static int ENDGAME = 4;

	// Screens
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MainMenuScreen mainMenuScreen;
	private PauseMenuScreen pauseMenuScreen;
	private EndScreen endScreen;
	private GameplayScreen gameplayScreen;

	private AppPreferences preferences;

	
	@Override
	public void create () {
		preferences = new AppPreferences();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);


	}

//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//
//		batch.end();
//	}
	
	@Override
	public void dispose () {


	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

	public void changeScreen(int screen){
		switch(screen){
			case MAIN_MENU:
				if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this);
				this.setScreen(mainMenuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case PAUSE:
				if(pauseMenuScreen == null) pauseMenuScreen = new PauseMenuScreen(this);
				this.setScreen(pauseMenuScreen);
				break;
			case GAME_PLAY:
				// UNSURE ON THIS ONE following a tutorial
				if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this);
				this.setScreen(mainMenuScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}
}
