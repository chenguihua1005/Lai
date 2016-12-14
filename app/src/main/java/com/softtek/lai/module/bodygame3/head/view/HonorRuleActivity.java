package com.softtek.lai.module.bodygame3.head.view;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_honor_rule)
public class HonorRuleActivity extends BaseActivity {
    private String url = "http://115.29.187.163:8082/HonorRole.html";

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.wv_honor_rule)
    WebView wv_honor_rule;

    @Override
    protected void initViews() {
        tv_title.setText(R.string.rule);
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
    }

    @Override
    protected void initDatas() {

    }
}
