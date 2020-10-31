package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class B2DPong extends ApplicationAdapter {

    public final Vector2 pSize = new Vector2(16, 9); // meters

    private SpriteBatch batch;

    pbPaddle leftPaddle;
    pbPaddle rightPaddle;
    pbBall ball;
    PongBody[] walls;

    OrthographicCamera cam;

    World world;
    Box2DDebugRenderer b2dr;


    @Override
    public void create() {
        batch = new SpriteBatch();

        // setup box2d world
        world = new World(new Vector2(0, -9.8f), false);
        world.setContactListener(new B2DContactListener());
        b2dr = new Box2DDebugRenderer();

        // setup camera
        // ref https://github.com/libgdx/libgdx/wiki/Orthographic-camera
        cam = new OrthographicCamera();
        cam.viewportHeight = pSize.y;
        cam.viewportWidth = pSize.x;
        cam.position.set(0, 0, 0);
        cam.update();

        // setup paddles
        // leftPaddle = new B2DPaddle();
        //leftPaddle = new B2DPaddle();
        Vector2 paddleSize = new Vector2(.3f, 2f);
        leftPaddle = new pbPaddle(world, "left paddle", paddleSize, Color.GOLDENROD);
        rightPaddle = new pbPaddle(world, "right paddle", paddleSize, Color.TEAL);

        leftPaddle.setBody(B2DBuilder.createPhysicsBox(world, leftPaddle.size, 0, false, false, false, leftPaddle));
        rightPaddle.setBody(B2DBuilder.createPhysicsBox(world, rightPaddle.size, 0, false, false, false, rightPaddle));

        float wallOffset = 1f; // m
        leftPaddle.setPosition(new Vector2((-pSize.x / 2) + wallOffset, 0));
        rightPaddle.setPosition(new Vector2((pSize.x / 2) - wallOffset, 0));

        // setup ball
        Vector2 ballSize = new Vector2(.2f, .2f);
        ball = new pbBall(world, "ball", ballSize, Color.WHITE);
        ball.setBody(B2DBuilder.createPhysicsBox(world, ball.size, 0, false, false, false, ball));
        ball.setPosition(Vector2.Zero);

        // make bouncy
        Fixture ballFixture  = ball.body.getFixtureList().get(0);
        ballFixture.setRestitution(1);

        // setup walls
        buildWalls();

        ball.randomServe();
    }

    private PongBody[] buildWalls() {
        walls = new PongBody[4];
        Vector2 yBoundarySize = new Vector2(pSize.x, 1);
        Vector2 xBoundarySize = new Vector2(1, pSize.y);
        Vector2 ceilingPosition = new Vector2(0, pSize.y / 2);
        Vector2 floorPosition = new Vector2(0, -pSize.y / 2);
        Vector2 leftWallPosition = new Vector2(-pSize.x / 2, 0);
        Vector2 rightWallPosition = new Vector2(pSize.x / 2, 0);
        Color wallColor = Color.DARK_GRAY;

        PongBody left = new PongBody(world, "left wall", xBoundarySize, wallColor);
        left.setBody(B2DBuilder.createPhysicsBox(world, left.size, 0, false,
                true, false, left));
        left.setPosition(leftWallPosition);
        walls[0] = left;

        PongBody right = new PongBody(world, "right wall", xBoundarySize, wallColor);
        right.setBody(B2DBuilder.createPhysicsBox(world, right.size, 0, false,
                true, false, right));
        right.setPosition(rightWallPosition);
        walls[1] = right;

        PongBody ceiling = new PongBody(world, "ceiling", yBoundarySize, wallColor);
        ceiling.setBody(B2DBuilder.createPhysicsBox(world, ceiling.size, 0, false,
                true, false, ceiling));
        ceiling.setPosition(ceilingPosition);
        walls[2] = ceiling;

        PongBody floor = new PongBody(world, "floor", yBoundarySize, wallColor);
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

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        update(Gdx.graphics.getDeltaTime());

        // b2dr.render(world, cam.combined);

        batch.begin();

        leftPaddle.draw(batch);
        rightPaddle.draw(batch);
        ball.draw(batch);

        for (PongBody pb : walls){
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

        leftPaddle.updatePosition();
        rightPaddle.updatePosition();
        ball.updatePosition();
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
