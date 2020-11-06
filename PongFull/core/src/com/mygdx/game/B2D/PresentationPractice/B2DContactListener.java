package com.mygdx.game.B2D.PresentationPractice;

import com.badlogic.gdx.physics.box2d.*;

public class B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && a.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler ach = (B2DCollisionHandler) a.getUserData();
            ach.beginContact(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler bch = (B2DCollisionHandler) b.getUserData();
            bch.beginContact(a.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && a.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler ach = (B2DCollisionHandler) a.getUserData();
            ach.endContact(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler bch = (B2DCollisionHandler) b.getUserData();
            bch.endContact(a.getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && a.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler ach = (B2DCollisionHandler) a.getUserData();
            ach.preSolve(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler bch = (B2DCollisionHandler) b.getUserData();
            bch.preSolve(a.getUserData());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && a.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler ach = (B2DCollisionHandler) a.getUserData();
            ach.postSolve(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof B2DCollisionHandler) {
            B2DCollisionHandler bch = (B2DCollisionHandler) b.getUserData();
            bch.postSolve(a.getUserData());
        }
    }
}
