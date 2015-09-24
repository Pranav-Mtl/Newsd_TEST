package com.android.appslure.newsd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.BE.UpcomingNewBE;
import com.android.BL.UpcomingNewsBL;
import com.android.Configuration.Configuration;
import com.flurry.android.FlurryAgent;
import com.loopj.android.image.SmartImageView;

import java.util.HashMap;
import java.util.Map;


public class UpcomingNews extends ActionBarActivity {

    GalleryImage galleryImage;
    String imgId;

    TextView detailText,detailTitle,detailDate;
    SmartImageView gelleryImg;
    SmartImageView imgButton;
    SmartImageView one;
    SmartImageView two;
    SmartImageView three;
    SmartImageView four;
    SmartImageView five;
    SmartImageView six;
    SmartImageView seven;
    SmartImageView eight;

    UpcomingNewsBL objUpcomingNewsBL;
    UpcomingNewBE objupcomingNewBE;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_news);

        detailText=(TextView)findViewById(R.id.updetail);
        one=(SmartImageView)findViewById(R.id.upone);
        two=(SmartImageView)findViewById(R.id.uptwo);
        three=(SmartImageView)findViewById(R.id.upthree);
        four=(SmartImageView)findViewById(R.id.upfour);
        five=(SmartImageView)findViewById(R.id.upfive);
        six=(SmartImageView)findViewById(R.id.upsix);
        seven=(SmartImageView)findViewById(R.id.upseven);
        eight= (SmartImageView) findViewById(R.id.upeight);
        gelleryImg=(SmartImageView)findViewById(R.id.upgalll_img);

        detailTitle= (TextView) findViewById(R.id.upcoming_news_title);
        detailDate= (TextView) findViewById(R.id.upcoming_news_date);

        progressDialog=new ProgressDialog(UpcomingNews.this);

        objupcomingNewBE=new UpcomingNewBE();
        objUpcomingNewsBL=new UpcomingNewsBL();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        Intent intent=getIntent();
        imgId=intent.getStringExtra("position");
        System.out.println("insdit the activity---" + imgId);

        if(Configuration.isInternetConnection(UpcomingNews.this)) {
            new GetNewsData().execute(imgId);
        }
        else
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    UpcomingNews.this);

// Setting Dialog Title
            alertDialog2.setTitle("No Internet Connection");

// Setting Dialog Message
            alertDialog2.setMessage("Please check your internet connection and try again");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog

                            dialog.cancel();
                            finish();
                        }
                    });
// Setting Negative "NO" Btn


// Showing Alert Dialog
            alertDialog2.show();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
           finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private class GetNewsData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

        }


        @Override
        protected String doInBackground(String... params) {
            String result = objUpcomingNewsBL.getPreviousEventData(params[0],objupcomingNewBE);
            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.hide();
            if(s.equals("empty"))
            {

            }
            else
            {
                Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                articleParams.put("Title", objupcomingNewBE.getTitle());

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Upcoming_Event_Opened", articleParams);

                detailText.setText(objupcomingNewBE.getDescription());

                detailTitle.setText(objupcomingNewBE.getTitle());
                detailDate.setText(objupcomingNewBE.getEventdate());

                //Header Image

                if(objupcomingNewBE.getHeaderImage().equals(""))
                {

                }
                else
                {

                    gelleryImg.setImageUrl(objupcomingNewBE.getHeaderImage());
                }

                //Image One

                if(objupcomingNewBE.getGalleryImageOne().equals(""))
                {

                }
                else
                {
                    one.setVisibility(View.VISIBLE);
                    one.setImageUrl(objupcomingNewBE.getGalleryImageOne());
                }

                //Image Two

                if(objupcomingNewBE.getGalleryImageTwe().equals(""))
                {

                }
                else
                {
                    two.setVisibility(View.VISIBLE);
                    two.setImageUrl(objupcomingNewBE.getGalleryImageTwe());
                }

                if(objupcomingNewBE.getGalleryImageThree().equals(""))
                {

                }
                else
                {
                    three.setVisibility(View.VISIBLE);
                    three.setImageUrl(objupcomingNewBE.getGalleryImageThree());
                }

                //Image Two

                if(objupcomingNewBE.getGalleryImageFour().equals(""))
                {

                }
                else
                {
                    four.setVisibility(View.VISIBLE);
                    four.setImageUrl(objupcomingNewBE.getGalleryImageFour());
                }


                if(objupcomingNewBE.getGalleryImageFive().equals(""))
                {

                }
                else
                {
                    five.setVisibility(View.VISIBLE);
                    five.setImageUrl(objupcomingNewBE.getGalleryImageFive());
                }

                //Image Two

                if(objupcomingNewBE.getGalleryImageSix().equals(""))
                {

                }
                else
                {
                    six.setVisibility(View.VISIBLE);
                    six.setImageUrl(objupcomingNewBE.getGalleryImageSix());
                }

                if(objupcomingNewBE.getGalleryImageSeven().equals(""))
                {

                }
                else
                {
                    seven.setVisibility(View.VISIBLE);
                    seven.setImageUrl(objupcomingNewBE.getGalleryImageSeven());
                }

                if(objupcomingNewBE.getGalleryImageEight().equals(""))
                {

                }
                else
                {
                    eight.setVisibility(View.VISIBLE);
                    eight.setImageUrl(objupcomingNewBE.getGalleryImageEight());
                }



            }


        }
    }

}
