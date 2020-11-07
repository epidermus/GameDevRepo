package com.mygdx.game.B2D.Starter;

public interface B2DCollisionHandler {

    void beginContact(Object userData);
    void endContact(Object userData);
    void preSolve(Object userData);
    void postSolve(Object userData);
}
