package com.oxigenoxide.caramballs.object.entity.orbContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_BallShard;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class OC_Egg extends OrbContainer {

    public OC_Egg() {
        super(Res.tex_blueEgg);
    }

    public OC_Egg(float x, float y, float height) {
        super(x, y, height);
        tex = Res.tex_blueEgg;
        sprite=new Sprite(tex);
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.fixtureDef_egg);
        body.setUserData(this);
    }

    public void update() {
        super.update();
    }

    public void destroy(Ball ball) {
        super.destroy(ball);
        doDispose = true;
        float angle = MathFuncs.angleBetweenPoints(ball.pos, pos);
        float impact = 1;
        for (int i = 0; i < 10; i++) {
            Main.particles.add(new Particle_BallShard(pos.x, pos.y, (float) (angle + Math.random() * Math.PI * 1.2f - Math.PI * .6f), impact * (.5f + (float) Math.random()), Res.eggPalette));
        }
        Main.addSoundRequest(ID.Sound.GLASS, 5, .5f, .5f);
        Game.throwConfetti(pos.x, pos.y);
    }
}
