package com.dandekar.epaper.data;

import com.dandekar.epaper.R;

import java.util.ArrayList;
import java.util.List;

public final class Mirror extends PublicationDetails {

    private static final String skin = "MumbaiMirror";
    private static  final Publication publication = Publication.Mirror;

    private static final List<EditionDetails> editions = new ArrayList<EditionDetails>();

    static {
        editions.add(new EditionDetails(publication, Edition.Ahmedabad, skin, R.drawable.ahmedabad, "AMIR"));
        editions.add(new EditionDetails(publication, Edition.Bangalore, skin, R.drawable.bangalore, "BGMIR"));
        editions.add(new EditionDetails(publication, Edition.Mumbai, skin, R.drawable.mumbai, "MMIR"));
        editions.add(new EditionDetails(publication, Edition.Pune, skin, R.drawable.pune, "PMIR"));
    }

    public Mirror() {
        super(skin, editions);
    }
}
