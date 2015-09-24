package com.android.appslure.newsd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import com.android.CONSTANTS.Constant;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;

import java.util.Locale;


public class MapMainActivity extends AppCompatActivity {

    ViewPager mViewPager;

    SectionsPagerAdapter mSectionsPagerAdapter;
    Intent intent;
    ToggleButton tglBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_map);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.new_logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        try {
            MyApp.tracker().setScreenName("Beyond Metro Selection");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Beyond Metro Selection")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("BeyondMetro Selection Swipe");


        tglBtn= (ToggleButton) findViewById(R.id.states_select_all_toggle);
        if(Constant.flag)
        {
            tglBtn.setChecked(true);

        }

      /* if(Constant.allChecked)
        {
            tglBtn.setChecked(true);
            Toast.makeText(getApplicationContext(),"all checkeed",Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"inside the all chekced",Toast.LENGTH_LONG).show();
        }*/
       else
        {
            tglBtn.setChecked(false);

        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(0);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        tglBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(tglBtn.isChecked())
                {
                    //Toast.makeText(getApplicationContext(),"is checked clicked",Toast.LENGTH_LONG).show();
                    Constant.flag=true;
                    Constant.statesCounter=13;
                    Constant.JAMMU=true;
                    Constant.HARYANA=true;
                    Constant.DELHI=true;
                    Constant.UTTARPRADESH=true;
                    Constant.GUJARAT=true;
                    Constant.MADHYAPRADESH=true;
                    Constant.BIHAR=true;
                    Constant.WB=true;
                    Constant.ASSAM=true;
                    Constant.MAHARASHTRA=true;
                    Constant.TELANGANA=true;
                    Constant.KARNATAKA=true;
                    Constant.KERALA=true;
                   // Constant.allChecked=true;
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    startActivity(new Intent(MapMainActivity.this, MapMainActivity.class));
                    finish();
                } else
                {
                    Constant.flag=false;

                    //Toast.makeText(getApplicationContext(),"inside the else condition",Toast.LENGTH_LONG).show();
                    Constant.statesCounter=0;
                    Constant.JAMMU=false;
                    Constant.HARYANA=false;
                    Constant.DELHI=false;
                    Constant.UTTARPRADESH=false;
                    Constant.GUJARAT=false;
                    Constant.MADHYAPRADESH=false;
                    Constant.BIHAR=false;
                    Constant.WB=false;
                    Constant.ASSAM=false;
                    Constant.MAHARASHTRA=false;
                    Constant.TELANGANA=false;
                    Constant.KARNATAKA=false;
                    Constant.KERALA=false;
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    //Constant.allChecked=false;
                    startActivity(new Intent(MapMainActivity.this, MapMainActivity.class));
                    finish();
                }
            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            try {
                switch (position) {
                    case 0:
                        // Top Rated fragment activity
                        return new MapFragmentOne();
                    case 1:
                        // Games fragment activity
                        return new MapFragmentTwo();
                    case 2:
                        // Movies fragment activity
                        return new MapFragmentThree();

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Map1".toUpperCase(l);
                case 1:
                    return "Map2".toUpperCase(l);
                case 2:
                    return "Map3".toUpperCase(l);

            }
            return null;
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
