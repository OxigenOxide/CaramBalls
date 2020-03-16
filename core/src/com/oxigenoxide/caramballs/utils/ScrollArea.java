package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.oxigenoxide.caramballs.Main;

public class ScrollArea {

    private float scrolled, scrollMax, scrolledFraction;
    private float x, y, width, height;
    private boolean inArea;

    public ScrollArea(int x, int y, int width, int height, int scrollMax) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scrollMax = Math.max(0, scrollMax);
    }

    public void update() {
        inArea = MathFuncs.pointInRectangle(Main.tap_begin[0].x, Main.tap_begin[0].y, x, y, width, height);
        if (inArea && !Gdx.input.justTouched() && Gdx.input.isTouched()) {
            scrolled += Main.tap_delta[0].y;
            scrolled = MathUtils.clamp(scrolled, 0, scrollMax);
        }
        scrolledFraction = scrolled / scrollMax;
    }

    public float getFraction() {
        return scrolledFraction;
    }

    public float getScrolled() {
        return scrolled;
    }

    public boolean isInArea() {
        return inArea;
    }
}
