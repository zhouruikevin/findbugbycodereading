package com.eno.enotest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eno.enotest.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    private View rootView;
    private BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(bindLayoutId(), container, false);
        mActivity = (BaseActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            initView(rootView, bundle);
        } else {
            initView(rootView);
        }
        return rootView;
    }

    public void initView(View rootView) {

    }

    public void initView(View rootView, Bundle bundle) {

    }

    public abstract int bindLayoutId();
}
