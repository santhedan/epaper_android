package com.dandekar.epaper.data.toimodel;

public final class PublishInfo {

    private String publisher;
    private String xmdVer;
    private String acrobatVer;

    public String getPublisher() {
        return publisher;
    }

    public String getXmdVer() {
        return xmdVer;
    }

    public String getAcrobatVer() {
        return acrobatVer;
    }

    public PublishInfo(String publisher, String xmdVer, String acrobatVer) {
        this.publisher = publisher;
        this.xmdVer = xmdVer;
        this.acrobatVer = acrobatVer;
    }

    @Override
    public String toString() {
        return "PublishInfo{" +
                "publisher='" + publisher + '\'' +
                ", xmdVer='" + xmdVer + '\'' +
                ", acrobatVer='" + acrobatVer + '\'' +
                '}';
    }
}
