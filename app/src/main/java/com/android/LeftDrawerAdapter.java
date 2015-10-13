package com.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.CONSTANTS.Constant;
import com.android.appslure.newsd.R;

/**
 * Created by Pranav Mittal on 10/9/2015.
 * Appslure WebSolution LLP
 * www.appslure.com
 */
public class LeftDrawerAdapter extends BaseAdapter{

    Context mContext;
    ImageView imgSide;
    TextView tvSide;

    public LeftDrawerAdapter(Context context){
        mContext=context;
    }
    @Override
    public int getCount() {
        return Constant.leftDrawer.length;
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
            gridView= infalInflater.inflate(R.layout.drawer_list_raw, null);

            imgSide= (ImageView) gridView.findViewById(R.id.iv_drawer);
            tvSide= (TextView) gridView.findViewById(R.id.tv_drawer);

        }

        tvSide.setText(Constant.leftDrawer[position]);
        if(position!=0) {
            imgSide.setImageResource(Constant.leftDrawerImg[position]);
        }

        gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
        return gridView;
    }
}
