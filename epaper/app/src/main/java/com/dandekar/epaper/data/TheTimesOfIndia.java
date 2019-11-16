package com.dandekar.epaper.data;

import com.dandekar.epaper.R;

import java.util.ArrayList;
import java.util.List;

public final class TheTimesOfIndia extends PublicationDetails {

    private static final String skin = "TimesOfIndia";
    private static  final Publication publication = Publication.TheTimesOfIndia;

    public static final List<EditionDetails> editions = new ArrayList<EditionDetails>();

    static {
        editions.add(new EditionDetails(publication, Edition.Ahmedabad, skin, R.drawable.ahmedabad, "TOIA"));
        editions.add(new EditionDetails(publication, Edition.Bangalore, skin, R.drawable.bangalore, "TOIBG"));
        editions.add(new EditionDetails(publication, Edition.Bhopal, skin, R.drawable.bhopal, "TOIBHO"));
        editions.add(new EditionDetails(publication, Edition.Chennai, skin, R.drawable.chennai, "TOICH"));
        editions.add(new EditionDetails(publication, Edition.Delhi, skin, R.drawable.delhi, "TOIDEL"));
        editions.add(new EditionDetails(publication, Edition.Hyderabad, skin, R.drawable.hyderabad, "TOIH"));
        editions.add(new EditionDetails(publication, Edition.Jaipur, skin, R.drawable.jaipur, "TOIJ"));
        editions.add(new EditionDetails(publication, Edition.Kolkata, skin, R.drawable.kolkata, "TOIKM"));
        editions.add(new EditionDetails(publication, Edition.Lucknow, skin, R.drawable.lucknow, "TOIL"));
        editions.add(new EditionDetails(publication, Edition.Mumbai, skin, R.drawable.mumbai, "TOIM"));
        editions.add(new EditionDetails(publication, Edition.Pune, skin, R.drawable.pune, "TOIPU"));
    }

    public TheTimesOfIndia() {
        super(skin, editions);
    }
}
