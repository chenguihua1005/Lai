package com.softtek.lai.module.home.view;

import android.net.http.SslError;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.model.HomeInfoModel;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_ask_detail)
public class ArticalDetailActivity extends BaseActivity{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.webView)
    WebView webView;

    @InjectView(R.id.pb)
    ProgressBar pb;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText(getIntent().getStringExtra("title"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // 为图片添加放大缩小功能
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                try {
                    if(newProgress==100){
                        pb.setVisibility(View.GONE);
                    }else {
                        if(pb.getVisibility()==View.GONE){
                            pb.setVisibility(View.VISIBLE);
                        }
                    }
                    pb.setProgress(newProgress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    @Override
    protected void initDatas() {
        HomeInfoModel model= (HomeInfoModel) getIntent().getSerializableExtra("info");
        if(model!=null){
            webView.loadUrl(model.getArtUrl());
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
