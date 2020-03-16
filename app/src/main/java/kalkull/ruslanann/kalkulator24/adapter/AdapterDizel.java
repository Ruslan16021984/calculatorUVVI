package kalkull.ruslanann.kalkulator24.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.fragments.Dizel12.Dizel1;
import kalkull.ruslanann.kalkulator24.fragments.Dizel12.Dizel2;
import kalkull.ruslanann.kalkulator24.fragments.Dizel12.Dizel3;

/**
 * Created by CARD on 04.02.2016.
 */
public class AdapterDizel extends FragmentPagerAdapter {
    private Map<Integer, BaseFragment> tabs;
    private Context context;
    public AdapterDizel(Context context, FragmentManager fm){
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
        tabs.put(0, Dizel1.getInstance(context));
        tabs.put(1, Dizel2.getInstance(context));
        tabs.put(2, Dizel3.getInstance(context));
    }
}
