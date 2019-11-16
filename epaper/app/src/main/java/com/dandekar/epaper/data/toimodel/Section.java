package com.dandekar.epaper.data.toimodel;

import java.io.Serializable;
import java.util.List;

public final class Section  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<Integer> pages;

    public String getName() {
        return name;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public Section(String name, List<Integer> pages) {
        this.name = name;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                '}';
    }
}
