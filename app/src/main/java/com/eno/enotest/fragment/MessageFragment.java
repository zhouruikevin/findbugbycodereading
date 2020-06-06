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
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.eno.enotest.R;
import com.eno.enotest.widget.EnoTabSelectedListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment {


    @Override
    public int bindLayoutId() {
        return R.layout.fragment_message;
    }

    private TabLayout mTabMessage;
    private ViewPager2 mVpMessage;

    private static List<Fragment> mFragments;

    private String[] itemNames = new String[]{"附近", "热门", "关注"};

    @SuppressLint("StaticFieldLeak")
    private static volatile MessageFragment instance;

    public static Fragment newInstance() {
        if (instance == null) {
            instance = new MessageFragment();
        }
        return instance;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mTabMessage = rootView.findViewById(R.id.tab_message);
        mVpMessage = rootView.findViewById(R.id.vp_message);

        mFragments = new ArrayList<>();
        for (int i = 0; i < itemNames.length; i++) {
            mFragments.add(EnoFragment.newInstance(i, itemNames[i]));
        }

        mVpMessage.setAdapter(new MyAdapter(this));
        mTabMessage.addOnTabSelectedListener(new EnoTabSelectedListener());
        new TabLayoutMediator(mTabMessage, mVpMessage, true, (tab, position) -> {
            tab.setText(itemNames[position]);
        }).attach();
    }

    public class MyAdapter extends FragmentStateAdapter {


        public MyAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }
    }
}
