package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Shop;

public class ShopSpot extends Button {

    public boolean isUnlocked;
    public boolean isSelected;
    int type = 0;
    int price = 420;
    Vector2 pos_num;
    int scrolled;
    Vector2 pos_original;

    public ShopSpot(Vector2 pos, int type) {
        super(pos);
        pos_original = new Vector2(pos);
        tex = Res.tex_shopSpot;
        tex_pressed = Res.tex_shopSpotPressed;
        pos_num = new Vector2(pos.x + 14, pos.y + 6);
        this.type = type;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void action() {
        if (isUnlocked) {
            Main.shop.select(type);
            isSelected = true;
        }

        if (!isUnlocked) {
            if (Main.gameData.orbs >= price) {
                isUnlocked = true;
                Main.gameData.orbs -= price;
                Main.shop.onPurchase();
            }
        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setScrolled(int scrolled){
        this.scrolled=scrolled;
        pos.set(pos_original.x,pos_original.y+scrolled);
    }
    @Override
    public void render(SpriteBatch batch) {

        if (isUnlocked) {
            super.render(batch);
            pos_num.set(pos.x + 14, pos.y + 6 - touchOffset);
            int level = 0;
            batch.setShader(Res.shader_palette);
            Main.setPalette(Res.ballPalette[level]);
            batch.draw(Res.tex_ball[type][1], pos.x + 14 - Res.tex_ball[type][1].getRegionWidth() / 2, pos.y + 24 - Res.tex_ball[type][1].getRegionHeight() / 2 - touchOffset);
            batch.setShader(null);
            if (isSelected)
                batch.draw(Res.tex_symbolSelected, pos.x + 7, pos.y + 4 - touchOffset);
        } else {
            batch.draw(Res.tex_shopSpotEmpty,pos.x,pos.y);
        }
    }
}
