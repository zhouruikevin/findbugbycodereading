package com.eno.enotest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.eno.enotest.R;
import com.eno.enotest.manager.AppManager;

public class SplashActivity extends BaseActivity {


    public static final int SKIP_MAIN = 1000;

    private Handler mHandle = new Handler(msg -> {
        if (msg.what == SKIP_MAIN) {
            toNextActivity();
        }
        return false;
    });

    private void toNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        AppManager.getAppManager().finishActivity(SplashActivity.class);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData(final Bundle savedInstanceState) {
        mHandle.sendEmptyMessageDelayed(SKIP_MAIN, 2 * 1000);
    }
}