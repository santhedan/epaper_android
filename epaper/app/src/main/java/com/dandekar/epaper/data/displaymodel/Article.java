package com.dandekar.epaper.data.displaymodel;

import com.dandekar.epaper.data.toimodel.MetaInfo;

import java.io.Serializable;
import java.util.List;

public final class Article  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String snippet;
    private String articleURL;
    private List<MetaInfo> metaInfo;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public List<MetaInfo> getMetaInfo() {
        return metaInfo;
    }

    public String getMetaInfoForDisplay() {
        StringBuilder builder = new StringBuilder();
        for (MetaInfo info : metaInfo) {
            builder.append(info.toString());
            builder.append(" ");
        }
        return builder.toString();
    }

    public Article(String id, String title, String snippet, String articleURL, List<MetaInfo> metaInfo) {
        this.id = id;
        this.title = title;
        this.snippet = snippet;
        this.articleURL = articleURL;
        this.metaInfo = metaInfo;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", articleURL='" + articleURL + '\'' +
                ", metaInfo=" + metaInfo +
                '}';
    }
}
