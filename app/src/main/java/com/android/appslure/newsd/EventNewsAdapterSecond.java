package com.android.appslure.newsd;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.CONSTANTS.Constant;
import com.loopj.android.image.SmartImageView;

/**
 * Created by appslure on 7/10/2015.
 */
public class EventNewsAdapterSecond extends BaseAdapter{
    private Context context;
    ImageView imgview;
    TextView btn;
    TextView textView;
    SmartImageView previousImg;
    PreviousNewsed previousNewsed;
    String imgId;


    public EventNewsAdapterSecond(Context context)
    {

        this.context=context;
    }
    @Override
    public int getCount() {

        return Constant.preImgUrl.length;
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
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View gridView=null;
        if (convertView != null){

            gridView=convertView;

        }else{
            gridView = new View(context);
            gridView= layoutInflater.inflate(R.layout.event_layoutsecond, null);

        }

       // imgview= (ImageView) gridView.findViewById(R.id.eventpic_secoond);
       // btn=(TextView)gridView.findViewById(R.id.event_buttonsecond);
        //btn.setBackgroundResource(R.drawable.event_ended);
        previousImg=(SmartImageView)gridView.findViewById(R.id.previousnews_img);
        textView=(TextView)gridView.findViewById(R.id.event_textsecond);
       // textView.setText("12 july 2015");
        textView.setText(Constant.preDate[position]);
        previousImg.setImageUrl(Constant.preImgUrl[position]);


      /*  previousImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PreviousNews.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/

        //imgview.setImageResource(R.drawable.newslogo);
        // title.setText(Constant.FollowedNews[position]);

        gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
        return gridView;


    }




    public class PreNews extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
          /*  pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);*/

        }


        @Override
        protected String doInBackground(String... params) {
            String result = previousNewsed.getPreviousNewsed();
            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pd.dismiss();

        }


    }




}
