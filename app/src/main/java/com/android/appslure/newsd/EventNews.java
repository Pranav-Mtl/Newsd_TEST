package com.android.appslure.newsd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;
import com.loopj.android.image.SmartImageView;

public class EventNews extends AppCompatActivity {
    Button previous,upcoming;
    ProgressDialog progressDialog;
    EventNewsAdapter adapter;
    ListView listView,listVewsecond;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.event_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        progressDialog=new ProgressDialog(EventNews.this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("Event Screen");

        int[] menu_imagesover = { R.drawable.event_joined, R.drawable.event_join};

        try {
            MyApp.tracker().setScreenName("Event Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Event screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        previous=(Button)findViewById(R.id.previosu);
        upcoming=(Button)findViewById(R.id.upcoming);

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UpcomingSearchNews.class);
                startActivity(intent);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PreviousSearchNews.class);
                startActivity(intent);

            }
        });

        listVewsecond=(ListView)findViewById(R.id.secondList);
        listView = ( ListView ) findViewById(R.id.firstList);

        try {
            if(Configuration.isInternetConnection(this)) {
                new GetEventNews().execute();
            }
            else
            {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        EventNews.this);

// Setting Dialog Title
                alertDialog2.setTitle("No Internet Connection");

// Setting Dialog Message
                alertDialog2.setMessage("Please check your internet connection and try again");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("OK",
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
          finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

private class GetEventNews extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        progressDialog.setMessage("Loading events...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... params) {

        adapter=new EventNewsAdapter(getApplicationContext());
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();

        String data[]=result.split(",");
        if(!data[0].equals("upComingEmpty")) {
            listView.setAdapter(adapter);
        }
        EventNewsAdapterSecond adapterSecond=new EventNewsAdapterSecond(getApplicationContext());

        if(!data[1].equals("previousEmpty")) {
            listVewsecond.setAdapter(adapterSecond);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EventNews.this, UpcomingNews.class);
                //System.out.println("at time of puttin value--------->"+Constant.preId[pos]);
                intent.putExtra("position", Constant.upcomingNewsID[position]);
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        listVewsecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventNews.this, PreviousNews.class);
                intent.putExtra("position",Constant.preId[position]);
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}


    public class EventNewsAdapter extends BaseAdapter  {
        private Context context;
        ImageView imgview;
        ProgressDialog pd;
        TextView btn;
        UpcomingNewsed upcomingNewsed;
        TextView textView, joinNow;
        int newPosition;
        LinearLayout rlayout;

        SmartImageView newsImg;
        ProgressDialog mProgressDialog;



        public EventNewsAdapter(Context context) {
            this.context = context;
            upcomingNewsed = new UpcomingNewsed();
            try {
                 result = upcomingNewsed.getNewsed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getCount() {
            //System.out.println("jthe value return by the apaterh ---" + Constant.imgUrl.length);
            return Constant.imgUrl.length;

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View gridView = null;
            if (convertView != null) {

                gridView = convertView;

            } else {
                gridView = new View(context);
                gridView = layoutInflater.inflate(R.layout.event_layout, null);

            }

            newsImg = (SmartImageView) gridView.findViewById(R.id.upcomingnews_img);
            //btn = (TextView) gridView.findViewById(R.id.event_button);
            textView = (TextView) gridView.findViewById(R.id.event_text);
            //textView.setText("12 jan 2015");
            textView.setText(Constant.upcomingdate[position]);


            //btn.setBackgroundResource(R.drawable.event_join);
            newsImg.setImageUrl(Constant.imgUrl[position]);


            // title.setText(Constant.FollowedNews[position]);

            gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            return gridView;


        }




/*        public class Upcoming extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
          *//*  pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);*//*

            }


            @Override
            protected String doInBackground(String... params) {
                String result = upcomingNewsed.getNewsed();
                return result;


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //pd.dismiss();

            }


        }*/
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
