package com.mygdx.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.TimeUtils;

public class Motion {

    float a;
    float b;
    float abDuration;
    float baDuration;
    Interpolation i;

    long startTime;
    boolean active;
    boolean mirror;

    public Motion(float _a, float _b, float _abDuration, boolean _mirror, Interpolation _i){
        a = _a;
        b = _b;
        abDuration = _abDuration;
        baDuration = _abDuration;
        mirror = _mirror;
        i = _i;
    }

    public void setABDuration(float _duration){
        abDuration = _duration;
    }
    public void setBADuration(float _duration){
        baDuration = _duration;
    }

    public void start(){
        startTime = TimeUtils.millis();
        active = true;
    }

    public float update() {
        if (active) {
            long elapsed = TimeUtils.timeSinceMillis(startTime);

            if (elapsed <= abDuration) {
                float ratio = elapsed / abDuration;
                return i.apply(a, b, ratio);
            }

            else if (mirror && elapsed <= abDuration + baDuration){
                float elapsedAdjusted = elapsed - abDuration;
                float ratio = elapsedAdjusted / baDuration;
                return i.apply(b, a, ratio);
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
