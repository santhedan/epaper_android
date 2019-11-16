package com.dandekar.epaper.data;

public final class CurrentSelection {

    // Path will use shortpath/year/month/day form
    private static final String pathFormat = "/%s/%d/%d/%d/";

    private String skin;
    private String shortPath;
    private int year;
    private int month;
    private int day;
    private int random;
    private String pathToSave;

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
        //
        this.pathToSave = String.format(pathFormat, shortPath, year, month, day);
    }

    public String getPathToSave() {
        // This method returns the path where the downloaded content will be stored
        // for the currently selected publication and edition
        return this.pathToSave;
    }
}
