package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.math.MathUtils;
import com.oxigenoxide.caramballs.ID;

public class SoundRequest {
    public int soundID;
    public int priority=5;
    public float volume=1;
    public float pitch=1;

    public SoundRequest(int soundID, int priority, float volume,float pitch){
        this.soundID=soundID;
        this.priority=priority;
        this.volume= MathUtils.clamp(volume,0,1);
        this.pitch = pitch;
    }
    public SoundRequest(int soundID, int priority, float volume){
        this.soundID=soundID;
        this.priority=priority;
        this.volume= MathUtils.clamp(volume,0,1);
        pitch = getStandardPitch();
    }
    public SoundRequest(int soundID, int priority){
        this.soundID=soundID;
        this.priority=priority;
        volume = getStandardVolume();
        pitch = getStandardPitch();
    }
    public SoundRequest(int soundID){
        this.soundID=soundID;
        volume = getStandardVolume();
        pitch = getStandardPitch();
    }

    float getStandardVolume(){
        switch(soundID){
            case ID.Sound.HIT:
                return 1;
        }
        return 1;
    }
    float getStandardPitch(){
        switch(soundID){
            case ID.Sound.HIT:
                return (float) Math.random() * .2f + .9f;
        }
        return 1;
    }
}
