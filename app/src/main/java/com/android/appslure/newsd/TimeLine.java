package com.android.appslure.newsd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.BL.SearchBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;

import java.util.HashMap;
import java.util.Map;


public class TimeLine extends ActionBarActivity {

    ListView tvSearch;
    SearchBL objSearchBL;
    EditText etSearch;
    ImageButton btnSearch;
    TimeLineAdapter objTimeLineAdapter;
    String etText;
    int currentpage = 0;
    ProgressBar progressBar;
    int currentFirstVisibleItem, currentVisibleItemCount, currentScrollState, SCROLL_STATE_IDLE;
    String tag;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        etSearch = (EditText) findViewById(R.id.timeline_text);

        progressBar = (ProgressBar) findViewById(R.id.search_progress_dialog);

        progressDialog=new ProgressDialog(TimeLine.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        objSearchBL=new SearchBL();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvSearch = (ListView) findViewById(R.id.timeline_result_tv);

        Constant.totalSearchValue=0;

        Intent intent=getIntent();
        Bundle bb=intent.getExtras();
        tag=bb.get("TAG").toString();
        etSearch.setText(tag);

        try {
            MyApp.tracker().setScreenName("TimeLine Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("TimeLine Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_DESCRIPTION, "Tag Searched  "+tag);
        logger.logEvent("TimeLine Screen", parameters);

        Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
        articleParams.put("Tag Name", tag);
//up to 10 params can be logged with each event
        FlurryAgent.logEvent("TimeLine tag", articleParams);

        if(Configuration.isInternetConnection(TimeLine.this)) {
            new GetTimeLinedata().execute(tag, "0");
        }
        else
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    TimeLine.this);

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




        tvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                currentScrollState = scrollState;
                System.out.println("Current Scroll State" + currentScrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                currentFirstVisibleItem = firstVisibleItem;
                currentVisibleItemCount = visibleItemCount;
                System.out.println("Current First Visible Item" + currentFirstVisibleItem);
                System.out.println("Visible Item Count" + currentVisibleItemCount);
                System.out.println("Total Value" + Constant.totalSearchValue);

                if (currentVisibleItemCount + currentFirstVisibleItem >= Constant.totalSearchValue) {
                    System.out.println("Call is scroll Completed Method");
                    isScrollCompleted();

                }


            }
        });


        tvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Intent intent = new Intent(getActivity(), BuyerProfile.class);
                intent.putExtra("ID", Constant.buyerIdArray[position]);
                intent.putExtra("MyPost", "allPost");
                startActivity(intent);*/

                Intent intent= new Intent(getApplication(), NewsWebView.class);
                intent.putExtra("NewsURL", Constant.newsURLSearch[position]);
                startActivity(intent);
            }
        });

    }

    private void isScrollCompleted() {

        if(currentVisibleItemCount>0)
            try
            {
                progressBar.setVisibility(View.VISIBLE);
                currentpage=currentpage+4;
                new GetTimeLineLoaddata().execute(tag, currentpage + "").get();
                progressBar.setVisibility(View.GONE);
                objTimeLineAdapter.notifyDataSetChanged();
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
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TimeLineAdapter extends BaseAdapter {

        Context mContext;
        TextView tvTitle, tvDate;

        public TimeLineAdapter(Context context, String etText,SearchBL objSearchBL) {
            mContext = context;
            try {
                objSearchBL.GetJson(etText,"0");

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        public TimeLineAdapter(Context context, String etText, String pageNo) {
            mContext = context;
            try {
                //new GetSearchLoaddata().execute(etText,pageNo).get();
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getCount() {
            return Constant.titleSearch.length;
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

            View gridView = null;
            //TextView tv,tv1;
            if (convertView != null) {

                gridView = convertView;

            } else {
                gridView = new View(mContext);
                gridView = infalInflater.inflate(R.layout.timeline_list_raw, null);

            }

            tvTitle = (TextView) gridView.findViewById(R.id.timeline_title);
            tvDate = (TextView) gridView.findViewById(R.id.timeline_date);

            tvTitle.setText(Constant.titleSearch[position]);
            tvDate.setText(Constant.dateSearch[position]);

            gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
            return gridView;

        }
    }


    private class GetTimeLinedata extends AsyncTask<String,String,String>
            {

                @Override
                protected void onPreExecute() {
                    progressDialog.show();
                   progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                }

                @Override
        protected String doInBackground(String... params) {

            objTimeLineAdapter = new TimeLineAdapter(getApplicationContext(),params[0],objSearchBL);

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            tvSearch.setAdapter(objTimeLineAdapter);
        }
    }

    private class GetTimeLineLoaddata extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            objSearchBL.GetJsonOnLoad(params[0], params[1]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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



