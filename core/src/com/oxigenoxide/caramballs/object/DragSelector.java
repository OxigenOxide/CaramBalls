package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.draggable.Draggable;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.tap;

public class DragSelector {
    Draggable draggable_selected;
    float distanceToSelect = 40;

    public DragSelector() {

    }

    public void update() {
        if (draggable_selected != null) {
            float max = 10;
            float ang = MathFuncs.angleBetweenPoints(draggable_selected.pos_pivot_visual, tap[0]);
            float force = Math.min(max, MathFuncs.distanceBetweenPoints(draggable_selected.pos_pivot_visual, tap[0]));
            draggable_selected.drag(force * (float) Math.cos(ang), force * (float) Math.sin(ang));
            //dragable_selected.drag(0.1f,0);
        }
    }

    public void onRelease() {
        draggable_selected = null;
    }

    public void setSelected(Draggable draggable) {
        draggable_selected = draggable;
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(Color.WHITE);
        if (draggable_selected != null) {
            sr.rectLine(draggable_selected.pos_pivot_visual, tap[0], 3);
            sr.circle(draggable_selected.pos_pivot_visual.x, draggable_selected.pos_pivot_visual.y, 3);
            sr.circle(tap[0].x, tap[0].y, 3);
        }
    }
}
