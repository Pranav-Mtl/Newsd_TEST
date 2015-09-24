package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.BL.SearchBL;
import com.android.CONSTANTS.Constant;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.loopj.android.image.SmartImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class Search extends AppCompatActivity {

    ListView tvSearch;
    SearchBL objSearchBL;
    EditText etSearch;
    ImageButton btnSearch;
    SearchAdapter objSearchAdapter;
    String etText;
    int currentpage=0;
    ProgressBar progressBar;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,SCROLL_STATE_IDLE;

    LinearLayout llSuggestionImages;
    SmartImageView imgViewOne,imgViewTwo,imgViewThree,imgViewFour;

    ProgressDialog progressDialog;

    String image[];
    String ImageURL[];
    String imageTitle[];

    TextView tvOne,tvTwo,tvThree,tvfour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch= (EditText) findViewById(R.id.search_text);
        btnSearch= (ImageButton) findViewById(R.id.search_btn);
        progressBar= (ProgressBar) findViewById(R.id.search_progress_dialog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog=new ProgressDialog(Search.this);

        llSuggestionImages= (LinearLayout) findViewById(R.id.layout_suggest_images);
        imgViewOne= (SmartImageView) findViewById(R.id.search_suggestion_one);
        imgViewTwo= (SmartImageView) findViewById(R.id.search_suggestion_two);
        imgViewThree= (SmartImageView) findViewById(R.id.search_suggestion_Three);
        imgViewFour= (SmartImageView) findViewById(R.id.search_suggestion_four);

        tvOne= (TextView) findViewById(R.id.trending_title_one);
        tvTwo= (TextView) findViewById(R.id.trending_title_two);
        tvThree= (TextView) findViewById(R.id.trending_title_three);
        tvfour= (TextView) findViewById(R.id.trending_title_four);

        tvSearch= (ListView) findViewById(R.id.search_result_tv);

        objSearchBL=new SearchBL();

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("Search Screen");

        try {
            MyApp.tracker().setScreenName("Search Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Search Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        new GetTendingNews().execute();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etText = etSearch.getText().toString();

                Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                articleParams.put("Keyword Name",etText);

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Keyword Searched", articleParams);

                tvSearch.setVisibility(View.VISIBLE);
                llSuggestionImages.setVisibility(View.INVISIBLE);


                if (etText.length() > 0) {
                    Constant.totalSearchValue = 0;
                    Constant.lastSearchValue = 0;
                    objSearchAdapter = new SearchAdapter(getApplicationContext(), etText);
                    tvSearch.setVisibility(View.VISIBLE);
                    tvSearch.setAdapter(objSearchAdapter);
                }
            }
        });

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

        imgViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewsWebView.class);
                intent.putExtra("NewsURL", ImageURL[0]);
                startActivity(intent);
            }
        });

        imgViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), NewsWebView.class);
                intent.putExtra("NewsURL", ImageURL[1]);
                startActivity(intent);
            }
        });

        imgViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), NewsWebView.class);
                intent.putExtra("NewsURL", ImageURL[2]);
                startActivity(intent);
            }
        });

        imgViewFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), NewsWebView.class);
                intent.putExtra("NewsURL", ImageURL[3]);
                startActivity(intent);
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
               new GetSearchLoaddata().execute(etText,currentpage+"").get();
               progressBar.setVisibility(View.GONE);
                objSearchAdapter.notifyDataSetChanged();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection
        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        private class SearchAdapter extends BaseAdapter{

            Context mContext;
            TextView tvTitle,tvDate;

           public SearchAdapter(Context context,String etText)
            {
                mContext=context;
                try {
                    new GetSearchdata().execute(etText,"0").get();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
            public SearchAdapter(Context context,String etText,String pageNo)
            {
                mContext=context;
                try {
                    new GetSearchLoaddata().execute(etText,pageNo).get();
                    notifyDataSetChanged();
                }
                catch (Exception e)
                {
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

                View gridView=null;
                //TextView tv,tv1;
                if (convertView != null){

                    gridView=convertView;

                }else{
                    gridView = new View(mContext);
                    gridView= infalInflater.inflate(R.layout.search_list_raw, null);

                }

                tvTitle= (TextView) gridView.findViewById(R.id.search_title);
                tvDate= (TextView) gridView.findViewById(R.id.search_date);

                tvTitle.setText(Constant.titleSearch[position]);
                tvDate.setText(Constant.dateSearch[position]);

                gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
                return gridView;

            }
        }

    public class GetSearchdata extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            objSearchBL.GetJson(params[0],params[1]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public class GetSearchLoaddata extends AsyncTask<String,String,String>
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


    public class GetTendingNews extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            String text = null;

            String url="v=1.0&topic=h&start=4&ned=in";

            try {
                URI uri = new URI("http", "www.ajax.googleapis.com", "/ajax/services/search/news",url, null);
                System.out.println("URLLLLL"+uri);
                String ll=uri.toASCIIString();
                System.out.println("gggg"+ll);
                HttpGet httpGet = new HttpGet(ll);
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    text = getASCIIContentFromEntity(entity);
                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }catch (IllegalArgumentException e)
            {
                System.out.println("exxmccc"+e);
            }
            catch (Exception e)
            {

            }
            Log.d("Response: ", "> " + text);


            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonarray;
                JSONObject jsonobject;

                JSONObject jsonObj = new JSONObject(s);
                JSONObject responseData = jsonObj.getJSONObject("responseData");
                //  JSONObject results = responseData.getJSONObject("results");

                jsonarray = responseData.getJSONArray("results");
                System.out.println("JSON ARRAY LENGTH" + jsonarray.length());

                image=new String[jsonarray.length()];
                ImageURL=new String[jsonarray.length()];
                imageTitle=new String[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);

                /*JSONObject image = jsonobject.getJSONObject("image");
                String news_image=image.getString("url");*/

                  ImageURL[i]=jsonobject.getString("signedRedirectUrl").toString();

                    JSONObject imageNews = jsonobject.getJSONObject("image");
                     image[i]=imageNews.getString("url");
                    imageTitle[i]=jsonobject.getString("title").toString().replace("&nbsp;", "").replace("&#39;", "'").replace("amp;", "");


                }



            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            imgViewOne.setImageUrl(image[0]);
            imgViewTwo.setImageUrl(image[1]);
            imgViewThree.setImageUrl(image[2]);
            imgViewFour.setImageUrl(image[3]);

            tvOne.setText(imageTitle[0]);
            tvTwo.setText(imageTitle[1]);
            tvThree.setText(imageTitle[2]);
            tvfour.setText(imageTitle[3]);

            progressDialog.dismiss();

        }
    }

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();


        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }


        return out.toString();
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
