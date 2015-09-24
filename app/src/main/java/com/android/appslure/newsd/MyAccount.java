package com.android.appslure.newsd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.android.BE.MyAccountBE;
import com.android.BL.MyAccountBL;
import com.android.BL.UpdateAccountBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MyAccount extends AppCompatActivity {

    ImageButton india,world,humour,business,trending,tech,startup,lifestyle,people,sports,entertainment,economy,beyondmetro;

    boolean flagindia=false,flag_world=false,flag_humor=false,flag_business=false,flag_trending=false,flag_tech=false,flag_stratup=false;
    boolean flag_lifestyle=false,flag_people=false,flag_sports=false,flag_entertainment=false,flag_economy=false,flag_beyonmetro=false;
    TextView textview,tvTimeLine,tvHIW;
    MyAccountBE objMyAccountBE;
    MyAccountBL objMyAccountBL;
    Button btnUpdate;
    String selectedCategory;

    UpdateAccountBL objUpdateAccountBL;

    ProgressDialog progressDialog;

    GoogleCloudMessaging gcmObj;

    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        objMyAccountBE=new MyAccountBE();
        objMyAccountBL=new MyAccountBL();

        objUpdateAccountBL=new UpdateAccountBL();

        textview=(TextView)findViewById(R.id.followed);
        india=(ImageButton)findViewById(R.id.india);
        world=(ImageButton)findViewById(R.id.world);
        humour=(ImageButton)findViewById(R.id.hometutor);
        business=(ImageButton)findViewById(R.id.business);
        trending=(ImageButton)findViewById(R.id.trending);
        tech=(ImageButton)findViewById(R.id.tech);
        startup=(ImageButton)findViewById(R.id.startup);
        lifestyle=(ImageButton)findViewById(R.id.lifestyle);
        people=(ImageButton)findViewById(R.id.people);
        sports=(ImageButton)findViewById(R.id.sports);
        entertainment=(ImageButton)findViewById(R.id.entertainment);
        economy=(ImageButton)findViewById(R.id.economy);
        beyondmetro=(ImageButton)findViewById(R.id.beyondmetro);
        tvTimeLine= (TextView) findViewById(R.id.timeline_account);
        btnUpdate= (Button) findViewById(R.id.btn_account_update);
        tvHIW= (TextView) findViewById(R.id.htw_account);

        try {
            MyApp.tracker().setScreenName("My Account");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("My Account")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("UserProfile Screen");

        regId=Configuration.getSharedPrefrenceValue(MyAccount.this,Constant.SHARED_PREFERENCE_UpdateRegistrationID);

        if(regId==null)
        {
            registerInBackground();
        }

        progressDialog=new ProgressDialog(MyAccount.this);

        final String deviceId= Configuration.getSharedPrefrenceValue(MyAccount.this,Constant.SHARED_PREFERENCE_ANDROID_ID);
        try
        {
            new GetSelectedCategories().execute(deviceId);
        }catch (Exception e)
        {

        }

        tvTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAccount.this,FollowedStory.class));
            }
        });

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAccount.this,FollowedNews.class));
            }
        });

        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagindia)
                {
                    india.setBackgroundResource(R.drawable.india);
                    flagindia=false;
                }
                else
                {
                    india.setBackgroundResource(R.drawable.indiaselected);
                    flagindia=true;
                }
            }
        });
        world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_world)
                {
                    world.setBackgroundResource(R.drawable.world);
                    flag_world=false;
                }
                else
                {
                    world.setBackgroundResource(R.drawable.worldselected);
                    flag_world=true;
                }
            }
        });
        humour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_humor)
                {
                    humour.setBackgroundResource(R.drawable.hometutors);
                    flag_humor=false;
                }
                else
                {
                    humour.setBackgroundResource(R.drawable.hometutorsselected);
                    flag_humor=true;
                }
            }
        });
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_business)
                {
                    business.setBackgroundResource(R.drawable.business);
                    flag_business=false;
                }
                else
                {
                    business.setBackgroundResource(R.drawable.business_selected);
                    flag_business=true;
                }
            }
        });
        trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_trending)
                {
                    trending.setBackgroundResource(R.drawable.trading);
                    flag_trending=false;
                }
                else
                {
                    trending.setBackgroundResource(R.drawable.trading_selected);
                    flag_trending=true;
                }
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_tech)
                {
                    tech.setBackgroundResource(R.drawable.tech);
                    flag_tech=false;
                }
                else
                {
                    tech.setBackgroundResource(R.drawable.tech_selected);
                    flag_tech=true;
                }
            }
        });
        startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_stratup)
                {
                    startup.setBackgroundResource(R.drawable.startup);
                    flag_stratup=false;
                }
                else
                {
                    startup.setBackgroundResource(R.drawable.startup_selected);
                    flag_stratup=true;
                }
            }
        });
        lifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_lifestyle)
                {
                    lifestyle.setBackgroundResource(R.drawable.lifestyle);
                    flag_lifestyle=false;
                }
                else
                {
                    lifestyle.setBackgroundResource(R.drawable.lifestyle_selected);
                    flag_lifestyle=true;
                }
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAccount.this, PeopleSelection.class));
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_sports)
                {
                    sports.setBackgroundResource(R.drawable.sports);
                    flag_sports=false;
                }
                else
                {
                    sports.setBackgroundResource(R.drawable.sports_selected);
                    flag_sports=true;
                }
            }
        });
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_entertainment)
                {
                    entertainment.setBackgroundResource(R.drawable.entertainment);
                    flag_entertainment=false;
                }
                else
                {
                    entertainment.setBackgroundResource(R.drawable.entertainment_selected);
                    flag_entertainment=true;
                }
            }
        });

        economy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_economy)
                {
                    economy.setBackgroundResource(R.drawable.economy);
                    flag_economy=false;
                }
                else
                {
                    economy.setBackgroundResource(R.drawable.economy_selected);
                    flag_economy=true;
                }
            }
        });
        beyondmetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(getApplicationContext(),UpdateMapActivity.class);
                    intent.putExtra("MyAccountBE", objMyAccountBE);
                    startActivityForResult(intent, 1);
                    //startActivityForResult(new Intent(MyAccount.this, UpdateMapActivity.class).putExtra("MyAccountBE", objMyAccountBE).putExtra(RESULT_OK, 1));

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(flagindia)
                {
                    selectedCategory=Constant.India;
                }
                if(flag_world)
                {
                    selectedCategory=selectedCategory+","+Constant.World;
                }
                if(flag_humor)
                {
                    selectedCategory=selectedCategory+","+Constant.Humour_Rumour;
                }
                if(flag_business)
                {
                    selectedCategory=selectedCategory+","+Constant.Bussiness;
                }
                if(flag_trending)
                {
                    selectedCategory=selectedCategory+","+Constant.Trending;
                }
                if(flag_tech)
                {
                    selectedCategory=selectedCategory+","+Constant.Tech;
                }
                if(flag_stratup)
                {
                    selectedCategory=selectedCategory+","+Constant.StartUp;
                }
                if(flag_lifestyle)
                {
                    selectedCategory=selectedCategory+","+Constant.LifeStyle;
                }
                /////////


                if(flag_people)
                {
                    selectedCategory=selectedCategory+","+Constant.People;
                }
                if(flag_sports)
                {
                    selectedCategory=selectedCategory+","+Constant.Sport;
                }
                if(flag_entertainment)
                {
                    selectedCategory=selectedCategory+","+Constant.Entertainment;
                }
                if(flag_economy)
                {
                    selectedCategory=selectedCategory+","+Constant.Economy;
                }

                /////////////////////////////////////////

                Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                articleParams.put("DeviceID", deviceId);
                articleParams.put("States", objMyAccountBE.getStates());
                articleParams.put("Category",selectedCategory);
