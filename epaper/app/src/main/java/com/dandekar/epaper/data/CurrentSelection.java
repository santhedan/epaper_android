package com.dandekar.epaper.data;

public final class CurrentSelection {

    private String skin;
    private String shortPath;
    private int year;
    private int month;
    private int day;
    private int random;

    public String getSkin() {
        return skin;
    }

    public String getShortPath() {
        return shortPath;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getRandom() {
        return random;
    }

    public CurrentSelection(String skin, String shortPath, int year, int month, int day, int random) {
        this.skin = skin;
        this.shortPath = shortPath;
        this.year = year;
        this.month = month;
        this.day = day;
        this.random = random;
    }
}
