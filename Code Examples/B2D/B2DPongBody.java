package com.mygdx.game.B2D.Starter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2D.Starter.B2DBuilder;
import com.mygdx.game.B2D.Starter.TextureBuilder;

public class B2DPongBody {
    public Vector2 size;
    public String name;

    protected Texture texture;
    protected Sprite sprite;
    protected World world;

    public Vector2 position = Vector2.Zero;

    public Body body;

    public B2DPongBody(World _world, String _name, Vector2 _size, Color _color) {
        size = _size;
        name = _name;
        world = _world;


        texture = TextureBuilder.createTexture(1, 1, _color);
        sprite = new Sprite();
        sprite.setTexture(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOriginCenter();
        position = Vector2.Zero;

        createBody();

        setPosition(Vector2.Zero);
    }

    public void draw(SpriteBatch batch) {
        updatePosition();
        sprite.draw(batch);
    }

    public void createBody() {
        setBody(B2DBuilder.createPhysicsBox(world, size, 0, true, false, false, this));
    }

    public void setBody(Body b) {
        if (body != null) {
            world.destroyBody(body);
        }
        body = b;
    }

    public void updatePosition() {
        position = body.getPosition();
        sprite.setPosition(position.x - size.x / 2, position.y - size.y / 2);
        //sprite.setPosition(position.x, position.y);
    }

    public void setPosition(Vector2 pos) {
        body.setTransform(pos, 0);
        updatePosition();
    }



}
