package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Pong;

public class LoadingScreen implements Screen {
    //instance variables
    private Pong parent;

    public LoadingScreen(Pong pong) {
        parent = pong;

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        parent.changeScreen(Pong.MAIN_MENU);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
