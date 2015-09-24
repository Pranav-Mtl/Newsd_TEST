package com.android.appslure.newsd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.android.Configuration.OnSwipeTouchListener;

public class CategorySwipe extends AppCompatActivity {

    ImageView imgSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_swipe);

        imgSwipe= (ImageView) findViewById(R.id.swipe_category);


        Configuration.setSharedPrefrenceValue(CategorySwipe.this, Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_Swipe_Category, "Selected");

        imgSwipe.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

            public boolean onSwipeTop() {
               // Toast.makeText(CategorySwipe.this, "top", Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onSwipeRight() {
               // Toast.makeText(CategorySwipe.this, "right", Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onSwipeLeft() {
                //Toast.makeText(CategorySwipe.this, "left", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }

            public boolean onSwipeBottom() {
               // Toast.makeText(CategorySwipe.this, "bottom", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        imgSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
