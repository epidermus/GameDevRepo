package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class CameraShake {

    OrthographicCamera cam;

    float overallDuration;
    float intensity;
    float motionDuration;


    private long startTime;

    private Motion2D currentMotion;
    private Motion intensityMotion;

    private Random r;

    public CameraShake(OrthographicCamera _cam, float _overallDuration, float _intensity, float _motionDuration) {
        cam = _cam;
        overallDuration = _overallDuration;
        intensity = _intensity;
        motionDuration = _motionDuration;
        r = new Random();

        intensityMotion = new Motion(intensity, 0, overallDuration, false, Interpolation.linear);
    }

    private Vector2 randomPointInUnitCircle() {
        float theta = r.nextFloat() * 2 * (float)Math.PI;
        return new Vector2((float) Math.cos(theta), (float) Math.sin(theta));
    }

    public void start() {
        startTime = TimeUtils.millis();
        intensityMotion.start();
    }

    public void update() {

        // start new motion if there is still time left and the last motion is finished
        if (currentMotion == null && TimeUtils.timeSinceMillis(startTime) < overallDuration) {
            float ratio = TimeUtils.timeSinceMillis(startTime) / overallDuration;
            float adjustedIntensity = intensityMotion.update();
            Vector2 pos = randomPointInUnitCircle().scl(adjustedIntensity);
            currentMotion = new Motion2D(Vector2.Zero, pos, motionDuration / 2, true, Interpolation.linear);
            currentMotion.start();
        }

        // continue current motion
        else if (currentMotion != null) {
            Vector2 nextPos = currentMotion.update();
            cam.position.set(new Vector3(nextPos.x, nextPos.y, 0));

            // if the current motion is finished
            if (!currentMotion.active){
                currentMotion = null;
            }
        }
    }
}
