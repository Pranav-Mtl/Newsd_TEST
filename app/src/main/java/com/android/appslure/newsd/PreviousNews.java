package com.android.appslure.newsd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.BE.PreviousNewsBE;
import com.android.BL.PreviousNewsBL;
import com.android.Configuration.Configuration;
import com.flurry.android.FlurryAgent;
import com.loopj.android.image.SmartImageView;

import java.util.HashMap;
import java.util.Map;

public class PreviousNews extends AppCompatActivity {

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

    PreviousNewsBL objPreviousNewsBL;
    PreviousNewsBE objPreviousNewsBE;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        detailText=(TextView)findViewById(R.id.detail);
        one=(SmartImageView)findViewById(R.id.preone);
        two=(SmartImageView)findViewById(R.id.pretwo);
        three=(SmartImageView)findViewById(R.id.prethree);
        four=(SmartImageView)findViewById(R.id.prefour);
        five=(SmartImageView)findViewById(R.id.prefive);
        six=(SmartImageView)findViewById(R.id.presix);
        seven=(SmartImageView)findViewById(R.id.preseven);
        eight= (SmartImageView) findViewById(R.id.preeight);
        gelleryImg=(SmartImageView)findViewById(R.id.pregalll_img);

        detailTitle= (TextView) findViewById(R.id.previous_news_title);
        detailDate= (TextView) findViewById(R.id.previous_news_date);

        progressDialog=new ProgressDialog(PreviousNews.this);

        objPreviousNewsBE=new PreviousNewsBE();
        objPreviousNewsBL=new PreviousNewsBL();


        Intent intent=getIntent();
        imgId=intent.getStringExtra("position");
        System.out.println("insdit the activity---" + imgId);

        if(Configuration.isInternetConnection(PreviousNews.this)) {
            new GetNewsData().execute(imgId);
        }
        else
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    PreviousNews.this);

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
            String result = objPreviousNewsBL.getPreviousEventData(params[0],objPreviousNewsBE);
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
                articleParams.put("Title", objPreviousNewsBE.getTitle());

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Upcoming_Event_Opened", articleParams);
                detailText.setText(objPreviousNewsBE.getDescription());
                detailTitle.setText(objPreviousNewsBE.getTitle());
                detailDate.setText(objPreviousNewsBE.getEventdate());

                //Header Image

                if(objPreviousNewsBE.getHeaderImage().equals(""))
                {

                }
                else
                {

                    gelleryImg.setImageUrl(objPreviousNewsBE.getHeaderImage());
                }

                //Image One

                if(objPreviousNewsBE.getGalleryImageOne().equals(""))
                {

                }
                else
                {
                    one.setVisibility(View.VISIBLE);
                    one.setImageUrl(objPreviousNewsBE.getGalleryImageOne());
                }

                //Image Two

                if(objPreviousNewsBE.getGalleryImageTwe().equals(""))
                {

                }
                else
                {
                    two.setVisibility(View.VISIBLE);
                    two.setImageUrl(objPreviousNewsBE.getGalleryImageTwe());
                }

                if(objPreviousNewsBE.getGalleryImageThree().equals(""))
                {

                }
                else
                {
                    three.setVisibility(View.VISIBLE);
                    three.setImageUrl(objPreviousNewsBE.getGalleryImageThree());
                }

                //Image Two

                if(objPreviousNewsBE.getGalleryImageFour().equals(""))
                {

                }
                else
                {
                    four.setVisibility(View.VISIBLE);
                    four.setImageUrl(objPreviousNewsBE.getGalleryImageFour());
                }


                if(objPreviousNewsBE.getGalleryImageFive().equals(""))
                {

                }
                else
                {
                    five.setVisibility(View.VISIBLE);
                    five.setImageUrl(objPreviousNewsBE.getGalleryImageFive());
                }

                //Image Two

                if(objPreviousNewsBE.getGalleryImageSix().equals(""))
                {

                }
                else
                {
                    six.setVisibility(View.VISIBLE);
                    six.setImageUrl(objPreviousNewsBE.getGalleryImageSix());
                }
                if(objPreviousNewsBE.getGalleryImageSeven().equals(""))
                {

                }
                else
                {
                    seven.setVisibility(View.VISIBLE);
                    seven.setImageUrl(objPreviousNewsBE.getGalleryImageSeven());
                }
                if(objPreviousNewsBE.getGalleryImageEight().equals(""))
                {

                }
                else
                {
                    eight.setVisibility(View.VISIBLE);
                    eight.setImageUrl(objPreviousNewsBE.getGalleryImageEight());
                }



            }


        }
    }

    }
