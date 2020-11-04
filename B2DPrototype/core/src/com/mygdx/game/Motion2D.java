package com.mygdx.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Motion2D {

    Vector2 a;
    Vector2 b;
    float abDuration;
    float baDuration;
    Interpolation i;

    long startTime;
    boolean active;
    boolean mirror;

    public Motion2D(Vector2 _a, Vector2 _b, float _abDuration, boolean _mirror, Interpolation _i){
        a = _a;
        b = _b;
        abDuration = _abDuration;
        baDuration = _abDuration;
        mirror = _mirror;
        i = _i;
    }

    public void setAToBDuration(float _duration){
        abDuration = _duration;
    }
    public void setBToADuration(float _duration){
        baDuration = _duration;
    }

    void start(){
        active = true;
        startTime = TimeUtils.millis();
    }

    public Vector2 update() {
        if (active) {
            long elapsed = TimeUtils.timeSinceMillis(startTime);

            if (elapsed <= abDuration) {
                float ratio = elapsed / abDuration;
                float x = i.apply(a.x, b.x, ratio);
                float y = i.apply(a.y, b.y, ratio);
                return new Vector2(x, y);
            }

            else if (mirror && elapsed <= abDuration + baDuration){
                float elapsedAdjusted = elapsed - abDuration;
                float ratio = elapsedAdjusted / baDuration;
                float x = i.apply(b.x, a.x, ratio);
                float y = i.apply(b.y, a.y, ratio);
                return new Vector2(x, y);
            }

            // stop animating
            else //if (elapsed > timeToExpand + timeToContract)
            {
                active = false;
                return a;
            }
        }

        else
            return a;
    }
}
