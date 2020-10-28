package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class B2DPaddle implements CollisionHandler {

    public final Vector2 size = new Vector2(10, 40);
    public final float speed = 500000f;

    private Texture texture;
    private Sprite sprite;

    public Vector2 position = Vector2.Zero;
    public Body body;


    public Rectangle rect;

    private ScaleAnimator anim;


    public B2DPaddle() {
        rect = new Rectangle(position.x, position.y, size.x, size.y);
        texture = TextureBuilder.createTexture((int) size.x, (int) size.y, Color.CYAN);
        sprite = new Sprite();
        sprite.setTexture(texture);
        sprite.setSize(size.x, size.y);

        anim = new ScaleAnimator(sprite, Interpolation.linear);
    }


    public void createBody(World world) {
        body = B2DBuilder.createPhysicsBox(world, size, position, 0, false, false, false, (short) 1, (short) 1, this);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void TranslateY(float input) {
        SetPosition(position.add(new Vector2(0, input * speed)));
    }

    public void setYVelocity(float input) {
        body.setLinearVelocity(0, input * speed);
    }

    public void update(float delta) {
        updatePosition();

        anim.update(position);
    }

    public void updatePosition() {
        Vector2 nonBodyPos = body.getPosition().sub(new Vector2(size.x / 2, size.y / 2));

        position = nonBodyPos;
        rect.setPosition(nonBodyPos);
        sprite.setPosition(nonBodyPos.x, nonBodyPos.y);
    }

    public void SetPosition(Vector2 pos) {
        position = pos;
        rect.setPosition(pos);
        sprite.setPosition(pos.x, pos.y);


        body.setTransform(new Vector2(position.x + size.x / 2, position.y + size.y / 2), 0);
    }

    @Override
    public void onCollision(Object userData) {
        //if (userData instanceof B2DBall) {
            anim.startAnimation();
        //}
    }
}
