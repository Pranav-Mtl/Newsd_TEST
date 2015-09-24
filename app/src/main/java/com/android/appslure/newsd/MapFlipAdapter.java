package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFlipAdapter extends BaseAdapter implements OnClickListener {

    Context context;
    String deviceID;
    //LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ProgressDialog progressDialog;
    private final boolean[] mHighlightedPositions = new boolean[10];
    //	ImageLoader imageLoader;

    ViewHolder holder;
    HashMap<String, String> resultp = new HashMap<String, String>();
    int pos;
    public MapFlipAdapter(Context context,
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
    private Callback callback;
    private List<Item> items = new ArrayList<Item>();

    public MapFlipAdapter(Context context,ProgressDialog pd) {
        System.out.println("New Item Added (Constuctor)---->" + new Item());
        this.context = context;
        progressDialog=pd;
        deviceID= Configuration.getSharedPrefrenceValue(context,Constant.SHARED_PREFERENCE_ANDROID_ID);
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 3; i++) {
            System.out.println("New Item Added (Constuctor)---->" + new Item());
            items.add(new Item());
        }
        System.out.println("ID" + Item.id);
    }

    public MapFlipAdapter(Context context,int size) {
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
            convertView = inflater.inflate(R.layout.map_flick_raw, parent, false);

            holder.llJammu= (LinearLayout) convertView.findViewById(R.id.first);
            holder.llHaryana= (LinearLayout) convertView.findViewById(R.id.second);
            holder.llDelhi= (LinearLayout) convertView.findViewById(R.id.third);
            holder.llUp= (LinearLayout) convertView.findViewById(R.id.four);
            holder.llGujarat= (LinearLayout) convertView.findViewById(R.id.five);

            holder.jammu=(ImageButton)convertView.findViewById(R.id.jammu);
            holder.haryana=(ImageButton)convertView.findViewById(R.id.haryana);
            holder.delhi=(ImageButton)convertView.findViewById(R.id.delhi);
            holder.utterpradesh=(ImageButton)convertView.findViewById(R.id.utterakhand);
            holder.gujrat=(ImageButton)convertView.findViewById(R.id.gurjat);

            holder.text_one= (TextView) convertView.findViewById(R.id.text_one);
            holder.text_two= (TextView) convertView.findViewById(R.id.text_two);
            holder.text_three= (TextView) convertView.findViewById(R.id.text_three);
            holder.text_four= (TextView) convertView.findViewById(R.id.text_four);
            holder.text_five= (TextView) convertView.findViewById(R.id.text_five);

            holder.text_one.setText(Constant.JammuText[position]);
            holder.text_two.setText(Constant.HaryaneText[position]);
            holder.text_three.setText(Constant.DelhiText[position]);
            holder.text_four.setText(Constant.UPText[position]);
            holder.text_five.setText(Constant.GujaratText[position]);

            if(position==0)
            {
                if(Constant.JAMMU)
                    holder.jammu.setBackgroundResource(Constant.ImageJammuSelected[position]);
                else
                    holder.jammu.setBackgroundResource(Constant.ImageJammu[position]);

                if(Constant.HARYANA)
                    holder.haryana.setBackgroundResource(Constant.ImageHaryanaSelected[position]);
                else
                    holder.haryana.setBackgroundResource(Constant.ImageHaryana[position]);

                if(Constant.DELHI)
                    holder.delhi.setBackgroundResource(Constant.ImageDelhiSelected[position]);
                else
                    holder.delhi.setBackgroundResource(Constant.ImageDelhi[position]);

                if(Constant.UTTARPRADESH)
                    holder.utterpradesh.setBackgroundResource(Constant.ImageUPSelected[position]);
                else
                    holder.utterpradesh.setBackgroundResource(Constant.ImageUP[position]);

                if(Constant.GUJARAT)
                    holder.gujrat.setBackgroundResource(Constant.ImageGujratSelected[position]);
                else
                    holder.gujrat.setBackgroundResource(Constant.ImageGujrat[position]);
            }
            else if(position==1)
            {

                if(Constant.MADHYAPRADESH)
                    holder.jammu.setBackgroundResource(Constant.ImageJammuSelected[position]);
                else
                    holder.jammu.setBackgroundResource(Constant.ImageJammu[position]);

                if(Constant.BIHAR)
                    holder.haryana.setBackgroundResource(Constant.ImageHaryanaSelected[position]);
                else
                    holder.haryana.setBackgroundResource(Constant.ImageHaryana[position]);

                if(Constant.WB)
                    holder.delhi.setBackgroundResource(Constant.ImageDelhiSelected[position]);
                else
                    holder.delhi.setBackgroundResource(Constant.ImageDelhi[position]);

                if(Constant.ASSAM)
                    holder.utterpradesh.setBackgroundResource(Constant.ImageUPSelected[position]);
                else
                    holder.utterpradesh.setBackgroundResource(Constant.ImageUP[position]);

                if(Constant.MAHARASHTRA)
                    holder.gujrat.setBackgroundResource(Constant.ImageGujratSelected[position]);
                else
                    holder.gujrat.setBackgroundResource(Constant.ImageGujrat[position]);
            }

            else if(position==2)
            {

                if(Constant.TELANGANA)
                    holder.jammu.setBackgroundResource(Constant.ImageJammuSelected[position]);
                else
                    holder.jammu.setBackgroundResource(Constant.ImageJammu[position]);

                if(Constant.KARNATAKA)
                    holder.haryana.setBackgroundResource(Constant.ImageHaryanaSelected[position]);
                else
                    holder.haryana.setBackgroundResource(Constant.ImageHaryana[position]);

                if(Constant.KERALA)
                    holder.delhi.setBackgroundResource(Constant.ImageDelhiSelected[position]);
                else
                    holder.delhi.setBackgroundResource(Constant.ImageDelhi[position]);


            }


            holder.llJammu.setTag(position);
            holder.llHaryana.setTag(position);
            holder.llDelhi.setTag(position);
            holder.llUp.setTag(position);
            holder.llGujarat.setTag(position);

            holder.llJammu.setOnClickListener(this);
            holder.llHaryana.setOnClickListener(this);
            holder.llDelhi.setOnClickListener(this);
            holder.llGujarat.setOnClickListener(this);
            holder.llUp.setOnClickListener(this);




           /* //holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.firstPage = (Button) convertView.findViewById(R.id.first_page);
            holder.lastPage = (Button) convertView.findViewById(R.id.last_page);
          */

       /*     holder.news_img= (SmartImageView)convertView.findViewById(R.id.news_img);
            holder.btnText = (Button) convertView.findViewById(R.id.post_heading);
            holder.post_text = (TextView) convertView.findViewById(R.id.post_text);

            holder.img_bookmark= (ImageButton) convertView.findViewById(R.id.img_btn_add_post_bookmark);
            holder.img_pugMark= (ImageButton) convertView.findViewById(R.id.img_btn_post_setting);
            holder.img_share= (ImageButton) convertView.findViewById(R.id.img_btn_post_share);
            holder.ticker_text=(TextView) convertView.findViewById(R.id.ticker_text);

            holder.ticker_text.setSelected(true);*/





           /*holder.firstPage.setOnClickListener(this);
			holder.lastPage.setOnClickListener(this);*/

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }




        return convertView;
    }

    static class ViewHolder {
        TextView text_one,text_two,text_three,text_four,text_five;



        LinearLayout llJammu,llHaryana,llDelhi,llUp,llGujarat;

        ImageButton jammu,haryana,delhi,utterpradesh,gujrat;
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
            case R.id.first:

                break;
            case R.id.second:
                break;
            case R.id.third:
                break;
            case R.id.four:
                break;
            case R.id.five :
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


}
