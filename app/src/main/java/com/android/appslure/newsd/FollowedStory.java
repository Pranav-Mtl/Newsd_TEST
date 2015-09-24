package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.BL.FollowBL;
import com.android.BL.RemoveFollowBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;


public class FollowedStory extends AppCompatActivity {

    ListView lvStory;

    FollowBL objFollowBL;
    FollowedStoryAdapter objFollowedNewsAdapter;
    String deviceId;
    String result="";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed_story);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        progressDialog=new ProgressDialog(FollowedStory.this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        deviceId= Configuration.getSharedPrefrenceValue(getApplicationContext(), Constant.SHARED_PREFERENCE_ANDROID_ID);

        objFollowBL=new FollowBL();


        lvStory= (ListView) findViewById(R.id.story_listview);

        try {
            MyApp.tracker().setScreenName("Follow Story List");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Follow Story List")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("FollowedStory List Screen");

        new GetFollowedStory().execute(deviceId);

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
    private  class GetFollowedStory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }


        @Override
        protected String doInBackground(String... params) {

          objFollowedNewsAdapter=new FollowedStoryAdapter(getApplicationContext(),objFollowBL,params[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

            if(result.equalsIgnoreCase("empty"))
            {

            }
            else if (result.equalsIgnoreCase("data")){
                lvStory.setAdapter(objFollowedNewsAdapter);


                lvStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Intent intent = new Intent(getActivity(), BuyerProfile.class);
                intent.putExtra("ID", Constant.buyerIdArray[position]);
                intent.putExtra("MyPost", "allPost");
                startActivity(intent);*/

                        Intent intent = new Intent(getApplication(), TimeLine.class);
                        intent.putExtra("TAG", Constant.FollowedStory[position]);
                        startActivity(intent);
                    }
                });
            }


        }
    }

    private class FollowedStoryAdapter extends BaseAdapter implements View.OnClickListener {
        FollowBL objFollowBL;
        Context mContext;
        TextView title,category,date,publisher;
        ImageButton btnDelete,btnTimeLIne;
        public FollowedStoryAdapter(Context context,FollowBL followBL,String deviceId)
        {
            mContext=context;
            objFollowBL=followBL;
             result=objFollowBL.getStory(deviceId);
        }

        @Override
        public int getCount() {
            return Constant.FollowedStory.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

            View gridView=null;
            //TextView tv,tv1;
            if (convertView != null){

                gridView=convertView;

            }else{
                gridView = new View(mContext);
                gridView= infalInflater.inflate(R.layout.followed_story_raw_list, null);

            }

            title= (TextView) gridView.findViewById(R.id.followed_news_title);
            category= (TextView) gridView.findViewById(R.id.followed_news_category);
            date= (TextView) gridView.findViewById(R.id.followed_news_date);
            publisher= (TextView) gridView.findViewById(R.id.followed_news_publisher);

            btnDelete= (ImageButton) gridView.findViewById(R.id.followed_news_delete);
            btnTimeLIne= (ImageButton) gridView.findViewById(R.id.followed_news);
            btnDelete.setTag(position);
            btnDelete.setOnClickListener(this);

            btnTimeLIne.setTag(position);
            btnTimeLIne.setOnClickListener(this);

            title.setText(Constant.FollowedTitle[position]);
            category.setText(Constant.FollowedCategory[position].toUpperCase());
            date.setText(Constant.FollowedTime[position]);
            publisher.setText(Constant.FollowedPublisher[position]);

            gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
            return gridView;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.followed_news_delete:
                   int position= (int) v.getTag();
                    new RemoveStory().execute(Constant.FollowedNewsID[position]);

                    break;
                case R.id.followed_news:
                    int pos=(Integer)v.getTag();
                    Intent intent = new Intent(mContext, TimeLine.class);
                    intent.putExtra("TAG",Constant.FollowedStory[pos]);
                    startActivity(intent);
                    break;
            }        }
    }

    private class RemoveStory extends AsyncTask<String,String,String>
    {
        RemoveFollowBL objRemoveFollowBL=new RemoveFollowBL();

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Removing...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objRemoveFollowBL.getNewsStoryId(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            try {
                if (s.equals("1")) {
                    Toast.makeText(getApplicationContext(), "Story removed from timeline", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FollowedStory.this, FollowedStory.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Network problem,please try again", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "Network problem,please try again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
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
