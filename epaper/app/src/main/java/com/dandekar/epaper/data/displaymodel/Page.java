package com.dandekar.epaper.data.displaymodel;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public final class Page  implements Serializable {

    private static final long serialVersionUID = 1L;

    private int position;
    private String name;
    private String thumbnailURL;
    private List<Article> articles;
    private transient Bitmap thumbnail;

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public Page(int position, String name, String thumbnailURL, List<Article> articles) {
        this.position = position;
        this.name = name;
        this.thumbnailURL = thumbnailURL;
        this.articles = articles;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString() {
        return "Page{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                ", articles=" + articles +
                '}';
    }
}
