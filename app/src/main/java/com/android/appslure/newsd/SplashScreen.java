package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.BL.CategoryBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.android.DB.ChatPeopleBE;
import com.android.DB.DBOperation;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class SplashScreen extends AppCompatActivity {

    int SPLASH_TIME = 1000;

    CategoryBL objCategoryBL;
    ProgressDialog mProgressDialog;

    String deviceID,regId;
    GoogleCloudMessaging gcmObj;

    String savedRegID;

    DBOperation dbOperation;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FacebookSdk.sdkInitialize(getApplicationContext());

        ImageView img=(ImageView) findViewById(R.id.title);
        final ImageView test_logo=(ImageView) findViewById(R.id.logo);

        mProgressDialog=new ProgressDialog(SplashScreen.this);

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("Splash Screen");

        FlurryAgent.logEvent("Splash Screen");

        objCategoryBL=new CategoryBL();
        //final ImageView test_title=(ImageView) findViewById(R.id.title);
        //final Animation fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        //final Animation fade_out = AnimationUtils.loadAnimation(this,R.anim.fade_out);

        try {
            MyApp.tracker().setScreenName("Landing Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Landing Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        System.out.println("LADOOO"+Configuration.getSharedPrefrenceValue(SplashScreen.this,Constant.CampaignLadoo));

        dbOperation = new DBOperation(this);
        dbOperation.createAndInitializeTables();


        try {

            savedRegID = Configuration.getSharedPrefrenceValue(getApplicationContext(), Constant.SHARED_PREFERENCE_UpdateRegistrationID);

            System.out.println("ffffff" + savedRegID);

            if (savedRegID==null)  {

                if (checkPlayServices()) {
                    registerInBackground();

                    System.out.println("REGIGGG"+regId);
                }
            }
            else
            {
                System.out.println("REGIGGG"+regId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



       /* TranslateAnimation animation = new TranslateAnimation(1000.0f, 0.0f,0.0f, 0.0f);//  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(1500);  // animation duration
        animation.setRepeatCount(0);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img.startAnimation(animation);*/

        try {
            deviceID= Configuration.getSharedPrefrenceValue(SplashScreen.this, Constant.SHARED_PREFERENCE_ANDROID_ID);
            System.out.println("Android STORED ID--->" + deviceID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

/*
        test_logo.startAnimation(fade_in);
        test_title.startAnimation(fade_in);
*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(deviceID==null) {
                    String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    System.out.println("Android ID" + android_id);
                    Configuration.setSharedPrefrenceValue(SplashScreen.this,Constant.PREFS_NAME,Constant.SHARED_PREFERENCE_ANDROID_ID,android_id);
                    startActivity(new Intent(SplashScreen.this, CategoryActivity.class));
                }
                else
                {
                  // startActivity(new Intent(SplashScreen.this, CategoryActivity.class));
                   try {

                       if(Configuration.isInternetConnection(SplashScreen.this)) {
                         //  String result = new InsertSelectedCategory().execute(deviceID, "0",savedRegID).get();
                           Intent intent = new Intent(SplashScreen.this, DemoViewFlipperActivity.class);
                           //intent.putExtra("DBOperation",dbOperation);
                           startActivity(intent);
                       }
                       else
                       {
                          // getData();
                           Intent intent = new Intent(SplashScreen.this, DemoViewFlipperActivity.class);
                           startActivity(intent);
                       }


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                finish();
            }
        }, SPLASH_TIME);
    }


    private class InsertSelectedCategory extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... params) {

            String result=objCategoryBL.getCategoryData(params[0], params[1],dbOperation,params[2]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                /*Toast.makeText(
                        getApplicationContext(),
                        "This device doesn't support Play services, App will not work normally",
                        Toast.LENGTH_LONG).show();*/
                finish();
            }
            return false;
        } else {
           /* Toast.makeText(
                    getApplicationContext(),
                    "This device supports Play services, App will work normally",
                    Toast.LENGTH_LONG).show();*/
        }
        return true;
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
                    savedRegID=regId;
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


    ChatPeopleBE addToChat(String newsdID, String newsdTitle, String newsdContent,String newsdTag,String newsdPublisher,String newsdImage,String newsdLink,String newsdFollow,String newsdBookmarked) {

        /*Log.i(TAG, "inserting : " + personName + ", " + chatMessage + ", "
                + toOrFrom + " , " + chattingToDeviceID);*/
        ChatPeopleBE curChatObj = new ChatPeopleBE();
        curChatObj.setNewdID(newsdID);
        curChatObj.setNewsTitle(newsdTitle);
        curChatObj.setNewsContent(newsdContent);
        curChatObj.setNewsTag(newsdTag);

        curChatObj.setNewsPublisher(newsdPublisher);
        curChatObj.setNewsLink(newsdLink);
        curChatObj.setNewsImage(newsdImage);
        curChatObj.setNewsFollow(newsdFollow);
        curChatObj.setNewsBookmark(newsdBookmarked);

        return curChatObj;

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
