package com.eno.enotest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.eno.enotest.R;
import com.eno.enotest.fragment.EnoFragment;
import com.eno.enotest.fragment.HomeFragment;
import com.eno.enotest.fragment.MessageFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatTextView mTvHome;
    private AppCompatTextView mTvMessage;
    private AppCompatTextView mTvMine;

    private Fragment mHomeFragment;
    private Fragment mMessageFragment;
    private Fragment mMineFragment;

    private String FRAGMENT_INDEX = "fragment_index";
    private int indexAfter = -1;

    /**
     * 两个任意一个为 True 操作 退出 再进入就闪退
     */
    private boolean isViewPageMore = true;  //是否 ViewPage+N个 Fragment
    private boolean isViewPage2More = false; //是否 ViewPage2+N个 Fragment

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(final Bundle savedInstanceState) {
        mTvHome = findViewById(R.id.tv_home);
        mTvMessage = findViewById(R.id.tv_message);
        mTvMine = findViewById(R.id.tv_mine);


        mTvHome.setOnClickListener(this);
        mTvMessage.setOnClickListener(this);
        mTvMine.setOnClickListener(this);

        if (savedInstanceState != null) {
            indexAfter = savedInstanceState.getInt(FRAGMENT_INDEX, -1);
        }

        onCheckFragemnt(indexAfter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                onCheckFragemnt(0);
                break;
            case R.id.tv_message:
                onCheckFragemnt(1);
                break;
            case R.id.tv_mine:
                onCheckFragemnt(2);
                break;
        }
    }

    private void onCheckFragemnt(int index) {
        if (index == -1) {
            index = 0;
        }

        if (index == indexAfter) {
            return;
        }

        indexAfter = index;

        mTvHome.setSelected(0 == index);
        mTvMessage.setSelected(1 == index);
        mTvMine.setSelected(2 == index);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case 0:
                if (mHomeFragment == null) {
                    if (isViewPageMore) {
                        mHomeFragment = HomeFragment.newInstance();
                    } else {
                        mHomeFragment = EnoFragment.newInstance(index, "首页");
                    }
                    transaction.add(R.id.main_layout, mHomeFragment);
                }
                showFragment(mHomeFragment);
                break;
            case 1:
                if (mMessageFragment == null) {
                    if (isViewPage2More) {
                        mMessageFragment = MessageFragment.newInstance();
                    } else {
                        mMessageFragment = EnoFragment.newInstance(index, "消息");
                    }
                    transaction.add(R.id.main_layout, mMessageFragment);
                }
                showFragment(mMessageFragment);
                break;
            case 2:
                if (mMineFragment == null) {
                    mMineFragment = EnoFragment.newInstance(index, "我的");
                    transaction.add(R.id.main_layout, mMineFragment);
                }
                showFragment(mMineFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示对应的Fragment
     *
     * @param fragment
     */
    private void showFragment(final Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction
     */
    private void hideAllFragment(final FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_INDEX, indexAfter);
    }
}