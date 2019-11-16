package com.dandekar.epaper.data.toimodel;

import java.io.Serializable;
import java.util.List;

public final class MetaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<String> val;

    public MetaInfo(String name, List<String> val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public List<String> getVal() {
        return val;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String value: val) {
            if (!("true".equals(value))) {
                builder.append(value);
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}
