package com.dandekar.epaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dandekar.epaper.R;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.Publication;
import com.dandekar.epaper.util.ApplicationCache;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.selectpublication);
        setContentView(R.layout.activity_main);
    }

    public void launch_toi_home(View view) {
        showCity(Publication.TheTimesOfIndia);
    }

    public void launch_et_home(View view) {
        showCity(Publication.TheEconomicTimes);
    }

    public void launch_mirror_home(View view) {
        showCity(Publication.Mirror);
    }

    private void showCity(Publication publication) {
        // Store the publication in the app cache
        ApplicationCache.publication = publication;
        // Navigate away but keep this activity on stack as user can come back to it
        // to pick another publication
        Intent intent = new Intent(this, EditionListing.class);
        intent.putExtra(Constants.PUBLICATION, publication.name());
        startActivity(intent);
    }

}
