package com.oxigenoxide.caramballs.utils;

import java.util.ArrayList;

public class UserData {
    public long timePlayed;
    public long highscore;
    public int adsClicked;
    public int gamesPlayed;
    public ArrayList<Boolean> ballsUnlocked;
    public int orbsCollected;
    public int orbs;
    public ArrayList<String> daysPlayed;

    public UserData(){
        ballsUnlocked=new ArrayList<Boolean>();
        daysPlayed=new ArrayList<String>();
    }
}
