package com.mygdx.game.B2D.PresentationPractice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2D.Starter.B2DBuilder;
import com.mygdx.game.B2D.Starter.B2DPongBody;

public class B2DPaddle extends B2DPongBody implements B2DCollisionHandler {
    float speed = 5f;

    Motion scaleMotion;

    public B2DPaddle(World _world, String _name, Vector2 _size, Color _color) {
        super(_world, _name, _size, _color);
        scaleMotion = new Motion(1, 2, 100f, true, Interpolation.bounce);
        scaleMotion.setBaDuration(300f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        sprite.setScale(scaleMotion.update());
    }


    @Override
    public void createBody() {
        setBody(B2DBuilder.createPhysicsBox(world, size, 0, false, false, false, this));
    }

    public void setVelocity(float input) {
        body.setLinearVelocity(new Vector2(0, input * speed));
    }

    @Override
    public void beginContact(Object o) {
        if (o instanceof B2DBall) {
            B2DBall b = (B2DBall) o;

            b.body.setLinearVelocity(b.body.getLinearVelocity().add(body.getLinearVelocity()));
            scaleMotion.start();
        }
    }

    @Override
    public void endContact(Object o) {

    }

    @Override
    public void preSolve(Object o) {

    }

    @Override
    public void postSolve(Object o) {

    }
}
