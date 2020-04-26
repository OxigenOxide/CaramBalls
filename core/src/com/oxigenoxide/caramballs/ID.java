package com.oxigenoxide.caramballs;

public class ID {
    public static class TutorialStage {
        public static final int PIN = 0;
        public static final int MOREPINS = 1;
        public static final int MERGE = 2;
        public static final int SLOWDOWN = 3;
    }

    public static class Place {
        public static final int DEFAULT = 0;
        public static final int SPACE = 1;
    }

    public static class Sound {
        public static final int SLOWDOWN = 0;
        public static final int PLOP = 1;
        public static final int HIT = 2;
        public static final int SPEEDUP = 3;
        public static final int GLASS = 4;
        public static final int SPLAT = 5;
        public static final int BOUNCE = 6;
        public static final int BUTTONCLICK_0 = 7;
        public static final int BUTTONCLICK_1 = 8;
        public static final int COLLECT = 9;
        public static final int SUCCESS = 10;
        public static final int PUNCH = 11;
        public static final int CORRECT = 12;
        public static final int SPLIT = 13;
        public static final int POP = 14;
        public static final int FAIL = 15;
        public static final int TAP = 16;
        public static final int ALARM = 17;
    }

    public static class Entity {
        public static final int BALL = 0;
        public static final int COLLECTABLE = 1;
        public static final int HOLE = 2;
        public static final int ORBCONTAINER = 3;
        public static final int PARTICLE = 4;
        public static final int BALLCAPSULE = 5;
        public static final int BULLET = 6;
        public static final int CANNON = 7;
        public static final int CAT = 8;
        public static final int CIRCULARBUMPER = 9;
        public static final int EYE = 10;
        public static final int FLOORBUTTON = 11;
        public static final int HONEY = 12;
        public static final int JUMPINGPAD = 13;
        public static final int SPIKE = 14;
        public static final int DRAGGABLE = 15;
        public static final int SCOOPER = 16;
        public static final int POWERORBENTITY = 17;
        public static final int AMOUNT = 18; // change this when adding IDs
    }

    public static class Font {
        public static final int NORMAL = 0;
        public static final int POP = 1;
        public static final int POP_LARGE = 2;
        public static final int SMALL = 3;
        public static final int FIELD = 4;
    }

    public static class Rarity {
        public static final int COMMON = 0;
        public static final int RARE = 1;
        public static final int LEGENDARY = 2;
    }

    public static class Fruit {
        public static final int APPLE = 0;
        public static final int LEMON = 1;
        public static final int STRAWBERRY = 2;
    }

    public static class Difficulty {
        public static final int INTRO = 0;
        public static final int EASY = 1;
        public static final int NORMAL = 2;
        public static final int HARD = 3;
        public static final int INSANE = 4;
    }


    public static class Level {
        public static final int SPIKES_INTRO = 0;
        public static final int CIRCULARBUMPERS = 1;
        public static final int SCOOPER_SPIKES = 2;
        public static final int SCOOPER_NORMAL = 3;
        public static final int SCOOPER_LEFT = 4;
        public static final int SCOOPER_CROSS = 5;
        public static final int SPIKES_HORIZONTAL = 6;
        public static final int OBSTACLEBALLS = 7;
        public static final int SCOOPERHOLES = 8;
        public static final int CANNONBOX = 9;
        public static final int TIRE_BADBALLS = 10;
        public static final int SPIKES_ALLAROUND = 11;
        public static final int BREATHER = 12;
        public static final int EMPTY = 13;
        public static final int SPIKES_EASY = 14;
        public static final int SPIKES_NORMAL = 15;
        public static final int SPIKES_HARD = 16;
        public static final int SPIKES_BUMPERS = 17;
        public static final int BADBALLS = 18;
        public static final int BADBALLS_BUMPERS = 19;
        public static final int BADBALLS_SPIKE = 20;
        public static final int SPIKELINES = 21;
        public static final int SPIKECLUSTERS = 22;
        public static final int CANNONS = 23;
        public static final int SPIKES_INSANE = 24;
    }
}
