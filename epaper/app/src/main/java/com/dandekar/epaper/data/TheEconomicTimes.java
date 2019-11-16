package com.dandekar.epaper.data;

import com.dandekar.epaper.R;

import java.util.ArrayList;
import java.util.List;

public final class TheEconomicTimes extends PublicationDetails {

    private static final String skin = "TheEconomicTimes";
    private static  final Publication publication = Publication.TheEconomicTimes;

    private static final List<EditionDetails> editions = new ArrayList<EditionDetails>();

    static {
        editions.add(new EditionDetails(publication, Edition.Bangalore, skin, R.drawable.bangalore, "ETBG"));
        editions.add(new EditionDetails(publication, Edition.Delhi, skin, R.drawable.delhi, "ETD"));
        editions.add(new EditionDetails(publication, Edition.Kolkata, skin, R.drawable.kolkata, "ETKM"));
        editions.add(new EditionDetails(publication, Edition.Mumbai, skin, R.drawable.mumbai, "ETM"));
    }

    public TheEconomicTimes() {
        super(skin, editions);
    }
}
