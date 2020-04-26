package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oxigenoxide.caramballs.Main;

public class Animation {
    public float count;
    public int countMax;
    int indices;
    public boolean isContinuous;
    boolean doLoop;
    TextureRegion[] textures;
    float[] durations;
    public boolean ended;

    public Animation(int countMax, TextureRegion[] textures, float[] durations, boolean isContinuous) {
        this.countMax = countMax;
        this.textures = textures;
        this.isContinuous = isContinuous;
        indices = textures.length;
        float totDur = 0;
        for (float f : durations)
            totDur += f;
        this.durations = new float[durations.length];
        for (int i = 0; i < durations.length; i++)
            this.durations[i] = durations[i] / totDur;
    }

    public void update(float dt) {
        count += dt;
        if (count >= countMax) {
            if (isContinuous || doLoop) {
                count = 0;
                doLoop = false;
            } else
                ended = true;
        } else
            ended = false;
    }

    public void update() {
        update(Main.dt_one);
    }

    public void loop() {
        if (count >= countMax)
            doLoop = true;
    }

    float duration;
    float countPart;

    public TextureRegion getTexture() {
        if (!ended) {
            duration = 0;
            countPart = count / (float) countMax;
            for (int i = 0; i < durations.length; i++) {
                if (countPart >= duration && countPart <= duration + durations[i])
                    return textures[i];
                duration += durations[i];
            }
        }
        return textures[0];
    }
}
