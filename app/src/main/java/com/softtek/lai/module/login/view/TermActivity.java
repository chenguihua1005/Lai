package com.softtek.lai.module.login.view;

import android.webkit.WebView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 3/16/2016.
 */
@InjectLayout(R.layout.activity_term)
public class TermActivity extends BaseActivity{

    @InjectView(R.id.web_view)
    WebView webView;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        webView.loadUrl("file:///android_asset/privacy.html");
    }
}
