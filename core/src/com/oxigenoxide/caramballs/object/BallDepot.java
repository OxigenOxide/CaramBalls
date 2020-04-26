package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;

import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;

import java.util.ArrayList;

public class BallDepot {

    private ArrayList<DepotBall> balls = new ArrayList<DepotBall>();
    private int ballsToAdd;
    private Sprite sprite_bar;
    private float barProgress;
    private Counter counter_ballsAdded;
    private Counter counter_blink;
    private float hideFactor;
    private boolean ballsHidden;
    private float shake;
    private float shakeDst;
    private float progress_blink;
    private Color color_blink;
    private float ballsSpawned;

    private static final int MAXBALLS = 16;
    private static final Vector2 POS = new Vector2(96, 2);
    private Vector2 pos_line = new Vector2(POS.x + 5, POS.y + 61);
    private Vector2 pos_number = new Vector2(pos_line.x, pos_line.y + 4);
    private float yBallSpawn; // two higher than the last ball's end height\

    private static final int METERHEIGHT = 23;
    private static final int BARHEIGHT = 20; // actually 19
    //private static final float YBALLSPAWN = POS.y + METERHEIGHT + 200; // two higher than the last ball's end height
    private static final float YFIRSTBALL = POS.y + METERHEIGHT + 2;
    private static final float YFOURTHBALL = POS.y + METERHEIGHT + 2 + 27;
    private static final float BOUNCEFACTOR = .25f * 1.1f;
    private static final Color COLOR_BLINKLIGHT = new Color(1, 52 / 255f, 96 / 255f, 1);
    //private static final Color COLOR_BLINKDARK = new Color(215 / 255f, 0, 0, 1);
    private static final Color COLOR_BLINKDARK = new Color(150 / 255f, 19 / 255f, 19 / 255f, 1);

    public BallDepot() {
        reset();
        sprite_bar = new Sprite(Res.tex_ballDepot_bar);
        sprite_bar.setPosition(POS.x + 1, POS.y + 3);
        counter_ballsAdded = new Counter(2);
        counter_blink = new Counter(new ActionListener() {
            @Override
            public void action() {
                Main.addSoundRequest(ID.Sound.ALARM);
                counter_blink.start();
            }
        }, 1).start();
        color_blink = new Color();
        yBallSpawn = Main.height + 10;
    }

    public void update() {
        counter_ballsAdded.update();

        ballsHidden = counter_ballsAdded.getProgress() == 1 && balls.size() >= 4 && balls.get(3).y < YFOURTHBALL + 1;

        if (ballsToAdd > 0) {
            if (balls.size() == 0 || balls.get(balls.size() - 1).getTop() < yBallSpawn) {
                ballsToAdd--;
                balls.add(new DepotBall(balls.size()));
                counter_ballsAdded.start();
            }
        }

        if (ballsHidden)
            hideFactor = Math.min(1, hideFactor + Main.dt);
        else
            hideFactor = Math.max(0, hideFactor - Main.dt);

        for (DepotBall db : balls)
            db.update();


        if (balls.size() != 0 && Game.getTotalBallSize() < 8 && !Game.inBreatherLevel) {
            if (inTerminalPhase())
                barProgress = barProgress + Main.dt_slowed * 2;
            else
                barProgress = barProgress + Main.dt_slowed * .25f;

            if (barProgress >= 1) {
                if (ballsSpawned == 0)
                    Main.balls.add(new Ball_Main(Game.level - 1).makeInvincible());
                else
                    Main.balls.add(new Ball_Main(Game.level - 1));

                ballsSpawned++;
                barProgress = barProgress % 1;
                pop();
            }
        } else
            barProgress = 0;

        if (inTerminalPhase()) {
            counter_blink.update();
            Funcs.combineColors(color_blink, COLOR_BLINKDARK, COLOR_BLINKLIGHT, counter_blink.getProgress());
            if (balls.size() == 0)
                barProgress = 1;
        }

        shake = Math.max(0, shake - Main.dt * 30);
        shakeDst = (float) Math.sin(shake) * shake / 10 * 3;

        sprite_bar.setSize(sprite_bar.getWidth(), barProgress * BARHEIGHT);
        sprite_bar.setPosition(POS.x + 1 + shakeDst, POS.y + 3);
    }

    public void render(SpriteBatch batch) {
        for (DepotBall db : balls)
            db.render(batch);

        batch.draw(Res.tex_ballDepot_back, POS.x + shakeDst + 1, POS.y + 19);
        if (inTerminalPhase()) {
            Funcs.setShader(batch, Res.shader_c);
            Res.shader_c.setUniformf("c", color_blink);
        }
        sprite_bar.draw(batch);
        if (inTerminalPhase())
            Funcs.setShaderNull(batch);

        batch.draw(Res.tex_ballDepot_frame, POS.x + shakeDst, POS.y);

        batch.setColor(1, 1, 1, hideFactor);
        batch.draw(Res.tex_ballDepot_line, POS.x, POS.y + 61);
        Funcs.drawNumber(batch, balls.size(), pos_number, ID.Font.SMALL);
        batch.setColor(Color.WHITE);
    }

    public void reset() {
        balls.clear();
        ballsToAdd = MAXBALLS;
        ballsSpawned = 0;
    }

    public void pop() {
        if (balls.size() > 0) {
            balls.remove(0);
            for (int i = 0; i < balls.size(); i++)
                balls.get(i).index = i;
        }
    }

    public void add(int amount) {
        ballsToAdd += amount;
        hideFactor = .5f; // to help speed it up
    }

    public boolean inTerminalPhase() {
        return Game.getTotalBallSize() + balls.size() + ballsToAdd < 8 && !Game.inBreatherLevel;
    }

    public void resetProgress() {
        barProgress = 0;
        shake();
    }

    void shake() {
        shake = 10;
    }

    class DepotBall {
        Sprite sprite;
        float y;
        float vel;
        int index;
        float floor;

        final static int HEIGHT = 8;

        DepotBall(int index) {
            this.index = index;
            sprite = new Sprite(Res.tex_ballDepot_ball);
            y = yBallSpawn;
            sprite.setPosition(97, y);
        }

        void update() {
            floor = YFIRSTBALL;
            if (index > 0)
                floor = balls.get(index - 1).getTop() + 1;

            if (y > floor)
                vel += -9.81f * Main.PPM * Main.dt;

            y += vel * Main.dt;

            if (y < floor) {
                y = floor;
                vel *= -BOUNCEFACTOR;
                if (vel < 2) {
                    vel = 0;
                    y = floor;
                } else if (vel > 25) {
                    Main.addSoundRequest(ID.Sound.HIT, 0, vel * .00125f);
                }
            }

            if (index > 3)
                sprite.setAlpha(.5f * (1 - hideFactor));
            else
                sprite.setAlpha(1);

            sprite.setPosition(sprite.getX(), y);
        }

        float getTop() {
            return y + HEIGHT;
        }

        void render(SpriteBatch batch) {
            sprite.draw(batch);
        }
    }
}
