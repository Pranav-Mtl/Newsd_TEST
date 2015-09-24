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
import android.view.Menu;
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

import com.android.BL.BookMarkListBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;

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


public class BookMarkList extends AppCompatActivity {

    ListView tv;
    String deviceID;
    BookListAdapter objBookListAdapter;

    BookMarkListBL objBookMarkListBL;

    String result;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog=new ProgressDialog(BookMarkList.this);

       objBookMarkListBL=new BookMarkListBL();

        tv= (ListView) findViewById(R.id.timeline_result_tv);

        deviceID= Configuration.getSharedPrefrenceValue(BookMarkList.this,Constant.SHARED_PREFERENCE_ANDROID_ID);

        try {
            MyApp.tracker().setScreenName("BookMarked List Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("BookMarked List Screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("BookMarked List Screen");

        new GetBookMarkList().execute(deviceID);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_mark_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private class BookListAdapter extends BaseAdapter implements View.OnClickListener{

        Context mContext;
        TextView tvTitle,tvDate,tvPublisher,tvCategory;
        ImageButton btnDelete;


        public BookListAdapter(Context context,String deviceID,BookMarkListBL objBookMarkListBL)
        {
            mContext=context;
            try {

                result=objBookMarkListBL.getBookMarkList(deviceID);
                System.out.println("RESULT BookMark LIST"+result);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

        @Override
        public int getCount() {
            return Constant.BookMarked_Title.length;
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
                gridView= infalInflater.inflate(R.layout.bookmark_list_raw, null);

            }

            tvTitle= (TextView) gridView.findViewById(R.id.bookmarked_title);
            tvDate= (TextView) gridView.findViewById(R.id.bookmarked_date);
            tvPublisher= (TextView) gridView.findViewById(R.id.bookmarked_publisher);
            tvCategory= (TextView) gridView.findViewById(R.id.bookmark_list_category);
            btnDelete= (ImageButton) gridView.findViewById(R.id.bookmark_list_delete);

            tvTitle.setTag(position);
            btnDelete.setTag(position);

            tvTitle.setTag(position);
            tvTitle.setOnClickListener(this);
            btnDelete.setOnClickListener(this);

            tvTitle.setText(Constant.BookMarked_Title[position]);
            tvDate.setText(Constant.BookMarked_date[position]);
            tvPublisher.setText(Constant.BookMarked_Publisher[position]);
            tvDate.setText(Constant.BookMarked_date[position]);
            tvCategory.setText(Constant.BookMarked_Category[position].toUpperCase());



            //tvDate.setText("date");


            gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
            return gridView;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bookmark_list_delete:
                    int position= (int) v.getTag();
                    new RemoveBookMark().execute(deviceID,Constant.BookMarked_id[position]);
                    this.notifyDataSetChanged();
                    break;
                case R.id.bookmarked_title:
                    int pos= (int) v.getTag();
                    startActivity(new Intent(mContext,BookMarkedNews.class).putExtra("NEWSID",Constant.BookMarked_id[pos]));
                    break;
            }
        }
    }

    private class GetBookMarkList extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            objBookListAdapter=new BookListAdapter(getApplicationContext(),params[0],objBookMarkListBL);


            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

            if(result.equals("1")) {
                tv.setAdapter(objBookListAdapter);

                tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Intent intent = new Intent(getActivity(), BuyerProfile.class);
                intent.putExtra("ID", Constant.buyerIdArray[position]);
                intent.putExtra("MyPost", "allPost");
                startActivity(intent);*/

                        Intent intent = new Intent(getApplication(), NewsWebView.class);
                        intent.putExtra("NewsURL", Constant.BookMarked_Url[position]);
                        startActivity(intent);
                    }
                });
            }
            else
            {

            }
        }
    }

    private class RemoveBookMark extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
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
            JSONParser jsonP=new JSONParser();
            String status="";

            progressDialog.dismiss();

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

                    startActivity(new Intent(getApplicationContext(),BookMarkList.class));
                    finish();
                   // objBookListAdapter.notifyDataSetChanged();

                }
            } catch (Exception E)
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
