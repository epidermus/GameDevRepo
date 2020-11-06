package com.mygdx.game.B2D.Finished;

public interface _B2DCollisionHandler {

    void beginContact(Object userData);
    void endContact(Object userData);
    void preSolve(Object userData);
    void postSolve(Object userData);
}
