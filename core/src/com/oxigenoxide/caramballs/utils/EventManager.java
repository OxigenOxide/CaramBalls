package com.oxigenoxide.caramballs.utils;

import com.oxigenoxide.caramballs.Main;

public class EventManager {
    int event;
    float count;
    int countMax;
    float progress;
    EventListener eventListener;
    boolean doCount;

    public EventManager(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public EventManager(EventListener eventListener, boolean start) {
        this.eventListener = eventListener;
        if (start)
            start();
    }

    public void update() {
        if (doCount) {
            count -= Main.dt_one;
            if (count <= 0) {
                int resp = eventListener.onEvent(event);
                if (resp == -1) {
                    doCount=false;
                    progress=1;
                    return;
                }
                countMax = resp;
                count = countMax;
                event++;
            }
            progress = (countMax - count) / countMax;
        }
    }

    public EventManager start() {
        doCount = true;
        count = 0;
        event = 0;
        return this;
    }

    public int getCountMax() {
        return countMax;
    }

    public float getCount() {
        return count;
    }

    public float getProgress() {
        return progress;
    }

    public float getEvent() {
        return event;
    }
}
