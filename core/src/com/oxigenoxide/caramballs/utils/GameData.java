package com.oxigenoxide.caramballs.utils;

import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Egg;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;

import java.util.ArrayList;

/**
 * Created by Frederik on 5/19/2019.
 */
public class GameData {
    public int highscore = 0;
    public boolean[] unlocks = new boolean[1];
    public int selectedBall = 0;
    public int orbs = 0;
    public int testerID = 0;
    public UserData userData;
    public boolean isSoundMuted;
    public boolean isMusicMuted;
    public long time_leftFarm;
    public ArrayList<Ball_Main.Ball_Main_Data> farmBalls;
    public ArrayList<Ball_Egg.Ball_Egg_Data> eggBalls;
    public ArrayList<Integer> eggsBought;
    public int eggsUnlocked = 0;
    public int livesBought = 0;

    public GameData() {
        unlocks[0] = true;
        farmBalls = new ArrayList<Ball_Main.Ball_Main_Data>();
        farmBalls.add(new Ball_Main.Ball_Main_Data(Main.width/2,Main.height/2+20,0,0,0));
        eggBalls = new ArrayList<Ball_Egg.Ball_Egg_Data>();
        eggsBought = new ArrayList<Integer>();
    }

    public void setTestValues() {
        orbs = 1000;
    }
}
