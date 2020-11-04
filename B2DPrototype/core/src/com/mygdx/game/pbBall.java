package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class pbBall extends PongBody implements CollisionHandler {

    float speed = 10f;

    public pbBall(World _world, String _name, Vector2 _size, Color _color) {
        super(_world, _name, _size, _color);
    }

    public void randomServe() {
        float randomY = (float) Math.random();
        boolean neg = Math.random() < 0.5;
        float _x = neg ? -1 : 1;

        Vector2 direction = new Vector2(_x, randomY).nor();
        body.setLinearVelocity(direction.scl(speed));
    }

    @Override
    public void createBody() {
        body = B2DBuilder.createPhysicsBox(world, size, 0, false, false, false, this);
    }

    @Override
    public void beginContact(Object userData) {
        if (userData instanceof PongBody) {
            PongBody pb = (PongBody) userData;

            if (pb.name.equalsIgnoreCase("left wall") || pb.name.equalsIgnoreCase("right wall")) {
                B2DPong.instance.shake.start();
                Gdx.app.log("collision", "Someone scored");
            }
        }
    }

    @Override
    public void endContact(Object userData) {
        Vector2 vel = body.getLinearVelocity();
        float currentSpeed = Vector2.len(vel.x, vel.y);
        body.setLinearVelocity(vel.scl(speed / currentSpeed));
    }

    @Override
    public void preSolve(Object userData) {

    }

    @Override
    public void postSolve(Object userData) {

    }
}
