package kalkull.ruslanann.kalkulator24.adapter.inner_adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.adapter.AdapterTwo;


/**
 * Created by CARD on 07.01.2016.
 */
public class ScreenTwo extends Fragment {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;



    public ScreenTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screen_one, container,
                false);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        AdapterTwo adapter = new AdapterTwo(getActivity(),getChildFragmentManager() );
        mViewPager.setAdapter(adapter);

        // Give the SlidingTabLayout the ViewPager, this must be
        // done AFTER the ViewPager has had it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

}
