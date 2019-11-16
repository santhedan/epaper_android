package com.dandekar.epaper.data.toimodel;

import java.io.Serializable;
import java.util.List;

public final class TOCItem  implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean ic;
    private boolean ip;
    private boolean is;
    private int g;
    private int id;
    private List<TOCItem> i;
    private String e;
    private String p;
    private String t;

    public boolean isIc() {
        return ic;
    }

    public boolean isIp() {
        return ip;
    }

    public boolean isIs() {
        return is;
    }

    public int getG() {
        return g;
    }

    public int getId() {
        return id;
    }

    public List<TOCItem> getI() {
        return i;
    }

    public String getE() {
        return e;
    }

    public String getP() {
        return p;
    }

    public String getT() {
        return t;
    }

    public void setIc(boolean ic) {
        this.ic = ic;
    }

    public void setIp(boolean ip) {
        this.ip = ip;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setI(List<TOCItem> i) {
        this.i = i;
    }

    public void setE(String e) {
        this.e = e;
    }

    public void setP(String p) {
        this.p = p;
    }

    public void setT(String t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "TOCItem{" +
                "ic=" + ic +
                ", ip=" + ip +
                ", is=" + is +
                ", g=" + g +
                ", id=" + id +
                ", i=" + i +
                ", e='" + e + '\'' +
                ", p='" + p + '\'' +
                ", t='" + t + '\'' +
                '}';
    }
}
