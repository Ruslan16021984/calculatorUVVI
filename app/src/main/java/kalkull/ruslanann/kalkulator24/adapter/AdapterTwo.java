package kalkull.ruslanann.kalkulator24.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.fragments.fragmentsTn.AXFragment;
import kalkull.ruslanann.kalkulator24.fragments.fragmentsTn.FragmentNN;

/**
 * Created by CARD on 08.01.2016.
 */
public class AdapterTwo extends FragmentPagerAdapter {
    private Map<Integer, BaseFragment> tabs;
    private Context context;
    public AdapterTwo(Context context, FragmentManager fm) {
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
        tabs.put(0, FragmentNN.getInstance(context));
        tabs.put(1, AXFragment.getInstance(context));
    }
}
