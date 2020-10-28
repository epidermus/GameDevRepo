package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class ScaleAnimator {

    public float deltaScale = 2f;
    public float timeToExpand = 100f; // ms
    public float timeToContract = 300f; // ms

    private long startTime = 0;

    public boolean active = false;

    private Sprite sprite;
    private Interpolation interpolation;

    public ScaleAnimator(Sprite s, Interpolation i) {
        sprite = s;
        interpolation = i;
    }

    public void update(Vector2 realPos) {
        if (active) {
            float scale = 1f;
            long elapsed = TimeUtils.timeSinceMillis(startTime);

            // animate expansion
            if (elapsed <= timeToExpand) {
                float ratio = elapsed / timeToExpand;
                scale =  interpolation.apply(1, deltaScale, ratio);
            }

            // animate contraction
            else if (elapsed <= timeToExpand + timeToContract) {
                float ratio = elapsed / (timeToExpand + timeToContract);
                scale = interpolation.apply(deltaScale, 1, ratio);
            }

            // stop animating
            else //if (elapsed > timeToExpand + timeToContract)
            {
                active = false;
                scale = 1f;
            }

            applyScale(scale, realPos);
        }
    }

    void applyScale(float scale, Vector2 realPos) {
        sprite.setScale(scale);

        // reposition sprite down and left

        float newWidth = scale * sprite.getWidth();
        float newHeight = scale * sprite.getHeight();

        float xOffset = (newWidth - sprite.getWidth()) / -2;
        float yOffset = (newHeight - sprite.getHeight()) / -2;

        sprite.setPosition(realPos.x + xOffset, realPos.y + yOffset);
    }

    public void startAnimation() {
        startTime = TimeUtils.millis();
        active = true;
    }
}
