package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.CONSTANTS.Constant;
import com.loopj.android.image.SmartImageView;

/**
 * Created by appslure on 7/10/2015.
 */
public class EventNewsAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    ImageView imgview;
    ProgressDialog pd;
    TextView btn;
    UpcomingNewsed upcomingNewsed;
    TextView textView,joinNow;
    int newPosition;
    LinearLayout rlayout;
    private final int[] Imageid;
    SmartImageView newsImg;
    ProgressDialog mProgressDialog;




    public EventNewsAdapter(Context context,int[] Imageid,ProgressDialog progressDialog)
    {

        mProgressDialog=progressDialog;
    this.Imageid = Imageid;
    this.context=context;

        upcomingNewsed=new UpcomingNewsed();
        try {
            new Upcoming().execute().get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
       System.out.println("jthe value return by the apaterh ---"+Constant.imgUrl.length);
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
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View gridView=null;
        if (convertView != null){

            gridView=convertView;

        }else{
            gridView = new View(context);
            gridView= layoutInflater.inflate(R.layout.event_layout, null);

        }

        newsImg=(SmartImageView)gridView.findViewById(R.id.upcomingnews_img);
        //btn=(TextView)gridView.findViewById(R.id.event_button);
        textView=(TextView)gridView.findViewById(R.id.event_text);
        //textView.setText("12 jan 2015");
        textView.setText(Constant.upcomingdate[position]);

        rlayout= (LinearLayout) gridView.findViewById(R.id.header);
        btn.setBackgroundResource(R.drawable.event_join);
        newsImg.setImageUrl(Constant.imgUrl[position]);

        newsImg.setTag(position);
        newsImg.setOnClickListener(this);


       btn.setOnClickListener(this);



       // title.setText(Constant.FollowedNews[position]);

        gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return gridView;



    }


    @Override
    public void onClick(View v) {
     switch (v.getId())
        {
        case R.id.upcomingnews_img: {




            int pos = (int) v.getTag();
            Intent intent = new Intent(context, UpcomingNews.class);
            //System.out.println("at time of puttin value--------->"+Constant.preId[pos]);
            intent.putExtra("position",Constant.upcomingNewsID[pos]);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
             /*Intent intent=new Intent(context,PreviousNews.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.startActivity(intent);*/

        }
    }

    public class Upcoming extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
          /*  pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);*/

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


    }

}
