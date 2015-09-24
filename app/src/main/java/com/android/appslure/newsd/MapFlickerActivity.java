package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.CONSTANTS.Constant;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;


public class MapFlickerActivity extends AppCompatActivity implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener{

    private FlipView mFlipView;
    ProgressDialog mProgressDialog;
    MapFlipAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_flicker);

        mProgressDialog=new ProgressDialog(MapFlickerActivity.this);
        mFlipView = (FlipView) findViewById(R.id.flip_view_map);

        mAdapter = new MapFlipAdapter(this,mProgressDialog);
       //Adapter.setCallback(this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(MapFlickerActivity.this);
        mFlipView.peakNext(true);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setEmptyView(findViewById(R.id.empty_view));
        mFlipView.setOnOverFlipListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_flicker, menu);
        return true;
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


    @Override
    public void onPageRequested(int page) {

        mFlipView.smoothFlipTo(page);


    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);

        if (position > mFlipView.getPageCount()-3 && mFlipView.getPageCount() < Constant.newsSize+12) {
            System.out.println("UNDER FLIPPED if Condition");
           /* mAdapter = new FlipAdapter(DemoViewFlipperActivity.this,12);
            mFlipView.setAdapter(mAdapter);*/



            // mAdapter.addItems(4);

        }
        else
        {

        }

    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);


        if (overFlipDistance > 100) {

        }

    }

}
