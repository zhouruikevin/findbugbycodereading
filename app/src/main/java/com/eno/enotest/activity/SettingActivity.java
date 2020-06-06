package com.eno.enotest.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;

import com.eno.enotest.R;
import com.eno.enotest.manager.AppManager;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {

    private AppCompatTextView mTvExitApp;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData(final Bundle savedInstanceState) {

        mTvExitApp = findViewById(R.id.tv_exit_app);
        mTvExitApp.setOnClickListener(v -> {
            AppManager.getAppManager().finishAllActivity();

            Intent loginIntent = new Intent(this, SplashActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        });
    }
}