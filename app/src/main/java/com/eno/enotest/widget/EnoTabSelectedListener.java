package com.eno.enotest.widget;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.google.android.material.tabs.TabLayout;

public class EnoTabSelectedListener implements TabLayout.OnTabSelectedListener {

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab == null || tab.getText() == null) {
            return;
        }
        String trim = tab.getText().toString().trim();
        SpannableString spStr = new SpannableString(trim);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spStr.setSpan(styleSpan, 0, trim.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tab.setText(spStr);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab == null || tab.getText() == null) {
            return;
        }
        String trim = tab.getText().toString().trim();
        SpannableString spStr = new SpannableString(trim);
        StyleSpan styleSpan = new StyleSpan(Typeface.NORMAL);
        spStr.setSpan(styleSpan, 0, trim.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tab.setText(spStr);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
