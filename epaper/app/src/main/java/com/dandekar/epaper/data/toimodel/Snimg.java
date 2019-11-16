package com.dandekar.epaper.data.toimodel;

public final class Snimg {

    private String id;
    private String ext;
    private String kind;
    private float res;
    private String pr;

    public Snimg(String id, String ext, String kind, float res, String pr) {
        this.id = id;
        this.ext = ext;
        this.kind = kind;
        this.res = res;
        this.pr = pr;
    }

    public String getId() {
        return id;
    }

    public String getExt() {
        return ext;
    }

    public String getKind() {
        return kind;
    }

    public float getRes() {
        return res;
    }

    public String getPr() {
        return pr;
    }

    @Override
    public String toString() {
        return "Snimg{" +
                "id='" + id + '\'' +
                ", ext='" + ext + '\'' +
                ", kind='" + kind + '\'' +
                ", res=" + res +
                ", pr='" + pr + '\'' +
                '}';
    }
}
