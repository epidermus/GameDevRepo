package com.mygdx.game.B2D.PresentationPractice;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2D.Finished.CameraShake;
import com.mygdx.game.B2D.Starter.B2DPongBody;
import com.mygdx.game.B2D.Starter.B2DWallBuilder;

public class B2DPong extends ApplicationAdapter {
	public static B2DPong instance;

	SpriteBatch batch;
	Vector2 size = new Vector2(16, 9); // meters
	World world;
	OrthographicCamera camera;
	Box2DDebugRenderer b2dr;
	B2DPaddle leftPaddle;
	B2DPaddle rightPaddle;
	B2DPongBody[] walls;
	B2DBall ball;
	CameraShake shake;

	@Override
	public void create () {
		instance = this;

		batch = new SpriteBatch();

		world = new World(new Vector2(0, -9.8f), false);
		world.setContactListener(new B2DContactListener());

		camera = new OrthographicCamera();
		camera.viewportHeight = size.y;
		camera.viewportWidth = size.x;
		camera.position.set(Vector3.Zero);
		camera.update();

		b2dr = new Box2DDebugRenderer();

		Vector2 paddleSize = new Vector2(.3f, 1.5f);
		leftPaddle = new B2DPaddle(world, "left paddle", paddleSize, Color.CORAL);
		rightPaddle = new B2DPaddle(world, "right paddle", paddleSize, Color.CORAL);

		float wallOffset = 2f;
		leftPaddle.setPosition(new Vector2(-size.x / 2 + wallOffset, 0));
		rightPaddle.setPosition(new Vector2(size.x / 2 - wallOffset, 0));

		ball = new B2DBall(world, "ball", new Vector2(.1f, .1f), Color.TEAL);

		walls = B2DWallBuilder.buildWalls(world, size);

		shake = new CameraShake(camera, 1000, 1, 50);

		ball.randomServe();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		float delta = Gdx.graphics.getDeltaTime();
		world.step(delta, 6, 2);

		shake.update();

		handleInput();

		batch.begin();

		leftPaddle.draw(batch);
		rightPaddle.draw(batch);
		ball.draw(batch);

		for (B2DPongBody wall : walls)
			wall.draw(batch);

		batch.end();

		b2dr.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void handleInput(){
		float leftInput = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.W)){
			leftInput += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)){
			leftInput -= 1;
		}
		leftPaddle.setVelocity(leftInput);

		float rightInput = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			rightInput += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			rightInput -= 1;
		}
		rightPaddle.setVelocity(rightInput);
	}
}
