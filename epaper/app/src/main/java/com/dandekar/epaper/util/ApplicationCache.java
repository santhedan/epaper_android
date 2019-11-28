package com.dandekar.epaper.util;

import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.data.Edition;
import com.dandekar.epaper.data.Publication;

public final class ApplicationCache {

    public static String cookie = "";

    public static String userName = "";

    public static Publication publication;

    public static Edition edition;

    public static CurrentSelection curSel;

    public static String thumbnailURL = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/image.ashx?kind=preview&href=%s/%d/%02d/%02d&page=%d&ts=%d";

    public static String articleURL = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/article.ashx?href=%s/%d/%02d/%02d&id=%s&ts=%d";
}
