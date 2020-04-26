package com.oxigenoxide.caramballs.object.effect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;

public class Effect_BallExplosion extends Effect {

    Animation animation;
    Sprite sprite;

    public Effect_BallExplosion(float x, float y) {

        super(x, y);

        Main.effects_front.add(this);

        sprite = new Sprite(Res.tex_ballExplosion[0]);
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);

        animation = new Animation((int)(20 * Main.test_float), Res.tex_ballExplosion, new float[]{1, 1, 1, 1, 1, 1}, false);

        Main.addSoundRequest(ID.Sound.FAIL,0,1, MathUtils.random(.8f,1.2f));
    }

    public void update() {
        animation.update();
        if (animation.ended)
            dispose();
        sprite.setRegion(animation.getTexture());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        Main.effects_front.remove(this);
        Main.effectsToRemove.add(this);
    }
}
