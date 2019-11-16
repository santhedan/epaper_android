package com.dandekar.epaper.data.toimodel;

import java.io.Serializable;
import java.util.List;

public final class Publication implements Serializable {

    private boolean aips;
    private boolean hasFootnotes;
    private boolean rtl;
    private int defaultPageHeight;
    private int defaultPageResolution;
    private int defaultPageWidth;
    private int pagesCount;
    private int previewResolution;
    private List <Object> page_layer_img;
    private List<Object> meta;
    private List<Object> page_img;
    private List<Page> pages;
    private List<Section> sections;
    private List<TOCItem> toc;
    private PublishInfo PublishObject;
    private String date;
    private String href;
    private String language;
    private String location;
    private String modified;
    private String name;
    private String pdf;
    private String publication;
    private String publicationTitle;
    private String sk;
    private String storageFormat;
    private String version;
    private String vm;

    //
    private String[] pageNames;

    public String[] getPageNames() {
        return pageNames;
    }

    public void setPageNames(String[] pageNames) {
        this.pageNames = pageNames;
    }

    private List<com.dandekar.epaper.data.displaymodel.Page> displayPages;

    public List<com.dandekar.epaper.data.displaymodel.Page> getDisplayPages() {
        return displayPages;
    }

    public void setDisplayPages(List<com.dandekar.epaper.data.displaymodel.Page> displayPages) {
        this.displayPages = displayPages;
    }

    public boolean isAips() {
        return aips;
    }

    public boolean isHasFootnotes() {
        return hasFootnotes;
    }

    public boolean isRtl() {
        return rtl;
    }

    public int getDefaultPageHeight() {
        return defaultPageHeight;
    }

    public int getDefaultPageResolution() {
        return defaultPageResolution;
    }

    public int getDefaultPageWidth() {
        return defaultPageWidth;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getPreviewResolution() {
        return previewResolution;
    }

    public List<Object> getPage_layer_img() {
        return page_layer_img;
    }

    public List<Object> getMeta() {
        return meta;
    }

    public List<Object> getPage_img() {
        return page_img;
    }

    public List<Page> getPages() {
        return pages;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<TOCItem> getToc() {
        return toc;
    }

    public PublishInfo getPublishObject() {
        return PublishObject;
    }

    public String getDate() {
        return date;
    }

    public String getHref() {
        return href;
    }

    public String getLanguage() {
        return language;
    }

    public String getLocation() {
        return location;
    }

    public String getModified() {
        return modified;
    }

    public String getName() {
        return name;
    }

    public String getPdf() {
        return pdf;
    }

    public String getPublication() {
        return publication;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public String getSk() {
        return sk;
    }

    public String getStorageFormat() {
        return storageFormat;
    }

    public String getVersion() {
        return version;
    }

    public String getVm() {
        return vm;
    }

    public void setAips(boolean aips) {
        this.aips = aips;
    }

    public void setHasFootnotes(boolean hasFootnotes) {
        this.hasFootnotes = hasFootnotes;
    }

    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }

    public void setDefaultPageHeight(int defaultPageHeight) {
        this.defaultPageHeight = defaultPageHeight;
    }

    public void setDefaultPageResolution(int defaultPageResolution) {
        this.defaultPageResolution = defaultPageResolution;
    }

    public void setDefaultPageWidth(int defaultPageWidth) {
        this.defaultPageWidth = defaultPageWidth;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public void setPreviewResolution(int previewResolution) {
        this.previewResolution = previewResolution;
    }

    public void setPage_layer_img(List<Object> page_layer_img) {
        this.page_layer_img = page_layer_img;
    }

    public void setMeta(List<Object> meta) {
        this.meta = meta;
    }

    public void setPage_img(List<Object> page_img) {
        this.page_img = page_img;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setToc(List<TOCItem> toc) {
        this.toc = toc;
    }

    public void setPublishObject(PublishInfo publishObject) {
        PublishObject = publishObject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public void setStorageFormat(String storageFormat) {
        this.storageFormat = storageFormat;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setVm(String vm) {
        this.vm = vm;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "aips=" + aips +
                ", hasFootnotes=" + hasFootnotes +
                ", rtl=" + rtl +
                ", defaultPageHeight=" + defaultPageHeight +
                ", defaultPageResolution=" + defaultPageResolution +
                ", defaultPageWidth=" + defaultPageWidth +
                ", pagesCount=" + pagesCount +
                ", previewResolution=" + previewResolution +
                ", page_layer_img=" + page_layer_img +
                ", meta=" + meta +
                ", page_img=" + page_img +
                ", pages=" + pages +
                ", sections=" + sections +
                ", toc=" + toc +
                ", PublishObject=" + PublishObject +
                ", date='" + date + '\'' +
                ", href='" + href + '\'' +
                ", language='" + language + '\'' +
                ", location='" + location + '\'' +
                ", modified='" + modified + '\'' +
                ", name='" + name + '\'' +
                ", pdf='" + pdf + '\'' +
                ", publication='" + publication + '\'' +
                ", publicationTitle='" + publicationTitle + '\'' +
                ", sk='" + sk + '\'' +
                ", storageFormat='" + storageFormat + '\'' +
                ", version='" + version + '\'' +
                ", vm='" + vm + '\'' +
                '}';
    }
}
