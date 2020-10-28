package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class Box2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) a.getUserData()).onCollision(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) b.getUserData()).onCollision(a.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
