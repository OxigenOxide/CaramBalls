package com.oxigenoxide.caramballs.object.entity.orbContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;

public class OC_Fruit extends OrbContainer {

    public OC_Fruit() {
        super(Res.getRandomFruitTex());
    }

    public OC_Fruit(float x, float y, float height) {
        super(x, y, height);
        tex = Res.getRandomFruitTex();
        sprite=new Sprite(tex);
    }

    public OC_Fruit(float x, float y) {
        super(x, y);
        tex = Res.getRandomFruitTex();
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
        Main.addSoundRequest(ID.Sound.SPLAT, 5, 1, .5f);
        Game.throwConfetti(pos.x, pos.y);
    }
}
