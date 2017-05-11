package com.softtek.lai.module.laiClassroom;

import android.net.http.SslError;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_articdetail)
public class ArticdetailActivity extends BaseActivity {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.pb)
    ProgressBar pb;
    @InjectView(R.id.webView)
    WebView webView;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    private String articalUrl;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText(getIntent().getStringExtra("articaltitle"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                try {
                    if (newProgress == 100) {
                        pb.setVisibility(View.GONE);
                    } else {
                        if (pb.getVisibility() == View.GONE) {
                            pb.setVisibility(View.VISIBLE);
                        }
                    }
                    pb.setProgress(newProgress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                tv_title.setText(title);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initDatas() {
        articalUrl = getIntent().getStringExtra("articalUrl");
        Log.i("文章地址", articalUrl);
        if (!TextUtils.isEmpty(articalUrl)) {
            webView.loadUrl(articalUrl);
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
