package com.eno.enotest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.eno.enotest.R;
import com.eno.enotest.activity.SettingActivity;

public class EnoFragment extends BaseFragment {

    private static String PAGE_TYPE = "page_type";
    private static String PAGE_NAME = "page_name";
    private int mType;

    private AppCompatTextView mTvPageName;
    private String mPageName;

    public static Fragment newInstance(final int type, final String name) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_TYPE, type);
        bundle.putString(PAGE_NAME, name);
        EnoFragment instance = new EnoFragment();
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public int bindLayoutId() {
        return R.layout.fragment_eno;
    }

    @Override
    public void initView(View rootView, Bundle bundle) {
        super.initView(rootView, bundle);
        if (bundle != null) {
            mType = bundle.getInt(PAGE_TYPE);
            mPageName = bundle.getString(PAGE_NAME);
            mTvPageName = rootView.findViewById(R.id.tv_page_name);
            mTvPageName.setText(mPageName);

            mTvPageName.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));
        }
    }
}
