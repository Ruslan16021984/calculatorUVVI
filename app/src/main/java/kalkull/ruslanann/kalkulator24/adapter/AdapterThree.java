package kalkull.ruslanann.kalkulator24.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.fragments.generator.FragmentG1;
import kalkull.ruslanann.kalkulator24.fragments.generator.FragmentG2;

/**
 * Created by CARD on 12.01.2016.
 */
public class AdapterThree extends FragmentPagerAdapter {
    private Map<Integer, BaseFragment> tabs;
    private Context context;
    public AdapterThree(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
        initTabMap();
    }
    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabMap() {
        tabs = new HashMap<>();
        tabs.put(0, FragmentG2.getInstance(context));
        tabs.put(1, FragmentG1.getInstance(context));
    }
}
