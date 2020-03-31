package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.oxigenoxide.caramballs.Main;

public class DataManager {

    private static DataManager ourInstance = new DataManager();

    public GameData gameData;
    private Json json = new Json();
    private FileHandle fileHandle;

    private DataManager() {
        if (!Main.inHTML)
            fileHandle = Gdx.files.local("bin/gamedata.json");
    }

    public void initializeGameData() {
        if (Main.inHTML) {
            gameData = new GameData();
            gameData.setTestValues();
            return;
        }
        if (!fileHandle.exists()) {
            gameData = new GameData();
            saveData();
        } else {
            loadData();
        }
        // REMOVE IN PUBLISHED VERSION
    }

    public void saveData() {
        if (Main.inHTML)
            return;

        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),
                    false);
            Gdx.files.local("gameData.json").writeString(json.prettyPrint(gameData), false);
        }
    }

    public void loadData() {
        gameData = json.fromJson(GameData.class,
                Base64Coder.decodeString(fileHandle.readString()));
    }

    public static DataManager getInstance() {
        return ourInstance;
    }
}
