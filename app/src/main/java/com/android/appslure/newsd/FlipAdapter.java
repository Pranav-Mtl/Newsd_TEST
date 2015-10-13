package com.android.appslure.newsd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.BL.CategoryBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.android.DB.DBOperation;
import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlipAdapter extends BaseAdapter implements OnClickListener {

    Context context;
    String deviceID;
    //LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ProgressDialog progressDialog;
    Activity activity;
    int ii=0;
    private final boolean[] mHighlightedPositions = new boolean[100];
    //	ImageLoader imageLoader;

    ViewHolder holder;
    HashMap<String, String> resultp = new HashMap<String, String>();
    int pos;
    public FlipAdapter(Context context,
                       ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        //	imageLoader = new ImageLoader(context);
    }

    public interface Callback {
        public void onPageRequested(int page);
    }

    static class Item {
        static long id = 0;
        long mId;
        public Item() {
            mId = id++;
            System.out.println("mid Increment" + mId);

        }
        long getId() {

            System.out.println("mid" + mId);
            return mId;
        }
    }

    private LayoutInflater inflater;
    public Callback callback;
    private List<Item> items = new ArrayList<Item>();

    public FlipAdapter(Context context,ProgressDialog pd,Activity act,CategoryBL objCategoryBL,DBOperation dbOperation,String regID) {
        System.out.println("New Item Added (Constuctor)---->" + new Item());
        this.context = context;
        activity =act;
        progressDialog=pd;
        deviceID= Configuration.getSharedPrefrenceValue(context,Constant.SHARED_PREFERENCE_ANDROID_ID);
        inflater = LayoutInflater.from(context);

        objCategoryBL.getCategoryData(deviceID,"0",dbOperation,regID);

        for (int i = 0; i < Constant.newsSize; i++) {
            System.out.println("New Item Added (Constuctor)---->" + new Item());
            items.add(new Item());
        }
        System.out.println("ID" + Item.id);
    }

    public FlipAdapter(Context context,ProgressDialog pd,Activity act){
        this.context = context;
        activity =act;
        progressDialog=pd;
        deviceID= Configuration.getSharedPrefrenceValue(context, Constant.SHARED_PREFERENCE_ANDROID_ID);
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < Constant.newsSize; i++) {
            System.out.println("New Item Added (Constuctor)---->" + new Item());
            items.add(new Item());
        }
        System.out.println("ID" + Item.id);

    }

    public FlipAdapter(Context context,int size) {
        System.out.println("New Item Added (Constuctor)---->" + new Item());
        inflater = LayoutInflater.from(context);

        for (int i = 0; i < size; i++) {
            System.out.println("New Item Added (Constuctor)---->" + new Item());
            items.add(new Item());
        }
        System.out.println("ID" + Item.id);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getCount() {
                System.out.println("Return item SIZE (getCount)-->" + items.size());
                return items.size();
    }

    @Override
    public Object getItem(int position) {
        System.out.println("Return item position (getItem)-->"+position);
        return position;
    }

    @Override
    public long getItemId(int position) {

        System.out.println("GET ITEM ID (getItemId)---->" + items.get(position).getId());
        return items.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        if (convertView == null) {
            holder = new ViewHolder();
            //convertView = inflater.inflate(R.layout.page, parent, false);
            convertView = inflater.inflate(R.layout.activity_news_screen, parent, false);
           /* //holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.firstPage = (Button) convertView.findViewById(R.id.first_page);
            holder.lastPage = (Button) convertView.findViewById(R.id.last_page);
          */

            holder.news_img= (SmartImageView)convertView.findViewById(R.id.news_img);
            holder.btnText = (TextView) convertView.findViewById(R.id.post_heading);
            holder.post_text= (TextView) convertView.findViewById(R.id.post_text);

            holder.img_bookmark= (ImageButton) convertView.findViewById(R.id.img_btn_add_post_bookmark);
            holder.img_pugMark= (ImageButton) convertView.findViewById(R.id.img_btn_post_setting);
            holder.img_video= (ImageButton) convertView.findViewById(R.id.news_video);
            holder.img_share= (ImageButton) convertView.findViewById(R.id.img_btn_post_share);
            holder.ticker_text=(TextView) convertView.findViewById(R.id.ticker_text);
            holder.llRegresh= (LinearLayout) convertView.findViewById(R.id.layout_hit_refresh);
            holder.llSwipe= (LinearLayout) convertView.findViewById(R.id.layout_hit_swipe);
            holder.llRead= (LinearLayout) convertView.findViewById(R.id.layout_hit_read_story);
            holder.rlHeader= (RelativeLayout) convertView.findViewById(R.id.newsscreen);
            holder.btnGotIt= (Button) convertView.findViewById(R.id.btn_gotit);
            holder.rlHeader= (RelativeLayout) convertView.findViewById(R.id.body);
            holder.llEvent= (LinearLayout) convertView.findViewById(R.id.layout_hit_event);
            holder.llBreaking= (LinearLayout) convertView.findViewById(R.id.layout_hit_breaking);
            holder.llCategory= (LinearLayout) convertView.findViewById(R.id.layout_hit_category);
            holder.llTag= (LinearLayout) convertView.findViewById(R.id.layout_hit_tag);

            holder.post_text.setMovementMethod(new ScrollingMovementMethod());

            holder.ticker_text.setSelected(true);
            holder.ticker_text.setText(Constant.tickerTitle);
           /*holder.firstPage.setOnClickListener(this);
			holder.lastPage.setOnClickListener(this);*/

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        System.out.println("POSITION------->><<<" + position);
        System.out.println("NEXT PAGE COUNTER------->><<<" + Constant.next_pages);
        //TODO set a text with the id as well

        //	holder.text.setText(items.get(position).getId()+":"+position);



        final String swipe=Configuration.getSharedPrefrenceValue(context,Constant.SHARED_PREFERENCE_Swipe_Story_One);
        final String swipeSecond=Configuration.getSharedPrefrenceValue(context,Constant.SHARED_PREFERENCE_Swipe_Story_Two);
        // startActivity(new Intent(DemoViewFlipperActivity.this,StorySwipeOne.class));

        if(swipe==null)
        {
            holder.llRegresh.setVisibility(View.VISIBLE);
            holder.llRead.setVisibility(View.VISIBLE);
            holder.llSwipe.setVisibility(View.VISIBLE);
            holder.btnGotIt.setVisibility(View.VISIBLE);
            //holder.rlHeader.setBackgroundResource(R.drawable.black_overlay);
            ii=0;
        }
        else
        {
            holder.llRegresh.setVisibility(View.INVISIBLE);
            holder.llRead.setVisibility(View.INVISIBLE);
            holder.llSwipe.setVisibility(View.INVISIBLE);
            holder.btnGotIt.setVisibility(View.INVISIBLE);

            ii++;

            if(ii>2) {

                if (swipeSecond == null) {
                    holder.llCategory.setVisibility(View.VISIBLE);
                    holder.llEvent.setVisibility(View.VISIBLE);
                    holder.llBreaking.setVisibility(View.VISIBLE);
                    holder.llTag.setVisibility(View.VISIBLE);
                    holder.btnGotIt.setVisibility(View.VISIBLE);
                   // holder.rlHeader.setBackgroundResource(R.drawable.black_overlay);
                } else {
                    holder.llCategory.setVisibility(View.INVISIBLE);
                    holder.llEvent.setVisibility(View.INVISIBLE);
                    holder.llBreaking.setVisibility(View.INVISIBLE);
                    holder.llTag.setVisibility(View.INVISIBLE);
                    holder.btnGotIt.setVisibility(View.INVISIBLE);
                    holder.rlHeader.setBackgroundResource(android.R.color.transparent);
                }
            }
        }

        holder.btnGotIt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swipe==null)
                {
                  //  Toast.makeText(context,"First time",Toast.LENGTH_SHORT).show();
                    holder.llRegresh.setVisibility(View.INVISIBLE);
                    holder.llRead.setVisibility(View.INVISIBLE);
                    holder.llSwipe.setVisibility(View.INVISIBLE);
                    holder.btnGotIt.setVisibility(View.INVISIBLE);
                    holder.rlHeader.setBackgroundResource(android.R.color.transparent);
                    Configuration.setSharedPrefrenceValue(context, Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_Swipe_Story_One, "not null");
                    notifyDataSetChanged();


                }
                else if(swipeSecond==null)
                {
                    //Toast.makeText(context,"Not First time",Toast.LENGTH_SHORT).show();
                    holder.llCategory.setVisibility(View.INVISIBLE);
                    holder.llEvent.setVisibility(View.INVISIBLE);
                    holder.llBreaking.setVisibility(View.INVISIBLE);
                    holder.llTag.setVisibility(View.INVISIBLE);
                    holder.btnGotIt.setVisibility(View.INVISIBLE);
                    holder.rlHeader.setBackgroundResource(android.R.color.transparent);
                    Configuration.setSharedPrefrenceValue(context, Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_Swipe_Story_Two, "not null");
                    notifyDataSetChanged();
                }
            }
        });

        Constant.currentPage=position;

       // System.out.println("TITLE lenght----->" + Constant.title.length);
        holder.btnText.setText(Constant.title[position]);

        String text = "<font color=#ff7e16>"+Constant.publisher[position]+"</font>";
        System.out.println(text);

        holder.post_text.setText(Html.fromHtml(Constant.content[position] + " " + text));

        if(Constant.video[position].equals("1")){
            holder.img_video.setVisibility(View.VISIBLE);
        }
        else {
            holder.img_video.setVisibility(View.GONE);
        }

        //holder.news_img.setImageUrl(Constant.imageURL[position]);

        Picasso.with(context)
                .load(Constant.imageURL[position])
                .placeholder(R.drawable.newsd_big_pic)
                .error(R.drawable.newsd_big_pic)
                .into(holder.news_img);

        holder.ticker_text.setText(Constant.tickerTitle);
        holder.img_video.setTag(position);
        holder.img_video.setOnClickListener(this);

        holder.ticker_text.setTag(position);
        holder.ticker_text.setOnClickListener(this);

      /*  if(Constant.bookmarkStatus[position]==1)
        {
            holder.img_bookmark.setBackgroundResource(R.drawable.bookmark_big);
        }
        else
        {
            holder.img_bookmark.setBackgroundResource(R.drawable.bookmark_big);
        }

        if(Constant.followStatus[position]==1)
        {
            holder.img_pugMark.setBackgroundResource(R.drawable.post_setting_img);
        }
        else
        {
            holder.img_pugMark.setBackgroundResource(R.drawable.post_setting_img);
        }*/

       /* if(mHighlightedPositions[position]) {
            holder.img_share.setBackgroundResource(R.drawable.post_share_img);
        }else {
            holder.img_share.setBackgroundResource(R.drawable.post_share_img_default);
        }*/

        //int post= (int) holder.img_bookmark.getTag();


        holder.img_bookmark.setTag(position);
        holder.img_bookmark.setOnClickListener(this);

        holder.btnText.setTag(position);
        holder.btnText.setOnClickListener(this);

        holder.img_pugMark.setTag(position);
        holder.img_pugMark.setOnClickListener(this);

        holder.img_share.setTag(position);
        holder.img_share.setOnClickListener(this);


         pos=position-1;



        return convertView;
    }

    static class ViewHolder {
        TextView post_text, post_heading,post_publisher,post_time,ticker_text;
        SmartImageView news_img;
        ImageButton img_bookmark,img_pugMark,img_share,img_video;
        TextView btnText;
        LinearLayout llRegresh,llRead,llSwipe,llBreaking,llTag,llEvent,llCategory;
        RelativeLayout rlHeader;
        Button btnGotIt;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.first_page:
                if (callback != null) {
                    System.out.println("FIRST PAGE");
                    callback.onPageRequested(0);
                }
                break;
            case R.id.last_page:
                if (callback != null) {
                    System.out.println("LAST PAGE");
                    callback.onPageRequested(getCount() - 1);
                }
                break;
            case R.id.post_heading:
                int posHeading= (int) v.getTag();
                Intent intent= new Intent(context, NewsWebView.class);
                intent.putExtra("NewsURL", Constant.newsURL[posHeading]);
                context.startActivity(intent);
                break;
            case R.id.img_btn_add_post_bookmark:
                System.out.println("I AM CLICKED");

                int posBookMark= (int) v.getTag();
                //Toast.makeText(context,Constant.title[Constant.currentPage-1],Toast.LENGTH_LONG).show();


                try {
                new DownloadWebPageTask().execute(deviceID, Constant.newsID[posBookMark]);


                }
                catch (Exception E)
                {
                    E.printStackTrace();
                }
                break;
            case R.id.img_btn_post_setting:
              //  holder.img_pugMark.setBackgroundResource(R.drawable.post_setting_img_selected);

                int posFollow= (int) v.getTag();
                Intent intent1=new Intent(context,Follow.class);
                intent1.putExtra("TAG",Constant.tag[posFollow]);
                intent1.putExtra("NewsID",Constant.newsID[posFollow]);
                context.startActivity(intent1);
                break;
            case R.id.img_btn_post_share:
               // System.out.println(" PUGMARK  AM CLICKED");
/*
                int position= (int) holder.img_share.getTag();
                if(mHighlightedPositions[position]) {
                    holder.img_share.setBackgroundResource(R.drawable.post_share_img_default);
                    mHighlightedPositions[position] = false;
                }else {
                    holder.img_share.setBackgroundResource(R.drawable.post_share_img);
                    mHighlightedPositions[position] = true;
                }*/

                int posShare= (int) v.getTag();
                shareImage(Constant.title[posShare]);

             /*
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, Constant.newsURL[posShare]);
                context.startActivity(Intent.createChooser(shareIntent, "Share this news"));*/
                break;
              /*  Toast.makeText(context,Constant.title[Constant.currentPage-1],Toast.LENGTH_LONG).show();
                new DownloadWebPageTask().execute(deviceID, Constant.newsID[Constant.currentPage-1]);*/
            case R.id.ticker_text:

               /* if (callback != null) {
                    System.out.println("LAST PAGE");
                    callback.onPageRequested(getCount() - 1);
                }*/

                int posTicker= (int) v.getTag();
                Intent intent2=new Intent(context,TickerNews.class);
               // intent2.putExtra("NEWSID",Constant.tickerID);
                context.startActivity(intent2);

                break;
            case R.id.news_video:
                int posHeadingVideo= (int) v.getTag();
                Intent intentVideo= new Intent(context, NewsWebView.class);
                intentVideo.putExtra("NewsURL", Constant.newsURL[posHeadingVideo]);
                context.startActivity(intentVideo);
                break;
        }
    }

    public void addItems(int amount) {
        for (int i = 0; i < amount; i++) {
            items.add(new Item());
        }
        notifyDataSetChanged();
    }

    public void addItemsBefore(int amount) {
        for (int i = 0; i < amount; i++) {
            items.add(0, new Item());
        }
        notifyDataSetChanged();
    }

     class DownloadWebPageTask extends AsyncTask<String, String, String> {

         @Override
         protected void onPreExecute() {
             progressDialog.show();
             progressDialog.setMessage("Loading...");
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
            //super.onPostExecute(result);


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
                    Toast.makeText(context, "Bookmarked this news successfully", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Bookmarked Removed", Toast.LENGTH_SHORT).show();
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
        View v = activity.getWindow().getDecorView().findViewById(R.id.newsscreen).getRootView();
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
        i.putExtra(Intent.EXTRA_TEXT, textLink+"\n"+"#GetNewsd"+"\n"+"www.newsd.in/app");
        activity.startActivity(i);
    }

}
