package com.android.appslure.newsd;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by appslure on 8/17/2015.
 */
public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    public ViewPager getViewPager() {
        return mPager;
    }

    @Override
    protected void onFinishInflate() {
        try {

            mPager = (ViewPager) getChildAt(0);
            mPager.setOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
