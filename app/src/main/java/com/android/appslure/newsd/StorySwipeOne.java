package com.android.appslure.newsd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.android.Configuration.OnSwipeTouchListener;

public class StorySwipeOne extends AppCompatActivity {

    ImageView imgSwipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_swipe_one);

        imgSwipe= (ImageView) findViewById(R.id.swipe_category);

        Configuration.setSharedPrefrenceValue(StorySwipeOne.this, Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_Swipe_Story_One, "Selected");

        imgSwipe.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

            public boolean onSwipeTop() {
                //Toast.makeText(StorySwipeOne.this, "top", Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onSwipeRight() {
                // Toast.makeText(StorySwipeOne.this, "right", Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onSwipeLeft() {
                //Toast.makeText(StorySwipeOne.this, "left", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StorySwipeOne.this, StorySwipeTwo.class));
                finish();
                return true;
            }

            public boolean onSwipeBottom() {
                // Toast.makeText(StorySwipeOne.this, "bottom", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        imgSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StorySwipeOne.this, StorySwipeTwo.class));
                finish();
            }
        });
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

        return super.onOptionsItemSelected(item);
    }
}
