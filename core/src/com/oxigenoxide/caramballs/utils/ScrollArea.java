package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.oxigenoxide.caramballs.Main;

public class ScrollArea {

    private float scrolled, scrollMax, scrolledFraction;
    private float x, y, width, height;
    private boolean inArea;
    private float scrolledTarget;
    private boolean approachTarget;
    private boolean locked;


    public ScrollArea(int x, int y, int width, int height, int scrollMax) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scrollMax = Math.max(0, scrollMax); // sometimes the computed scroll area is too small to be scrollable
    }

    public void update(float dt) {
        if(!locked) {
            inArea = MathFuncs.pointInRectangle(Main.tap_begin[0].x, Main.tap_begin[0].y, x, y, width, height);
            if (inArea && !Gdx.input.justTouched() && Gdx.input.isTouched())
                setScrolled(scrolled + Main.tap_delta[0].y);
        }

        if(approachTarget) {
            float dst = scrolledTarget - scrolled;
            scrolled = scrolled + dst * (1 - (float) Math.pow(.001f, dt));
            if(Math.abs(dst) < 1) {
                scrolled = scrolledTarget;
                approachTarget = false;
            }
        }

        scrolledFraction = scrolled / scrollMax;
    }

    public float getFraction() {
        return scrolledFraction;
    }

    public float getScrolled() {
        return scrolled;
    }

    public void setScrolled(float scrolled) {
        this.scrolled = MathUtils.clamp(scrolled, 0, scrollMax);
    }

    public void lock(){
        locked=true;
    }

    public void unlock(){
        locked=false;
    }

    public void setScrolledTarget(float scrolledTarget) {
        this.scrolledTarget = MathUtils.clamp(scrolledTarget, 0, scrollMax);
        approachTarget = true;
    }

    public boolean isInArea() {
        return inArea;
    }
}
