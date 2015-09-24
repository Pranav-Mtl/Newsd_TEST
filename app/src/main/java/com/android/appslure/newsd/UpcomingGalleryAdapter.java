package com.android.appslure.newsd;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.CONSTANTS.Constant;
import com.loopj.android.image.SmartImageView;

/**
 * Created by appslure on 7/15/2015.
 */
public class UpcomingGalleryAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    TextView detailText;
    SmartImageView gelleryImg;
    SmartImageView imgButton;
    SmartImageView one;
    SmartImageView two;
    SmartImageView three;
    SmartImageView four;
    SmartImageView five;
    SmartImageView six;
    SmartImageView seven;

    String imgId;
    GalleryImage galleryImage;

    public UpcomingGalleryAdapter(Context context,String id)
    {
        imgId=id;
        System.out.println("inside the galllry"+id);
        galleryImage=new GalleryImage();
        try {
            new Galimg().execute().get();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        this.context=context;
    }
    @Override
    public int getCount() {

        return Constant.detail.length;
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
            gridView= layoutInflater.inflate(R.layout.gallery_image, null);

        }
        imgId=Constant.upcomingNewsID[position];
        System.out.println("sing iddddddddddddddddddddd------"+imgId);
        // imgview= (ImageView) gridView.findViewById(R.id.eventpic_secoond);
        detailText=(TextView)gridView.findViewById(R.id.detail);
        imgButton=(SmartImageView)gridView.findViewById(R.id.multipleImg);
        one=(SmartImageView)gridView.findViewById(R.id.one);
        two=(SmartImageView)gridView.findViewById(R.id.two);
        three=(SmartImageView)gridView.findViewById(R.id.three);
        four=(SmartImageView)gridView.findViewById(R.id.four);
        five=(SmartImageView)gridView.findViewById(R.id.five);
        six=(SmartImageView)gridView.findViewById(R.id.six);
        seven=(SmartImageView)gridView.findViewById(R.id.seven);
        gelleryImg=(SmartImageView)gridView.findViewById(R.id.galll_img);
        // textView.setText("12 july 2015");
        detailText.setText(Constant.detail[position]);
        gelleryImg.setImageUrl(Constant.gellery[position]);
        gelleryImg.setTag(position);
        imgButton.setImageUrl(Constant.gellery[position]);

        if(Constant.imgOne[position].equals(""))
        {

        }
        else {

            six.setImageUrl(Constant.imgOne[position]);
        }
        if(Constant.imgSix[position].equals(""))
        {

        }
        else {

            six.setImageUrl(Constant.imgSix[position]);
        }

        if(Constant.imgTwo[position].equals(""))
        {

        }
        else {

            two.setImageUrl(Constant.imgTwo[position]);
        }
       /* if(Constant.imgThree[position].equals(""))
        {

        }
        else {

            three.setImageUrl(Constant.imgThree[position]);
        }*/

        if(Constant.imgFour[position].equals(""))
        {

        }
        else {

            four.setImageUrl(Constant.imgFour[position]);
        }

        if(Constant.imgFive[position].equals(""))
        {

        }
        else {

            five.setImageUrl(Constant.imgFive[position]);
        }
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.previousnews_img: {
/*

                int pos = (int) v.getTag();
                String value=String.valueOf(pos);
                Intent intent = new Intent(context, PreviousNews.class);
                intent.putExtra("position",imgId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
*/


            }

        }
    }


    public class Galimg extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
          /*  pd.show();
            pd.setMessage("Loading...");
            pd.setCancelable(false);*/

        }


        @Override
        protected String doInBackground(String... params) {
            String result = galleryImage.getGalleryImages(imgId);
            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pd.dismiss();

        }


    }





}




