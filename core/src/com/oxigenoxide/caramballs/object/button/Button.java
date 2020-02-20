package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;

public class Button {

    int type;
    public Vector2 pos;
    public boolean isTouched;
    int touchOffset;
    boolean hidden;
    TextureRegion tex;
    TextureRegion tex_pressed;
    boolean firstFrame = true;
    public boolean individualRender;
    public boolean act;
    public boolean released;
    boolean pressed;

    public Button(Vector2 pos) {
        this.pos = pos;
    }

    public void update() {
        if (!(Main.tap[0].x > pos.x && Main.tap[0].x < pos.x + tex.getRegionWidth() && Main.tap[0].y > pos.y && Main.tap[0].y < pos.y + tex.getRegionHeight())) {
            touchOffset = 0;
            if (isTouched) {
                isTouched = false;
                Main.addSoundRequest(ID.Sound.BUTTONCLICK_1);
            }
        }

        if (isTouched) {
            touchOffset = tex.getRegionHeight() - tex_pressed.getRegionHeight();
        } else {
            touchOffset = 0;
        }
        act = false;

        released = false;
        if (isTouched) {
            pressed = true;
        } else if (pressed) {
            pressed = false;
            released = true;
        }

    }

    public boolean isTouching() {

        if (!hidden) {
            if (Main.tap[0].x > pos.x && Main.tap[0].x < pos.x + tex.getRegionWidth() && Main.tap[0].y > pos.y && Main.tap[0].y < pos.y + tex.getRegionHeight()) {
                Main.isButtonPressed=true;
                if (Gdx.input.justTouched()) {
                    if (!isTouched) {
                        isTouched = true;
                        Main.addSoundRequest(ID.Sound.BUTTONCLICK_0);
                    }
                } else if (!Gdx.input.isTouched() && isTouched) {
                    action();
                    isTouched = false;
                    Main.addSoundRequest(ID.Sound.BUTTONCLICK_1);
                    touchOffset=0;
                }
                else{
                    touchOffset=0;
                }
                return true;
            }
            else {
                isTouched = false;
                touchOffset=0;
            }
        }
        return false;
    }

    public void action() {
        act = true;
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            hidden = false;
        } else {
            hidden = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!hidden) {
            if (isTouched && tex_pressed != null) {
                batch.draw(tex_pressed, pos.x - tex_pressed.getRegionWidth()/2+tex.getRegionWidth()/2, pos.y);
            } else if (tex != null) {
                batch.draw(tex, pos.x, pos.y);
            }
        }
    }

    public void setPosCenterX(float x, float y) {
        pos.set(x - tex.getRegionWidth() / 2, y);
    }


}
