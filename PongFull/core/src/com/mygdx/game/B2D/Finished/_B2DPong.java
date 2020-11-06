package com.mygdx.game.B2D.Finished;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2D.Starter.B2DBuilder;
import com.mygdx.game.B2D.Starter.B2DPongBody;

public class _B2DPong extends ApplicationAdapter {

    public final Vector2 size = new Vector2(16, 9); // meters

    public static _B2DPong instance;

    private SpriteBatch batch;

    _B2DPaddle leftPaddle;
    _B2DPaddle rightPaddle;
    _B2DBall ball;
    B2DPongBody[] walls;

    OrthographicCamera cam;
    CameraShake shake;

    World world;
    Box2DDebugRenderer b2dr;


    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();

        // setup box2d world
        world = new World(new Vector2(0, -9.8f), false);
        world.setContactListener(new _B2DContactListener());

        b2dr = new Box2DDebugRenderer();

        // setup camera
        // ref https://github.com/libgdx/libgdx/wiki/Orthographic-camera
        cam = new OrthographicCamera();
        cam.viewportHeight = size.y;
        cam.viewportWidth = size.x;
        cam.position.set(0, 0, 0);
        cam.update();
        shake = new CameraShake(cam, 500, .75f, 250);

        // setup paddles
        // leftPaddle = new B2DPaddle();
        //leftPaddle = new B2DPaddle();
        Vector2 paddleSize = new Vector2(.3f, 2f);
        leftPaddle = new _B2DPaddle(world, "left paddle", paddleSize, Color.GOLDENROD);
        rightPaddle = new _B2DPaddle(world, "right paddle", paddleSize, Color.TEAL);

        leftPaddle.setBody(B2DBuilder.createPhysicsBox(world, leftPaddle.size, 0, false, false, false, leftPaddle));
        rightPaddle.setBody(B2DBuilder.createPhysicsBox(world, rightPaddle.size, 0, false, false, false, rightPaddle));

        float wallOffset = 1f; // m
        leftPaddle.setPosition(new Vector2((-size.x / 2) + wallOffset, 0));
        rightPaddle.setPosition(new Vector2((size.x / 2) - wallOffset, 0));

        // setup ball
        Vector2 ballSize = new Vector2(.2f, .2f);
        ball = new _B2DBall(world, "ball", ballSize, Color.WHITE);
        ball.setBody(B2DBuilder.createPhysicsBox(world, ball.size, 0, false, false, false, ball));
        ball.setPosition(Vector2.Zero);

        // make bouncy
        Fixture ballFixture  = ball.body.getFixtureList().get(0);
        ballFixture.setRestitution(1);

        // setup walls
        buildWalls();

        ball.randomServe();
    }

    private B2DPongBody[] buildWalls() {
        walls = new B2DPongBody[4];
        Vector2 yBoundarySize = new Vector2(size.x, 1);
        Vector2 xBoundarySize = new Vector2(1, size.y);
        Vector2 ceilingPosition = new Vector2(0, size.y / 2);
        Vector2 floorPosition = new Vector2(0, -size.y / 2);
        Vector2 leftWallPosition = new Vector2(-size.x / 2, 0);
        Vector2 rightWallPosition = new Vector2(size.x / 2, 0);
        Color wallColor = Color.DARK_GRAY;

        B2DPongBody left = new B2DPongBody(world, "left wall", xBoundarySize, wallColor);
        left.setBody(B2DBuilder.createPhysicsBox(world, left.size, 0, false,
                true, false, left));
        left.setPosition(leftWallPosition);
        walls[0] = left;

        B2DPongBody right = new B2DPongBody(world, "right wall", xBoundarySize, wallColor);
        right.setBody(B2DBuilder.createPhysicsBox(world, right.size, 0, false,
                true, false, right));
        right.setPosition(rightWallPosition);
        walls[1] = right;

        B2DPongBody ceiling = new B2DPongBody(world, "ceiling", yBoundarySize, wallColor);
        ceiling.setBody(B2DBuilder.createPhysicsBox(world, ceiling.size, 0, false,
                true, false, ceiling));
        ceiling.setPosition(ceilingPosition);
        walls[2] = ceiling;

        B2DPongBody floor = new B2DPongBody(world, "floor", yBoundarySize, wallColor);
        floor.setBody(B2DBuilder.createPhysicsBox(world, floor.size, 0, false,
                true, false, floor));
        floor.setPosition(floorPosition);
        walls[3] = floor;

        return walls;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(Gdx.graphics.getDeltaTime());

        cam.update();
        batch.setProjectionMatrix(cam.combined);


        b2dr.render(world, cam.combined);

        batch.begin();

        leftPaddle.draw(batch);
        rightPaddle.draw(batch);
        ball.draw(batch);

        for (B2DPongBody pb : walls){
            pb.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    void update(float delta) {
        handleInput();
        world.step(delta, 6, 2);

        shake.update();
    }

    @Override
    public void resize(int width, int height) {
    }

    void handleInput() {
        float leftInput = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            leftInput += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            leftInput -= 1;
        }
        leftPaddle.setYVelocity(leftInput);

        float rightInput = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            rightInput += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            rightInput -= 1;
        }
        rightPaddle.setYVelocity(rightInput);

        // camera stuff
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        float rotationSpeed = 0.5f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

    }
}
