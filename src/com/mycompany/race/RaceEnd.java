package com.mycompany.race;

public class RaceEnd {
    private boolean raceEnd = false;

    public synchronized boolean isRaceEnd() {
        return raceEnd;
    }

    public synchronized void setRaceEnd(boolean raceEnd) {
        this.raceEnd = raceEnd;
    }
}
