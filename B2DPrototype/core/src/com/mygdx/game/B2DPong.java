package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class B2DPong extends ApplicationAdapter {

	// ref https://github.com/libgdx/libgdx/wiki/Orthographic-camera

	private SpriteBatch batch;

	B2DPaddle leftPaddle;
	B2DPaddle rightPaddle;
	B2DBall ball;

	OrthographicCamera cam;

	World world;
	Box2DDebugRenderer b2dr;


	@Override
	public void create () {
		batch = new SpriteBatch();

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		// setup box2d world
		world = new World(new Vector2(0, -9.8f), false);
		world.setContactListener(new Box2DContactListener());
		b2dr = new Box2DDebugRenderer();

		// setup camera
		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false, width, height);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();

		// setup paddles
		leftPaddle = new B2DPaddle();
		leftPaddle.createBody(world);

		rightPaddle = new B2DPaddle();
		rightPaddle.createBody(world);

		leftPaddle.body.setFixedRotation(true);
		rightPaddle.body.setFixedRotation(true);

		float wallOffset = 10f;
		leftPaddle.SetPosition(new Vector2(wallOffset, height / 2 - leftPaddle.size.y / 2));
		rightPaddle.SetPosition(new Vector2(width - rightPaddle.size.x - wallOffset, height / 2 - rightPaddle.size.y / 2));

		// setup ball
		ball = new B2DBall();
		ball.createBody(world);
		ball.body.setFixedRotation(true);
		ball.SetPosition(new Vector2(width / 2, height / 2));

		// setup walls
		buildWalls();
	}

	private void buildWalls(){
		Vector2 verticalBoundarySize = new Vector2(Gdx.graphics.getWidth(), 5);
		float verticalBoundaryDistance = (float)Gdx.graphics.getHeight() / 2;
		Vector2 ceilingPosition = centerPosition().add(new Vector2(0, verticalBoundaryDistance));
		Vector2 floorPosition = centerPosition().add(new Vector2(0, -verticalBoundaryDistance));

		Body floor = B2DBuilder.createPhysicsBox(world, verticalBoundarySize, floorPosition, 0, false,
				true, false, (short)1, (short)1, null);
		Body ceiling = B2DBuilder.createPhysicsBox(world, verticalBoundarySize, ceilingPosition, 0, false,
				true, false, (short)1, (short)1, null);

		Vector2 xBoundarySize = new Vector2(5, Gdx.graphics.getHeight());
		float xBoundaryDistance = (float)Gdx.graphics.getWidth() / 2;
		Vector2 leftWallPosition = centerPosition().add(new Vector2(-xBoundaryDistance, 0));
		Vector2 rightWallPosition = centerPosition().add(new Vector2(xBoundaryDistance, 0));

		Body leftWall = B2DBuilder.createPhysicsBox(world, xBoundarySize, leftWallPosition, 0, false,
				true, false, (short)1, (short)1, null);
		Body rightWall = B2DBuilder.createPhysicsBox(world, xBoundarySize, rightWallPosition, 0, false,
				true, false, (short)1, (short)1, null);
	}

	private Vector2 centerPosition(){
		return new Vector2((float)Gdx.graphics.getWidth() / 2, (float)Gdx.graphics.getHeight() / 2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		update(Gdx.graphics.getDeltaTime());

		b2dr.render(world, cam.combined);

		batch.begin();

		leftPaddle.draw(batch);
		rightPaddle.draw(batch);
		ball.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	void update(float delta){
		handleInput();
		world.step(delta, 6, 2);

		leftPaddle.update(delta);
		rightPaddle.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.update();

		// Gdx.app.log("resize input", "(" + width + ", " + height + ")");
		// Gdx.app.log("resize result", "(" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight() + ")");
	}

	void handleInput(){
		float leftInput = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.R)) {
			leftInput += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			leftInput -= 1;
		}
		//leftPaddle.TranslateY(leftInput);
		leftPaddle.setYVelocity(leftInput);

		float rightInput = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.U)) {
			rightInput += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.J)) {
			rightInput -= 1;
		}
		// rightPaddle.TranslateY(rightInput);
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
