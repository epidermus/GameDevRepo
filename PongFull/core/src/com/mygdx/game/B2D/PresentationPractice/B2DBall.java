package com.mygdx.game.B2D.PresentationPractice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2D.Starter.B2DBuilder;
import com.mygdx.game.B2D.Starter.B2DPongBody;
import com.mygdx.game.Bodies.PongBody;

public class B2DBall extends B2DPongBody implements B2DCollisionHandler {

    float speed = 10f;

    public B2DBall(World _world, String _name, Vector2 _size, Color _color) {
        super(_world, _name, _size, _color);

        body.getFixtureList().get(0).setRestitution(1);
    }

    @Override
    public void createBody() {
        setBody(B2DBuilder.createPhysicsBox(world, size, 0, false, false, false, this));
    }

    public void randomServe(){
        float x = Math.random() < .5 ? -1 : 1;
        float y = ((float)Math.random() * 2f) - 1f;

        Vector2 vel = new Vector2(x, y);

        body.setLinearVelocity(vel.scl(speed));
    }

    @Override
    public void beginContact(Object o) {
        if (o instanceof B2DPongBody){
            B2DPongBody b = (B2DPongBody)o;

            if (b.name.equals("left wall")){
                B2DPong.instance.shake.start();
                Gdx.app.log("collision", "right paddle scored!");
            }
            else if (b.name.equals("right wall")){
                B2DPong.instance.shake.start();
                Gdx.app.log("collision", "left paddle scored!");
            }
        }
    }

    @Override
    public void endContact(Object o) {
        // guarantees constant speed
        body.setLinearVelocity(body.getLinearVelocity().nor().scl(speed));
    }

    @Override
    public void preSolve(Object o) {

    }

    @Override
    public void postSolve(Object o) {

    }
}
