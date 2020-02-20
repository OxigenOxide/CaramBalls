package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
    public int count;
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
        for (float f : durations) {
            totDur += f;
        }
        this.durations = new float[durations.length];
        for (int i = 0; i < durations.length; i++) {
            this.durations[i] = durations[i] / totDur;
        }
    }

    public void update() {
        count++;
        if (count >= countMax) {
            if (isContinuous || doLoop) {
                count = 0;
                doLoop = false;
            } else {
                ended = true;
            }
        } else
            ended = false;

    }

    public void doLoop() {
        if (count >= countMax) {
            doLoop = true;
        }
    }

    float duration;
    float countPart;

    public TextureRegion getTexture() {
        if (ended) {
            return textures[0];
        }
        duration = 0;
        countPart = count / (float) countMax;
        for (int i = 0; i < durations.length; i++) {
            if (countPart >= duration && countPart <= duration + durations[i]) {
                return textures[i];
            }
            duration += durations[i];
        }

        return textures[0];
    }
}
