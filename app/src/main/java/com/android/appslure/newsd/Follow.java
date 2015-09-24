package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.android.BL.FollowBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;

import java.util.HashMap;
import java.util.Map;


public class Follow extends AppCompatActivity {

    Button btnTagOne,btnTagTwo,btnTagThree,btnOK,btnFollowStory;
    int pop_height,pop_width;

    boolean firstTag,secondTag,thirdTag,fourthTag;
    int count;
    ProgressDialog progressDialog;
    String selecteTags="";
     String deviceID="";
     String NewsID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        overridePendingTransition(R.animator.anim_in, R.animator.anim_out);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        try {
            MyApp.tracker().setScreenName("Follow Timeline/Tag Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Follow Timeline/Tag screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("Follow Timeline/Tag Screen");

        progressDialog=new ProgressDialog(Follow.this);

        if(width> 1000)
        {
            pop_height = 1500;
            pop_width = 800;
        }
        else if (width > 700) {

            pop_height = 1000;
            pop_width = 700;

        } else if (width > 500) {
            pop_height = 1000;
            pop_width = 500;
        } else {
            pop_height = 1000;
            pop_width = 500;
        }

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = pop_height;
        params.width = pop_width;
        this.getWindow().setAttributes(params);
        this.getWindow().setGravity(Gravity.RIGHT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        btnTagOne= (Button) findViewById(R.id.follow_tag_one);
        btnTagTwo= (Button) findViewById(R.id.follow_tag_two);
        btnTagThree= (Button) findViewById(R.id.follow_tag_three);

        btnOK= (Button) findViewById(R.id.btn_follow_ok);
        btnFollowStory= (Button) findViewById(R.id.btn_follow_story);

        Intent intent=getIntent();
        Bundle bb=intent.getExtras();
        String tag=bb.get("TAG").toString();
          NewsID=bb.get("NewsID").toString();
        System.out.println("TAG" + tag);
        final String tagArray[]=tag.split(",");
        System.out.println("TAG ARRAY LENGTH" + tagArray.length);

        if(tagArray.length==1)
        {
            btnTagOne.setVisibility(View.VISIBLE);
            btnTagOne.setText(tagArray[0]);
        }
        else if(tagArray.length==2)
        {
            btnTagOne.setVisibility(View.VISIBLE);
            btnTagTwo.setVisibility(View.VISIBLE);

            btnTagOne.setText(tagArray[0]);
            btnTagTwo.setText(tagArray[1]);
        }
        else if(tagArray.length==3)
        {
            btnTagOne.setVisibility(View.VISIBLE);
            btnTagTwo.setVisibility(View.VISIBLE);
            btnTagThree.setVisibility(View.VISIBLE);

            btnTagOne.setText(tagArray[0]);
            btnTagTwo.setText(tagArray[1]);
            btnTagThree.setText(tagArray[2]);
        }

        btnTagOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTag)
                {
                    btnTagOne.setBackgroundResource(R.drawable.follobtn);
                    btnTagOne.setTextColor(Color.parseColor("#ffffff"));
                    firstTag = false;
                    count--;
                }
                else {
                    btnTagOne.setBackgroundResource(R.drawable.follow_btn_selected);
                    btnTagOne.setTextColor(Color.parseColor("#ff7e18"));
                    firstTag = true;
                    count++;
                }
            }
        });
        btnTagTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondTag)
                {
                    btnTagTwo.setBackgroundResource(R.drawable.follobtn);
                    btnTagTwo.setTextColor(Color.parseColor("#ffffff"));
                    secondTag = false;
                    count--;
                }
                else {
                    btnTagTwo.setBackgroundResource(R.drawable.follow_btn_selected);
                    btnTagTwo.setTextColor(Color.parseColor("#ff7e18"));
                    secondTag = true;
                    count++;
                }
            }
        });
        btnTagThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thirdTag)
                {
                    btnTagThree.setBackgroundResource(R.drawable.follobtn);
                    btnTagThree.setTextColor(Color.parseColor("#ffffff"));
                    thirdTag = false;
                    count--;
                }
                else {
                    btnTagThree.setBackgroundResource(R.drawable.follow_btn_selected);
                    btnTagThree.setTextColor(Color.parseColor("#ff7e18"));
                    thirdTag = true;
                    count++;
                }
            }
        });


         deviceID= Configuration.getSharedPrefrenceValue(Follow.this, Constant.SHARED_PREFERENCE_ANDROID_ID);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int flag=0;

                if(count>0) {

                    if (firstTag) {
                        selecteTags = selecteTags + tagArray[0];
                        flag++;
                    }
                    if (secondTag) {
                        if(flag==0) {
                            selecteTags = selecteTags + tagArray[1];
                            flag++;
                        }
                        else
                        {
                            selecteTags = selecteTags + "," + tagArray[1];
                        }
                    }
                    if (thirdTag) {
                        if(flag==0) {
                            selecteTags = selecteTags + tagArray[2];
                            flag++;
                        }
                        else {
                            selecteTags = selecteTags + "," + tagArray[2];
                        }
                    }


                    new SetFollowTag().execute(deviceID,selecteTags);
                }
            }
        });

        btnFollowStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SetFollowStory().execute(deviceID,NewsID);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_follow, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    private class SetFollowTag extends AsyncTask<String,String,String>
    {
        FollowBL objFollowBL=new FollowBL();

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objFollowBL.insertTag(params[0],params[1]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
            articleParams.put("Tags",selecteTags );
            articleParams.put("DeviceID",deviceID);

//up to 10 params can be logged with each event
            FlurryAgent.logEvent("Tag followed", articleParams);
            finish();
            Toast.makeText(Follow.this,"Tag successfully added",Toast.LENGTH_SHORT).show();
        }
    }

    private class SetFollowStory extends AsyncTask<String,String,String>
    {
        FollowBL objFollowBL=new FollowBL();

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objFollowBL.insertStory(params[0], params[1]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
            articleParams.put("NewsID",NewsID );
            articleParams.put("DeviceID",deviceID);

//up to 10 params can be logged with each event
            FlurryAgent.logEvent("Story followed", articleParams);
            finish();
            Toast.makeText(Follow.this,"Story Successfully Followed",Toast.LENGTH_SHORT).show();
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
}