//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Profile Updated", articleParams);


                System.out.print("SELECTED CATEGORY"+objMyAccountBE.getStates());

                new UpdateAccount().execute(deviceId,selectedCategory,objMyAccountBE.getStates(),regId);

            }
        });


        tvHIW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HowItWork.class));
            }
        });

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

    private class GetSelectedCategories extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objMyAccountBL.getAccount(params[0],objMyAccountBE);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try
            {
            if(s.equals("1")) {
                String selectedCategory = objMyAccountBE.getCategory();

                String selectedArray[] = selectedCategory.split(",");

                for (int i = 0; i < selectedArray.length; i++) {
                    if (selectedArray[i].equals(Constant.India)) {
                        india.setBackgroundResource(R.drawable.indiaselected);
                        flagindia = true;
                    } else if (selectedArray[i].equals(Constant.World)) {
                        world.setBackgroundResource(R.drawable.worldselected);
                        flag_world = true;
                    } else if (selectedArray[i].equals(Constant.Trending)) {
                        trending.setBackgroundResource(R.drawable.trading_selected);
                        flag_trending = true;
                    } else if (selectedArray[i].equals(Constant.Humour_Rumour)) {
                        humour.setBackgroundResource(R.drawable.hometutorsselected);
                        flag_humor = true;
                    } else if (selectedArray[i].equals(Constant.Economy)) {
                        economy.setBackgroundResource(R.drawable.economy_selected);
                        flag_economy = true;
                    } else if (selectedArray[i].equals(Constant.Bussiness)) {
                        business.setBackgroundResource(R.drawable.business_selected);
                        flag_business = true;
                    } else if (selectedArray[i].equals(Constant.Tech)) {
                        tech.setBackgroundResource(R.drawable.tech_selected);
                        flag_tech = true;
                    } else if (selectedArray[i].equals(Constant.Sport)) {
                        sports.setBackgroundResource(R.drawable.sports_selected);
                        flag_sports = true;
                    } else if (selectedArray[i].equals(Constant.Entertainment)) {
                        entertainment.setBackgroundResource(R.drawable.entertainment_selected);
                        flag_entertainment = true;
                    } else if (selectedArray[i].equals(Constant.People)) {
                        people.setBackgroundResource(R.drawable.people_selected);
                        flag_people = true;
                    } else if (selectedArray[i].equals(Constant.LifeStyle)) {
                        lifestyle.setBackgroundResource(R.drawable.lifestyle_selected);
                        flag_lifestyle = true;
                    } else if (selectedArray[i].equals(Constant.StartUp)) {
                        startup.setBackgroundResource(R.drawable.startup_selected);
                        flag_stratup = true;
                    }



                }

                if(!objMyAccountBE.getStates().equalsIgnoreCase(""))
                {
                    beyondmetro.setBackgroundResource(R.drawable.beyong_selected);
                    flag_beyonmetro=true;
                }

                progressDialog.dismiss();
            }
        }catch (Exception e)
            {

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        MyAccount.this);

