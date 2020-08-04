package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Egg;
import com.oxigenoxide.caramballs.scene.Farm;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.tap;

public class PlaceEgg {
    boolean correctPlace;
    int level;
    boolean isSelected;
    Sprite sprite;

    static final float SPAWNHEIGHT = 30;

    public PlaceEgg() {
        sprite = new Sprite(Res.tex_egg);
    }

    public void update() {
        if (isSelected) {
            correctPlace = MathFuncs.pointInRectangle(tap[0].x, tap[0].y - SPAWNHEIGHT, Main.farm.pos_field.x + Ball_Egg.RADIUS, Main.farm.pos_field.y + Ball_Egg.RADIUS, Farm.FIELDWIDTH - Ball_Egg.RADIUS * 2, Farm.FIELDWIDTH - Ball_Egg.RADIUS * 2);

            if (!Gdx.input.isTouched()) {
                isSelected = false;
                if (correctPlace)
                    buy();
            }

            sprite.setPosition(tap[0].x - sprite.getWidth() / 2, tap[0].y - 5);
        }
    }

    void buy() {
        Main.balls.add(new Ball_Egg(tap[0].x, tap[0].y - SPAWNHEIGHT, level));
        Main.gameData.orbs -= EggShop.price.get(level);
        Main.farm.eggShop.onBuy(level);
        Main.addSoundRequest(ID.Sound.PLOP);
    }

    public void renderShadow(ShapeRenderer sr) {
        if (isSelected && correctPlace) {
            sr.setColor(0, 0, 0, .25f);
            sr.circle(tap[0].x, tap[0].y - SPAWNHEIGHT, 3, 20);
        }
    }

    public void render(SpriteBatch batch) {
        if (isSelected) {
            if (!correctPlace)
                sprite.setColor(1, 1, 1, .25f);
            else
                sprite.setColor(1, 1, 1, 1);
            batch.setShader(Res.shader_palette);
            Main.setPalette(Res.palette_egg[level % EggShop.COLORS]);
            sprite.draw(batch);
            Funcs.setShaderNull(batch);
        }
    }

    public void select(int level, Vector2 pos) {
        this.level = level;
        isSelected = true;
    }
}
