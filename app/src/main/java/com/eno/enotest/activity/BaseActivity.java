package com.eno.enotest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eno.enotest.R;
import com.eno.enotest.manager.AppManager;

public abstract class BaseActivity extends AppCompatActivity {

    private View mView;
    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mView == null) {
            mView = LayoutInflater.from(this)
                    .inflate(bindLayoutId(), null);
        }
        setContentView(mView);
        mActivity = this;
        AppManager.getAppManager().addActivity(mActivity);
        initData(savedInstanceState);
    }

    protected abstract int bindLayoutId();

    protected abstract void initData(final Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

}