// Setting Dialog Title
                alertDialog2.setTitle("Internet Not Found");

// Setting Dialog Message
                alertDialog2.setMessage("No Internet connection found,Please check your internet connection");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                               dialog.cancel();
                                finish();
                            }
                        });
// Setting Negative "NO" Btn


// Showing Alert Dialog
                alertDialog2.show();



            }
            finally {
                progressDialog.dismiss();
            }
        }
    }


    private class UpdateAccount extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objUpdateAccountBL.updateAccount(params[0],params[1],params[2],params[3]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

            if(s.equals("1"))
            {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MyAccount.class));
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            try {
                String dataStated = data.getStringExtra("STATES");
                if(dataStated!=null) {
                    if (dataStated.equals("") || dataStated.equals(null)) {
                        beyondmetro.setBackgroundResource(R.drawable.beyond_metro);
                        flag_beyonmetro = false;
                    } else {
                        beyondmetro.setBackgroundResource(R.drawable.beyong_selected);
                        flag_beyonmetro = true;
                    }
                    System.out.println("DATAA" + dataStated);
                    objMyAccountBE.setStates(dataStated);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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


    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(getApplicationContext());
                    }
                    regId = gcmObj
                            .register(Constant.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

                System.out.println(msg);
                if (!TextUtils.isEmpty(regId)) {
                    //storeRegIdinSharedPref(applicationContext, regId, emailID);
                    //savedRegID=regId;
                    Configuration.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_UpdateRegistrationID, regId);
                   /* Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show();*/
                } else {
                    /*Toast.makeText(
                            applicationContext,
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();*/
                }
            }
        }.execute(null, null, null);
    }
}
