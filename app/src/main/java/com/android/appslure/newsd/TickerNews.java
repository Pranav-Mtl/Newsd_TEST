package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.BE.TickerNewsBE;
import com.android.BL.TickerNewsBL;
import com.android.CONSTANTS.Constant;
import com.android.appslure.newsd.TickerFlipAdapter.Callback;
import com.google.android.gms.analytics.HitBuilders;
import com.loopj.android.image.SmartImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;


public class TickerNews extends AppCompatActivity  implements Callback, FlipView.OnFlipListener{

    Button btnTitle;
    TextView tvContent,tvTicker;

    SmartImageView objSmartImageView;
    ProgressDialog mProgressDialog;

    private FlipView mFlipView;


    TickerNewsBE objTickerNewsBE;
    TickerNewsBL objTickerNewsBL;


    private TickerFlipAdapter  mAdapter;



    ImageButton img_bookmark,img_pugMark,img_share;

    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker_news);

        mFlipView = (FlipView) findViewById(R.id.flip_view);

        mProgressDialog=new ProgressDialog(TickerNews.this);

        mAdapter = new TickerFlipAdapter(this,mProgressDialog,TickerNews.this);

        mAdapter.setCallback(this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(this);
        mFlipView.peakNext(true);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setEmptyView(findViewById(R.id.empty_view));
        //mFlipView.setOnOverFlipListener(this);

      //  deviceID= Configuration.getSharedPrefrenceValue(getApplicationContext(), Constant.SHARED_PREFERENCE_ANDROID_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        //progressDialog=new ProgressDialog(TickerNews.this);

        objTickerNewsBE=new TickerNewsBE();
        objTickerNewsBL=new TickerNewsBL();



        try {
            MyApp.tracker().setScreenName("Ticker Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Ticker Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



      /*  objSmartImageView= (SmartImageView) findViewById(R.id.news_img);
        btnTitle= (Button) findViewById(R.id.post_heading);
        tvContent= (TextView) findViewById(R.id.post_text);
        tvTicker= (TextView) findViewById(R.id.ticker_text);
        tvTicker.setSelected(true);
        tvTicker.setText(Constant.tickerTitle);

        String newsId=getIntent().getExtras().get("NEWSID").toString();

         deviceID= Configuration.getSharedPrefrenceValue(getApplicationContext(),Constant.SHARED_PREFERENCE_ANDROID_ID);

        btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), NewsWebView.class);
                intent.putExtra("NewsURL", objTickerNewsBE.getUrl());
                startActivity(intent);
            }
        });

        img_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadWebPageTask().execute(deviceID,objTickerNewsBE.getNewsId());
            }
        });

        img_pugMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getApplicationContext(),Follow.class);
                intent1.putExtra("TAG",objTickerNewsBE.getTag());
                intent1.putExtra("NewsID",objTickerNewsBE.getNewsId());
                startActivity(intent1);
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, objTickerNewsBE.getUrl());
                startActivity(Intent.createChooser(shareIntent, "Share this news"));
            }
        });

        new GetNews().execute(newsId);*/
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
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private class GetNews extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            objTickerNewsBL.getBookMarkList(params[0],objTickerNewsBE);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            //pd.dismiss();
            btnTitle.setText(objTickerNewsBE.getTitle());
            // tvContent.setText(objBookMarkedNewsBE.getContent());
            objSmartImageView.setImageUrl(objTickerNewsBE.getImage());

            String text = "<font color=#ff7e16>"+objTickerNewsBE.getSource()+"</font>";
            System.out.println(text);
            tvContent.setText(Html.fromHtml(objTickerNewsBE.getContent() + "" + text));

        }
    }


    class DownloadWebPageTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... urls) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            String text = null;
            // http://newsd.in/demo/ws/bookmark.php?regID=3222222222&news_id=34

            String url="regID="+urls[0]+"&news_id="+urls[1];

            try {
                URI uri = new URI("http", "www.newsd.in", "/demo1/ws/bookmark",url, null);
                String ll = uri.toASCIIString();

                System.out.println("NEWS URL"+ll);
                HttpGet httpGet = new HttpGet(ll);



                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);


                    HttpEntity entity = response.getEntity();


                    text = getASCIIContentFromEntity(entity);


                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }
            catch (Exception e)
            {

            }


            return text;

        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);


            JSONParser jsonP=new JSONParser();
            String status="";



            try {

                Object obj = jsonP.parse(result);

                JSONArray jsonArrayObject = (JSONArray) obj;

                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

                status=jsonObject.get("status").toString();
                if(status.equals("1"))
                {
                    Toast.makeText(getApplicationContext(), "Bookmarked this news successfully", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Bookmarked Removed", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception E)
            {

            }



            // Toast.makeText(context, "Bookmarked this news successfully", Toast.LENGTH_LONG).show();


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
    public void onPageRequested(int page) {

        mFlipView.smoothFlipTo(page);


    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);

        if (position > mFlipView.getPageCount()-3 && mFlipView.getPageCount() < Constant.categoryNewsSize+12) {
            System.out.println("UNDER FLIPPED if Condition");
           /* mAdapter = new FlipAdapter(DemoViewFlipperActivity.this,12);
            mFlipView.setAdapter(mAdapter);*/



            // mAdapter.addItems(4);

        }
        else
        {

        }

    }

}
