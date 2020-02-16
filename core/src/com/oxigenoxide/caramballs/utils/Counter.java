package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;

public class Counter {
    ActionListener listener;
    float time;
    float count;
    boolean doCount;

    public Counter(ActionListener listener, float time) {
        this.listener = listener;
        this.time = time;
    }

    public Counter(float time) {
        this.time = time;
    }

    public Counter start() {
        count = time;
        doCount=true;
        return this;
    }

    public void update() {
        if(doCount) {
            count -= Gdx.graphics.getDeltaTime();
            if (count < 0) {
                count = 0;
                doCount=false;
                if (listener != null)
                    listener.action(); // fix this before using listener
            }
        }
    }

    public float getCount() {
        return count;
    }

    public float getProgress() {
        return (time - count) / time;
    }
}
