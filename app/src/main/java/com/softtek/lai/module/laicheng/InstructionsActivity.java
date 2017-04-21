package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;

public class InstructionsActivity extends AppCompatActivity implements View.OnClickListener{
    WebView cWebView;
    TextView tv_title;
    LinearLayout ll_left;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setTitle("使用说明书");
        cWebView = (WebView) findViewById(R.id.activity_instruction_web);
        tv_title=(TextView) findViewById(R.id.tv_title);
        ll_left=(LinearLayout)findViewById(R.id.ll_left);
        ll_left.setOnClickListener(this);
        tv_title.setText("使用说明");
        cWebView.loadUrl("https://api.yunyingyang.com/html/help.html");
        cWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        cWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && cWebView.canGoBack()) {
            cWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
