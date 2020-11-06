package com.mygdx.game.B2D.PresentationPractice;

public interface B2DCollisionHandler {
    void beginContact(Object o);
    void endContact(Object o);
    void preSolve(Object o);
    void postSolve(Object o);
}
