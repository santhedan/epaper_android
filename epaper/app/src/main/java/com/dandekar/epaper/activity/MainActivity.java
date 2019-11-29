package com.dandekar.epaper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dandekar.epaper.R;
import com.dandekar.epaper.activity.ui.login.LoginActivity;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.Publication;
import com.dandekar.epaper.util.ApplicationCache;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.selectpublication);
        setContentView(R.layout.activity_publication_list);
        //
        drawerLayout = findViewById(R.id.drawer_layout);
        //
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView email = headerView.findViewById(R.id.email_address);
        email.setText(ApplicationCache.userName);
        //
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(Constants.TAG, "Item clicked -> " + item.getItemId());
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            drawerLayout.openDrawer(Gravity.START);
        }
        return super.onOptionsItemSelected(item);
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

    public void handleLogout(View view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                deletePreference();
            }
        };
        t.start();
    }

    public void handleDelete(MenuItem item) {
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

    protected void deletePreference() {
        //
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.COOKIE_KEY);
        editor.remove(Constants.USERNAME_KEY);
        editor.commit();
        //
        startLogin();
    }

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
