package com.mygdx.game.B2D.Finished;

import com.badlogic.gdx.physics.box2d.*;

public class _B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) a.getUserData()).beginContact(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) b.getUserData()).beginContact(a.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) a.getUserData()).endContact(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) b.getUserData()).endContact(a.getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) a.getUserData()).preSolve(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) b.getUserData()).preSolve(a.getUserData());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) a.getUserData()).postSolve(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof _B2DCollisionHandler){
            ((_B2DCollisionHandler) b.getUserData()).postSolve(a.getUserData());
        }
    }
}
