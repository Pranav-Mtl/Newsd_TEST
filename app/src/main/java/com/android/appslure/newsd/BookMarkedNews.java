package com.android.appslure.newsd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.BE.BookMarkedNewsBE;
import com.android.BL.BookMarkedNewsBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.facebook.appevents.AppEventsConstants;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class BookMarkedNews extends AppCompatActivity {

    BookMarkedNewsBE objBookMarkedNewsBE;
    BookMarkedNewsBL objBookMarkedNewsBL;
    SmartImageView objSmartImageView;
    TextView btnTitle;
    TextView tvContent,tvTicker;
    ProgressDialog pd;
    Activity activity;

    ImageButton img_bookmark,img_pugMark,img_share;

    String deviceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marked_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        activity=BookMarkedNews.this;

        pd=new ProgressDialog(BookMarkedNews.this);


        objSmartImageView= (SmartImageView) findViewById(R.id.news_img);
        btnTitle= (TextView) findViewById(R.id.post_heading);
        tvContent= (TextView) findViewById(R.id.post_text);
        tvTicker= (TextView) findViewById(R.id.ticker_text);
        tvTicker.setSelected(true);

        img_bookmark= (ImageButton) findViewById(R.id.img_btn_add_post_bookmark);
        img_pugMark= (ImageButton) findViewById(R.id.img_btn_post_setting);
        img_share= (ImageButton)findViewById(R.id.img_btn_post_share);

        img_bookmark.setVisibility(View.INVISIBLE);

        tvTicker.setText(Constant.tickerTitle);

        deviceID= Configuration.getSharedPrefrenceValue(BookMarkedNews.this,Constant.SHARED_PREFERENCE_ANDROID_ID);

        objBookMarkedNewsBE=new BookMarkedNewsBE();
        objBookMarkedNewsBL=new BookMarkedNewsBL();

        try {
            MyApp.tracker().setScreenName("BookMarked  News");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("BookMarked News Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String newsId=getIntent().getExtras().get("NEWSID").toString();
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_DESCRIPTION, "BookMarked News  "+newsId);
        logger.logEvent("BookMarked News Screen",parameters);

        btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewsWebView.class);
                intent.putExtra("NewsURL", objBookMarkedNewsBE.getUrl());
                startActivity(intent);
            }
        });

        try {
            new GetNews().execute(newsId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

     /*   img_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadWebPageTask().execute(deviceID,objBookMarkedNewsBE.getNewsId());
            }
        });*/

        img_pugMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getApplicationContext(),Follow.class);
                intent1.putExtra("TAG",objBookMarkedNewsBE.getTag());
                intent1.putExtra("NewsID",objBookMarkedNewsBE.getNewsId());
                startActivity(intent1);
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, objBookMarkedNewsBE.getUrl());
                startActivity(Intent.createChooser(shareIntent, "Share this news"));*/

                shareImage(objBookMarkedNewsBE.getTitle());
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
    private class GetNews extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objBookMarkedNewsBL.getBookMarkList(params[0],objBookMarkedNewsBE);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try {
                btnTitle.setText(objBookMarkedNewsBE.getTitle());
                // tvContent.setText(objBookMarkedNewsBE.getContent());
                objSmartImageView.setImageUrl(objBookMarkedNewsBE.getImage());

                String text = "<font color=#ff7e16>" + objBookMarkedNewsBE.getSource() + "</font>";
                System.out.println(text);
                tvContent.setText(Html.fromHtml(objBookMarkedNewsBE.getContent() + "" + text));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
            articleParams.put("Title",objBookMarkedNewsBE.getTitle());
            articleParams.put("NewsID",objBookMarkedNewsBE.getNewsId());
//up to 10 params can be logged with each event
            FlurryAgent.logEvent("BookMarked News Readed", articleParams);

        }
    }

    class DownloadWebPageTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);
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

            pd.dismiss();

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


    public void shareImage(String textLink){
        String path= Environment.getExternalStorageDirectory()+ File.separator+"Screenshot.jpeg";
        File imageFile=new File(path);
        // create bitmap screen capture
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        View v = activity.getWindow().getDecorView().findViewById(R.id.bookmark_mainnews).getRootView();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);
        // v1.setDrawingCacheEnabled( false);
        OutputStream fout = null ;
        try {
            fout = new FileOutputStream(imageFile);
            returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
            fout.flush();
            fout.close();
            //Toast.makeText(activity, "Image saved!", Toast.LENGTH_SHORT).show();
        } catch ( FileNotFoundException e) {
            // TODO Auto-generated catch block
            Toast.makeText(activity,"File not found!", Toast.LENGTH_SHORT).show();
            // e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Toast.makeText(activity, "IO Exception!", Toast.LENGTH_SHORT).show();
            // e.printStackTrace();
        }

        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        i.putExtra(Intent.EXTRA_TEXT, textLink+"\n"+"-via newsd"+"\n"+"http://www.newsd.in/app");

        activity.startActivity(i);
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
