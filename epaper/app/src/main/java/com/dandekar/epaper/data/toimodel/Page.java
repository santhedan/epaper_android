package com.dandekar.epaper.data.toimodel;

import java.util.List;

public final class Page {

    private String l;
    private boolean sfr;
    private String sk;
    private List<PageItem> en;

    public String getL() {
        return l;
    }

    public boolean isSfr() {
        return sfr;
    }

    public String getSk() {
        return sk;
    }

    public List<PageItem> getEn() {
        return en;
    }

    public void setL(String l) {
        this.l = l;
    }

    public void setSfr(boolean sfr) {
        this.sfr = sfr;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public void setEn(List<PageItem> en) {
        this.en = en;
    }

    @Override
    public String toString() {
        return "Page{" +
                "l='" + l + '\'' +
                ", sfr=" + sfr +
                ", sk='" + sk + '\'' +
                ", en=" + en +
                '}';
    }
}
