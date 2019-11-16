package com.dandekar.epaper.data.toimodel;

import java.util.List;

public final class Section {

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
