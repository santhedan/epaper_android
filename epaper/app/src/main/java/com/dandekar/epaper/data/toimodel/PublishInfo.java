package com.dandekar.epaper.data.toimodel;

import java.io.Serializable;

public final class PublishInfo  implements Serializable {

    private static final long serialVersionUID = 1L;

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
