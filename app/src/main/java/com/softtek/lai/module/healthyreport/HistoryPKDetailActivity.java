package com.softtek.lai.module.healthyreport;

import android.app.ProgressDialog;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pk_detail)
public class HistoryPKDetailActivity extends BaseActivity {
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.wv_honor_rule)
    WebView wv_honor_rule;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    private ProgressDialog dialog;

    private String url ="";

    @Override
    protected void initViews() {
        url = getIntent().getStringExtra("url");

        dialog = ProgressDialog.show(this, null, "页面加载中，请稍后..");
        tv_title.setText("数据比较");
        wv_honor_rule.getSettings().setJavaScriptEnabled(true);// 开启jacascript
        wv_honor_rule.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 支持通过JS打开新窗口
        wv_honor_rule.getSettings().setSupportZoom(true);
        wv_honor_rule.getSettings().setLoadsImagesAutomatically(true);// 支持自动加载图片
//                wvTopic.requestFocusFromTouch();
        wv_honor_rule.getSettings().setCacheMode(wv_honor_rule.getSettings().LOAD_NO_CACHE);
        wv_honor_rule.getSettings().setBuiltInZoomControls(false);// 设置支持缩放
        wv_honor_rule.getSettings().setDisplayZoomControls(false);//隐藏缩放条
        wv_honor_rule.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页，如果没有这个在低分辨率手机上显示会异常
//        wv_honor_rule.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        wv_honor_rule.loadUrl(url);
//        wv_honor_rule.setWebViewClient(new WebViewClient());
//        wv_honor_rule.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //自适应屏幕
        wv_honor_rule.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_honor_rule.getSettings().setLoadWithOverviewMode(true);

        //
        wv_honor_rule.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void initDatas() {
    }

    @OnClick({R.id.ll_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
