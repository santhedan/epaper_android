package com.dandekar.epaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;
import com.dandekar.epaper.adapter.EditionAdapter;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.data.EditionDetails;
import com.dandekar.epaper.decoration.ThumbnailDecoration;
import com.dandekar.epaper.util.ApplicationCache;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class EditionListing extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_listing);

        String publication = getIntent().getStringExtra(Constants.PUBLICATION);

        Log.e(Constants.TAG, "The publication is -> " + publication);

        recyclerView = (RecyclerView) findViewById(R.id.edition_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        ThumbnailDecoration decoration = new ThumbnailDecoration(this, ThumbnailDecoration.GRID, 2, getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(decoration);

        // specify an adapter (see also next example)
        mAdapter = new EditionAdapter(getApplicationContext(), publication, this);
        recyclerView.setAdapter(mAdapter);
        //
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Only one menu item - so no check required
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // Get the year month and day for today
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        //Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        EditionDetails details = (EditionDetails) v.getTag();
        // Cache the selection
        ApplicationCache.edition = details.getEdition();
        if (details != null) {
            CurrentSelection curSel = new CurrentSelection(details.getSkin(), details.getShortPath(), year, month, day, generateRandom());
            ApplicationCache.curSel = curSel;
            String URL = String.format(Constants.getXMLURL, details.getSkin(), details.getShortPath(), year, month, day, details.getShortPath(), year, month, day, year, month, day, curSel.getRandom());
            Intent intent = new Intent(this, PageListing.class);
            intent.putExtra(Constants.XMLURL,URL);
            startActivity(intent);
        }
    }

    private int generateRandom() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
}