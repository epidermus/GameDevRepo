package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;

public class pbPaddle extends PongBody implements CollisionHandler {

    private final float speed = 5f;

    private Motion scalingMotion;

    public pbPaddle(World _world, String _name, Vector2 _size, Color _color) {
        super(_world, _name, _size, _color);

        scalingMotion = new Motion(1f, .5f, 250, true, Interpolation.elastic);
        scalingMotion.setABDuration(75f);
    }

    public void setYVelocity(float input) {
        body.setLinearVelocity(0, input * speed);
    }

    @Override
    public void createBody() {
        body = B2DBuilder.createPhysicsBox(world, size, 0, false, false, false, this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        // sprite.setScale(scaleInterpolator.update());
        sprite.setScale(scalingMotion.update());
    }

    @Override
    public void beginContact(Object userData) {
        if (userData instanceof pbBall) {
            // scaleInterpolator.startAnimation();
            scalingMotion.start();

            pbBall b = (pbBall) userData;

            // the ball will scale its own velocity anyway
            b.body.setLinearVelocity(b.body.getLinearVelocity().add(body.getLinearVelocity()));
        }
    }

    @Override
    public void endContact(Object userData) {

    }

    @Override
    public void preSolve(Object userData) {

    }

    @Override
    public void postSolve(Object userData) {

    }
}
