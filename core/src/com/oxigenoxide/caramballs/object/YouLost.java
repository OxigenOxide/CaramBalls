package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class YouLost {
    TextureRegion tex;
    float height;
    boolean full;
    float moved;
    float timeToMove;
    float timeToMoveBack;
    boolean visible;
    float alpha;
    float extraHeight;
    int y=155;
    float timeToEnd=300;
    float progressToEnd;

    public YouLost( float height) {
        extraHeight=height;
        tex = Res.tex_youlost;
    }

    public void update() {
        height = Math.min(tex.getRegionHeight(), height + 1);
        progressToEnd=1-(timeToEnd/500);
        alpha=progressToEnd;
        if (height == tex.getRegionHeight())
            full = true;
        if(full) {
            timeToEnd=Math.max(0,timeToEnd-1);
            timeToMove+=.1f;
            y=(int)(Main.height/2+85*progressToEnd);
            if(timeToMove>2) {
               moved=(float)Math.sin(timeToMove-2)*(1-progressToEnd);
            }
        }


    }

    public void fadeIn(){

    }
    public void fadeOut(){

    }

    public void render(SpriteBatch batch) {
        Res.shader_a.setUniformf("a", alpha);
        batch.draw(tex, Main.width / 2 - tex.getRegionWidth() / 2, (int) (y - height / 2 + extraHeight), tex.getRegionWidth(), (int) height);

        if(full){
            batch.draw(tex, Main.width / 2 - tex.getRegionWidth() / 2, (int) (y - height / 2 + moved * 14 + extraHeight));
            batch.draw(tex, Main.width / 2 - tex.getRegionWidth() / 2, (int) (y - height / 2 - moved * 14 + extraHeight));
        }
    }
}
