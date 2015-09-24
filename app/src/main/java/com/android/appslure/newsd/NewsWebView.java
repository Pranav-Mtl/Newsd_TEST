package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;
import com.loopj.android.image.SmartImageView;


public class NewsWebView extends AppCompatActivity {

    //private Button button;
    private WebView webView;

    Button btn_contact, btn_audio, btn_bookmark, btn_search;

    ImageView contact_tab, audio_tab, bookmark_tab, search_tab;

    SmartImageView news_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        toolbar.setLogo(R.drawable.logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Bundle bundle= getIntent().getExtras();

        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        btn_bookmark = (Button) findViewById(R.id.btn_bookmark);
        btn_search = (Button) findViewById(R.id.btn_search);

        contact_tab = (ImageView) findViewById(R.id.contact_tab);
        audio_tab = (ImageView) findViewById(R.id.audio_tab);
        bookmark_tab = (ImageView) findViewById(R.id.bookmark_tab);
        search_tab = (ImageView) findViewById(R.id.search_tab);
        news_img = (SmartImageView) findViewById(R.id.news_img);

        //Intent i= getIntent();
       // String  newpos=i.getStringExtra("position");
       // String pos=bundle.getString("position");

        try {
            MyApp.tracker().setScreenName("Web View");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Web View")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        String news_url= bundle.getString("NewsURL");

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_DESCRIPTION, "Opened News URL is "+news_url);
        logger.logEvent("WebView Screen",parameters);

            //Get webview
            webView = (WebView) findViewById(R.id.webView1);

        startWebView(news_url);
            //startWebView("http://google.com");

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact_tab.getVisibility() == View.VISIBLE) {
                    contact_tab.setVisibility(View.INVISIBLE);
                } else {
                    contact_tab.setVisibility(View.VISIBLE);
                    audio_tab.setVisibility(View.INVISIBLE);
                    bookmark_tab.setVisibility(View.INVISIBLE);
                    search_tab.setVisibility(View.INVISIBLE);
                }
                startActivity(new Intent(getApplicationContext(), MyAccount.class));


            }
        });

        btn_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audio_tab.getVisibility() == View.VISIBLE) {
                    audio_tab.setVisibility(View.INVISIBLE);
                } else {
                    audio_tab.setVisibility(View.VISIBLE);
                    contact_tab.setVisibility(View.INVISIBLE);

                    bookmark_tab.setVisibility(View.INVISIBLE);
                    search_tab.setVisibility(View.INVISIBLE);
                }
                startActivity(new Intent(getApplicationContext(), EventNews.class));
                //Toast.makeText(getApplicationContext(), "Audio Page Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmark_tab.getVisibility() == View.VISIBLE) {
                    bookmark_tab.setVisibility(View.INVISIBLE);
                } else {
                    bookmark_tab.setVisibility(View.VISIBLE);
                    contact_tab.setVisibility(View.INVISIBLE);
                    audio_tab.setVisibility(View.INVISIBLE);

                    search_tab.setVisibility(View.INVISIBLE);
                }
                startActivity(new Intent(getApplicationContext(),BookMarkList.class));

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_tab.getVisibility() == View.VISIBLE) {
                    search_tab.setVisibility(View.INVISIBLE);
                } else {
                    search_tab.setVisibility(View.VISIBLE);
                    contact_tab.setVisibility(View.INVISIBLE);
                    audio_tab.setVisibility(View.INVISIBLE);
                    bookmark_tab.setVisibility(View.INVISIBLE);

                }
                startActivity(new Intent(getApplicationContext(), Search.class));
            }
        });




    }

        private void startWebView(String url) {

            //Create new webview Client to show progress dialog
            //When opening a url or click on link

            webView.setWebViewClient(new WebViewClient() {
                ProgressDialog progressDialog;

                //If you will not use this method url links are opeen in new brower not in webview
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                //Show loader on url load
                public void onLoadResource (WebView view, String url) {
                    if (progressDialog == null) {
                        // in standard case YourActivity.this
                        progressDialog = new ProgressDialog(NewsWebView.this);
                        progressDialog.setMessage("Loading...");
                        //progressDialog.show();
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    try{
                        if (progressDialog.isShowing()) {
                            //progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    try{
                        if (progressDialog.isShowing()) {
                           // progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }

            });

            // Javascript inabled on webview
            webView.getSettings().setJavaScriptEnabled(true);

            // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

            //Load url in webview
            webView.loadUrl(url);


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


    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        /*if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }*/

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
