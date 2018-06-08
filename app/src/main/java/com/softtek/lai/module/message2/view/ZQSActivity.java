/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 *
 * Created by jerry.guan on 3/16/2016.
 */
@InjectLayout(R.layout.activity_term)
public class ZQSActivity extends BaseActivity {

    @InjectView(R.id.web_view)
    WebView webView;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("康宝莱使用知情书");
    }

    @Override
    protected void initDatas() {
        webView.loadUrl("file:///android_asset/note.html");
    }
}
