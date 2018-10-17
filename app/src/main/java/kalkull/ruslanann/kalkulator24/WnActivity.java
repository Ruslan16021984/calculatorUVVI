package kalkull.ruslanann.kalkulator24;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import kalkull.ruslanann.kalkulator24.adapter.Adapter;
import kalkull.ruslanann.kalkulator24.screenfragments.SlidingTabLayout;

public class WnActivity extends BaseDrawerActivity {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_one);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        Adapter adapter = new Adapter(this,getSupportFragmentManager());
        mViewPager.setAdapter(adapter);


        // Give the SlidingTabLayout the ViewPager, this must be
        // done AFTER the ViewPager has had it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
}
