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
import android.widget.TextView;

import com.android.BL.PreviousSearchBL;
import com.android.CONSTANTS.Constant;
import com.loopj.android.image.SmartImageView;

/**
 * Created by appslure on 7/17/2015.
 */
public class PreviousSearchAdapter extends BaseAdapter {
    private Context context;

    ProgressDialog pd;
    TextView btn;
    TextView textView,joinNow;
    SmartImageView newsImg;
    String searchText;
    PreviousSearchBL previousSearchBL;



    public PreviousSearchAdapter(Context context,String text)
    {

        this.searchText=text;
        this.context=context;
        previousSearchBL=new PreviousSearchBL();

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
        return Constant.preimg.length;

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
            gridView= layoutInflater.inflate(R.layout.previoussearch_adapter, null);

        }

        newsImg=(SmartImageView)gridView.findViewById(R.id.precomingSearch_news);
        //btn=(TextView)gridView.findViewById(R.id.precomingSearch_button);
        //textView=(TextView)gridView.findViewById(R.id.upcoming_text);
        //textView.setText(Constant.upnews_date[position]);

        newsImg.setImageUrl(Constant.preimg[position]);
       /* newsImg.setTag(position);
        newsImg.setOnClickListener(this);*/

        textView=(TextView)gridView.findViewById(R.id.precoming_text);
        //textView.setText("12 jan 2015");
        textView.setText(Constant.prenews_date[position]);


        // title.setText(Constant.FollowedNews[position]);

        gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return gridView;



    }


  /*  @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.precomingSearch_news:
                System.out.println("upcomig button  clicked");
                int pos = (int) v.getTag();
                Intent intent = new Intent(context, UpcomingNews.class);
                System.out.println("at time of puttin value--------->"+Constant.prenewsId[pos]);
                intent.putExtra("position",Constant.prenewsId[pos]);
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
            String result = previousSearchBL.preSearch(params[0]);
            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pd.dismiss();

        }


    }



}

