package kalkull.ruslanann.kalkulator24.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.fragments.dvigatel.Nelineynost;
import kalkull.ruslanann.kalkulator24.fragments.dvigatel.OmCircleFrag;
import kalkull.ruslanann.kalkulator24.fragments.dvigatel.OmFrag;
import kalkull.ruslanann.kalkulator24.fragments.dvigatel.Rizolyaciia;

/**
 * Created by CARD on 16.03.2016.
 */
public class AdapterDV extends FragmentPagerAdapter {
    private Map<Integer, BaseFragment> tabs;
    private Context context;
    public AdapterDV(Context context, FragmentManager fm) {
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
        tabs.put(0, Rizolyaciia.getInstance(context));
        tabs.put(1, OmFrag.getInstance(context));
        tabs.put(2, OmCircleFrag.getInstance(context));
        tabs.put(3, Nelineynost.getInstance(context));
    }
}
