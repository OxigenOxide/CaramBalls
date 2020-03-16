package com.oxigenoxide.caramballs.utils;

import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;

import java.util.ArrayList;

/**
 * Created by Frederik on 5/19/2019.
 */
public class GameData {
    public int highscore=0;
    public boolean[] unlocks=new boolean[1];
    public int selectedBall=0;
    public int orbs=0;
    public int testerID=0;
    public UserData userData;
    public boolean isSoundMuted;
    public boolean isMusicMuted;
    public long time_leftFarm;
    public ArrayList<Ball_Main.Ball_Main_Data> farmBalls;
    public GameData(){
        unlocks[0]=true;
        farmBalls=new ArrayList<Ball_Main.Ball_Main_Data>();
    }
}
