package com.dandekar.epaper.data;

import java.util.List;

public abstract class PublicationDetails {

    private String skin;

    private List<EditionDetails> editionDetails;

    public PublicationDetails(String skin, List<EditionDetails> editionDetails) {
        this.skin = skin;
        this.editionDetails = editionDetails;
    }

    public String getSkin() {
        return skin;
    }

    public List<EditionDetails> getEditionDetails() {
        return editionDetails;
    }
}
