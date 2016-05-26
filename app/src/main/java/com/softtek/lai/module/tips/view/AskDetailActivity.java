package com.softtek.lai.module.tips.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.tips.model.AskHealthyModel;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_ask_detail)
public class AskDetailActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.webView)
    WebView webView;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("资讯详情");
    }

    @Override
    protected void initDatas() {
        AskHealthyModel model=getIntent().getParcelableExtra("ask");
        if(model!=null){
            webView.loadUrl(model.getTips_Link());

        }
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
