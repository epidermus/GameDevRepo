package com.mygdx.game.B2D.PresentationPractice;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.TimeUtils;

public class Motion {
    // interpolates from A to B over time

    float a;
    float b;
    float abDuration;
    float baDuration;
    Interpolation i;
    long startTime;
    boolean active;
    boolean mirror;

    public Motion(float _a, float _b, float _abDuration, boolean _mirror, Interpolation _i){
        active = false;
        a = _a;
        b = _b;
        abDuration = _abDuration;
        baDuration = _abDuration;
        mirror = _mirror;
        i = _i;
    }

    public void setAbDuration(float dur){
        abDuration = dur;
    }
    public void setBaDuration(float dur){
        baDuration = dur;
    }

    public void start(){
        active = true;
        startTime = TimeUtils.millis();
    }

    public float update(){
        if (active){
            long elapsed = TimeUtils.timeSinceMillis(startTime);

            if (elapsed <= abDuration){
                float ratio = elapsed / abDuration;
                return i.apply(a, b, ratio);
            }

            else if (mirror && elapsed <= abDuration + baDuration){
                float adjustedElapsed = elapsed - abDuration;
                float ratio = adjustedElapsed / baDuration;
                return i.apply(b, a, ratio);
            }

            else{
                active = false;
                return a;
            }
        }
        else{
            return a;
        }
    }
}
