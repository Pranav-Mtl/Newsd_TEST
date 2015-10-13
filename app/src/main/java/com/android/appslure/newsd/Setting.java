package com.android.appslure.newsd;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;

public class Setting extends AppCompatActivity {

    ToggleButton tg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tg1=(ToggleButton)findViewById(R.id.toggleButton);

        String checkData=Configuration.getSharedPrefrenceValue(getApplicationContext(),Constant.SHARED_PREFERENCE_NOTIFICATION);

        if(checkData==null){
            tg1.setChecked(true);
        }
        else if(checkData.equals("disable")){
            tg1.setChecked(false);
        }

        tg1.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                //Toast.makeText(getApplicationContext(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    Configuration.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME,Constant.SHARED_PREFERENCE_NOTIFICATION,null);
                }
                else {
                    Configuration.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME,Constant.SHARED_PREFERENCE_NOTIFICATION,"disable");
                }
            }
        }) ;
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
}
