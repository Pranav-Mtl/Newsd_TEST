package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.BL.UpcomingSearchBL;
import com.android.CONSTANTS.Constant;
import com.loopj.android.image.SmartImageView;

/**
 * Created by appslure on 7/16/2015.
 */
public class UpcomingSearchAdapter extends BaseAdapter {
    private Context context;

    ProgressDialog pd;
    TextView btn;
    UpcomingNewsed upcomingNewsed;
    TextView textView,joinNow;
    int newPosition;
    RelativeLayout rlayout;
    SmartImageView newsImg;
    String searchText;
    UpcomingSearchBL  upcomingSearchBL;



    public UpcomingSearchAdapter(Context context,String text)
    {

        this.searchText=text;
        this.context=context;
        upcomingSearchBL=new UpcomingSearchBL();

        try {
            new PreSearch().execute(text).get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        System.out.println("jthe value return by the apaterh ---"+ Constant.imgUrl.length);
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
            gridView= layoutInflater.inflate(R.layout.upcomingsearch_adapter, null);

        }

        newsImg=(SmartImageView)gridView.findViewById(R.id.upcomingSearch_news);
       // btn=(TextView)gridView.findViewById(R.id.upcomingSearch_button);
        //textView=(TextView)gridView.findViewById(R.id.upcoming_text);
        //textView.setText(Constant.upnews_date[position]);
       /* btn.setBackgroundResource(R.drawable.event_join);
        btn.setTag(position);
        btn.setOnClickListener(this);*/
        newsImg.setImageUrl(Constant.upimg[position]);
       /* newsImg.setTag(position);
        newsImg.setOnClickListener(this);
*/
        textView=(TextView)gridView.findViewById(R.id.upcoming_text);
        //textView.setText("12 jan 2015");
        textView.setText(Constant.upnews_date[position]);


        // title.setText(Constant.FollowedNews[position]);

        gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return gridView;



    }


    /*@Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.upcomingSearch_news:
                System.out.println("upcomig button  clicked");
                int pos = (int) v.getTag();
                Intent intent = new Intent(context, UpcomingNews.class);
                System.out.println("at time of puttin value--------->"+Constant.upnewsId[pos]);
                intent.putExtra("position",Constant.upimg[pos]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;

        }
    }*/


    public class PreSearch extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
          /*  pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);*/

        }


        @Override
        protected String doInBackground(String... params) {
            String result = upcomingSearchBL.preSearch(params[0]);
            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pd.dismiss();

        }


    }



}

