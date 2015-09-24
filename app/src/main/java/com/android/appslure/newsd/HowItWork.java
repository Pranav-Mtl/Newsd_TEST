package com.android.appslure.newsd;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;

public class HowItWork extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_work);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            MyApp.tracker().setScreenName("HowItWork Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("HowItWork Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("HowItWork Screen");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}
