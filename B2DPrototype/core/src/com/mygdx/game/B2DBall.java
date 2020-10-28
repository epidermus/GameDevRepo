package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class B2DBall {

    final Vector2 size = new Vector2(10, 10);

    private Texture texture;
    private Sprite sprite;

    public Vector2 position;
    public Rectangle rect;

    public Vector2 velocity;
    float speed = 2f;

    public Body body;

    public B2DBall() {
        texture = TextureBuilder.createTexture((int)size.x, (int)size.y, Color.RED);
        sprite = new Sprite();
        sprite.setTexture(texture);
        sprite.setSize(size.x, size.y);

        // set ball position in the middle of the screen
        float x = Gdx.graphics.getWidth() / 2f - size.x / 2f;
        float y = Gdx.graphics.getHeight() / 2f - size.y / 2f;

        position = new Vector2(x, y);
        rect = new Rectangle(position.x, position.y, size.x, size.y);

        float randomY = (float)Math.random();
        // Gdx.app.log("ball", "startY: " + randomY);
        boolean neg = Math.random() < 0.5;
        float _x = neg ? -1 : 1;

        Vector2 direction = new Vector2(_x, randomY).nor();
        // velocity = direction.scl(speed);
    }

    public void createBody(World world) {
        body = B2DBuilder.createPhysicsBox(world, size, position, 0, false, false, false, (short) 1, (short) 1, this);
    }

    private void RestrictPosition(){
        float maxY = Gdx.graphics.getHeight() - size.y;

        if (position.y < 0){
            SetPosition(new Vector2(position.x, 0));
            velocity = new Vector2(velocity.x, -velocity.y);
        }
        else if (position.y > maxY){
            SetPosition(new Vector2(position.x, maxY));
            velocity = new Vector2(velocity.x, -velocity.y);
        }
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    public void UpdatePosition(){
        Vector2 newPos = position.add(velocity);
        SetPosition(newPos);
    }

    public void update(float delta) {
        updatePosition();
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
}
