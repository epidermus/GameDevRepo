package com.mygdx.game;

public interface CollisionHandler {

    void beginContact(Object userData);
    void endContact(Object userData);
    void preSolve(Object userData);
    void postSolve(Object userData);
}
