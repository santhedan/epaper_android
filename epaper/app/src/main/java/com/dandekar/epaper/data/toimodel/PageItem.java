package com.dandekar.epaper.data.toimodel;

import java.util.ArrayList;
import java.util.List;

public final class PageItem {

    private int roi;
    private int tid;
    private int toc;
    private int wc;
    private List<MetaInfo> meta;
    private List<Object> pr = new ArrayList<Object>();
    private Snimg snimg;
    private String cid;
    private String eap;
    private String ep;
    private String f;
    private String id;
    private String n;
    private String nc;
    private String sk;
    private String sn;
    private String t;
    private String vm;

    public int getRoi() {
        return roi;
    }

    public int getTid() {
        return tid;
    }

    public int getToc() {
        return toc;
    }

    public int getWc() {
        return wc;
    }

    public List<MetaInfo> getMeta() {
        return meta;
    }

    public List<Object> getPr() {
        return pr;
    }

    public Snimg getSnimgObject() {
        return snimg;
    }

    public String getCid() {
        return cid;
    }

    public String getEap() {
        return eap;
    }

    public String getEp() {
        return ep;
    }

    public String getF() {
        return f;
    }

    public String getId() {
        return id;
    }

    public String getN() {
        return n;
    }

    public String getNc() {
        return nc;
    }

    public String getSk() {
        return sk;
    }

    public String getSn() {
        return sn;
    }

    public String getT() {
        return t;
    }

    public String getVm() {
        return vm;
    }

    public void setRoi(int roi) {
        this.roi = roi;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setToc(int toc) {
        this.toc = toc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    public void setMeta(List<MetaInfo> meta) {
        this.meta = meta;
    }

    public void setPr(List<Object> pr) {
        this.pr = pr;
    }

    public void setSnimgObject(Snimg snimg) {
        this.snimg = snimg;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setEap(String eap) {
        this.eap = eap;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public void setF(String f) {
        this.f = f;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setN(String n) {
        this.n = n;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setVm(String vm) {
        this.vm = vm;
    }

    @Override
    public String toString() {
        return "PageItem{" +
                "roi=" + roi +
                ", tid=" + tid +
                ", toc=" + toc +
                ", wc=" + wc +
                ", meta=" + meta +
                ", pr=" + pr +
                ", Snimg=" + snimg +
                ", cid='" + cid + '\'' +
                ", eap='" + eap + '\'' +
                ", ep='" + ep + '\'' +
                ", f='" + f + '\'' +
                ", id='" + id + '\'' +
                ", n='" + n + '\'' +
                ", nc='" + nc + '\'' +
                ", sk='" + sk + '\'' +
                ", sn='" + sn + '\'' +
                ", t='" + t + '\'' +
                ", vm='" + vm + '\'' +
                '}';
    }
}
