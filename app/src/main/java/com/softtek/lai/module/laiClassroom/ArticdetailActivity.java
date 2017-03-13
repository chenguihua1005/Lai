package com.softtek.lai.module.laiClassroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
    private String articalUrl;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        });
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        articalUrl = intent.getStringExtra("articalUrl");
        Log.i("文章地址", articalUrl);
        if (!TextUtils.isEmpty(articalUrl)) {
            webView.loadUrl(articalUrl);
        }

    }


}
