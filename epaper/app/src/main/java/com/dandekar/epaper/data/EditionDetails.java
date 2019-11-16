package com.dandekar.epaper.data;

public final class EditionDetails {

    private Publication publication;
    private Edition edition;
    private String skin;
    private int imageResource;
    private String shortPath;

    public EditionDetails(Publication publication, Edition edition, String skin, int imageResource, String shortPath) {
        this.publication = publication;
        this.edition = edition;
        this.skin = skin;
        this.imageResource = imageResource;
        this.shortPath = shortPath;
    }

    public Edition getEdition() {
        return edition;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getShortPath() {
        return shortPath;
    }

    public Publication getPublication() {
        return publication;
    }

    public String getSkin() {
        return skin;
    }

    @Override
    public String toString() {
        return "EditionDetails{" +
                "publication=" + publication +
                ", edition=" + edition +
                ", skin='" + skin + '\'' +
                ", imageResource=" + imageResource +
                ", shortPath='" + shortPath + '\'' +
                '}';
    }
}
