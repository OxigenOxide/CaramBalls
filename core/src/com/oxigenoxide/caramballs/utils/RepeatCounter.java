package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;

import java.util.List;

import javax.xml.bind.Marshaller;

public class RepeatCounter {
    ActionListener listener;
    float time;
    float count;
    public RepeatCounter(ActionListener listener, float time){
        this.listener=listener;
        this.time=time;
        count=time;
    }

    public void update(){
        count -= Gdx.graphics.getDeltaTime();
        if(count<0){
            count = time-count;
            listener.action();
        }
    }
}


