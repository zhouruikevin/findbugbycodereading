package com.eno.enotest.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eno.enotest.R;
import com.eno.enotest.widget.EnoTabSelectedListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {


    private TabLayout mTabHome;
    private ViewPager mVpHome;

    private static List<Fragment> mFragments;

    private String[] itemNames = new String[]{"附近", "热门", "关注"};

    @SuppressLint("StaticFieldLeak")
    private static volatile HomeFragment instance;

    public static Fragment newInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mTabHome = rootView.findViewById(R.id.tab_home);
        mVpHome = rootView.findViewById(R.id.vp_home);

        mFragments = new ArrayList<>();
        for (int i = 0; i < itemNames.length; i++) {
            mFragments.add(EnoFragment.newInstance(i, itemNames[i]));
        }

        mVpHome.setAdapter(new MyAdapter(getFragmentManager()));
        mTabHome.addOnTabSelectedListener(new EnoTabSelectedListener());
        mTabHome.setupWithViewPager(mVpHome);
        mVpHome.setOffscreenPageLimit(1);
    }

    public class MyAdapter extends FragmentStatePagerAdapter {


        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return itemNames[position];
        }

    }
}
