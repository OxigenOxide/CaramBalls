package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class ShopSpot extends Button {

    public boolean isUnlocked;
    public boolean isSelected;
    int type = 0;
    int price = 420;
    Vector2 pos_num;
    int scrolled;
    Vector2 pos_original;
    int rarity;

    public ShopSpot(Vector2 pos, int type) {
        super(pos);
        pos_original = new Vector2(pos);
        tex = Res.tex_shopSpot[0];
        tex_pressed = Res.tex_shopSpotPressed[0];
        pos_num = new Vector2(pos.x + 14, pos.y + 6);
        this.type = type;
    }

    @Override
    public void action() {
        if (isUnlocked) {
            Main.shop.select(type);
            isSelected = true;
        }
    }

    public void unlock() {
        isUnlocked = true;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
        tex = Res.tex_shopSpot[rarity];
        tex_pressed = Res.tex_shopSpotPressed[rarity];
    }

    public int getPrice() {
        return price;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setScrolled(int scrolled) {
        this.scrolled = scrolled;
        pos.set(pos_original.x, pos_original.y + scrolled);
    }

    @Override
    public boolean isTouching() {
        if(isSelected)
            return false;
        return super.isTouching();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isSelected) {
            batch.draw(Res.tex_shopSpotPressed[rarity], pos.x, pos.y);
            pos_num.set(pos.x + 14, pos.y + 6 - touchOffset);
            batch.setShader(Res.shader_palette);
            Main.setPalette(Res.PALETTE_WHITEBALL);
            batch.draw(Res.tex_ball[type][1], pos.x + 14 - Res.tex_ball[type][1].getRegionWidth() / 2, pos.y + 20 - Res.tex_ball[type][1].getRegionHeight() / 2);
            batch.setShader(null);
            return;
        }
        if (isUnlocked) {
            super.render(batch);
            pos_num.set(pos.x + 14, pos.y + 6 - touchOffset);
            batch.setShader(Res.shader_palette);
            Main.setPalette(Res.PALETTE_WHITEBALL);
            batch.draw(Res.tex_ball[type][1], pos.x + 14 - Res.tex_ball[type][1].getRegionWidth() / 2, pos.y + 22 - Res.tex_ball[type][1].getRegionHeight() / 2 - touchOffset);
            batch.setShader(null);
            return;
        }
        batch.draw(Res.tex_shopSpotEmpty[rarity], pos.x, pos.y);
    }
}
