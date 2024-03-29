package com.dandekar.epaper.data;

public abstract class Constants {
    public static final String TAG = "epaper_app";
    public static final String PUBLICATION = "Publication";
    public static final String XMLURL = "XMLURL";
    public static final String SHARED_PREF_NAME = "CookiePref";
    public static final String COOKIE_KEY = "Cookie";
    public static final String USERNAME_KEY = "Username";
    //https://epaper.timesgroup.com/Olive/ODN/TimesOfIndia/get/TOIM-2019-09-27/prxml.ashx?kind=doc&href=TOIM%2F2019%2F09%2F27&snippets=true&ts=20190927003412
    public  static final String getXMLURL = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/prxml.ashx?kind=doc&href=%s/%d/%02d/%02d&snippets=true&ts=%d%02d%02d%d";
    public static final String ARTICLE_URL = "ARTICLE_URL";

    // SHORT_PATH + YEAR + MONTH + DAY + PAGE NO
    public static final String ThumbnailFileNameFormat = "TN_%s_%d_%d_%d_%d.jpg";
    // SHORT_PATH + YEAR + MONTH + DAY + PAGE NO + ARTICLE ID
    public static final String ArticleFileNameFormat = "ART_%s_%d_%d_%d_%s.html";
    // SHORT_PATH + YEAR + MONTH + DAY
    public static final String PublicationFileNameFormat = "PUB_%s_%d_%d_%d.pub";
    public static final String ARTICLE_TITLE = "ARTICLE_TITLE";
    public static final String PAGE_NUMBER = "PAGE_NUMBER";
    public static final String PAGE_NAMES = "PAGE_NAMES";
    public static final String PAGE_COUNT = "PAGE_COUNT";

    public static String ARTICLE_ID ="ARTICLE_ID";

    //https://epaper.timesgroup.com/Olive/ODN/TimesOfIndia/get/TOIM-2019-12-01/image.ashx?href=TOIM%2F2019%2F12%2F01&page=44&kind=picture&res=120&ts=20191201011050
    public static String FORMAT_URL_PICTURE = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/image.ashx?href=%s/%d/%02d/%02d&page=%s&kind=picture&res=%d&ts=20191201011050";;

    //https://epaper.timesgroup.com/Olive/ODN/TimesOfIndia/get/TOIM-2019-12-01/image.ashx?href=TOIM%2F2019%2F12%2F01&page=44&kind=text&res=120&ts=20191201011050
    public static String FORMAT_URL_TEXT = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/image.ashx?href=%s/%d/%02d/%02d&page=%s&kind=text&res=%d&ts=20191201011050";

}
