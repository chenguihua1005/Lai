package com.softtek.lai.module.laicheng;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visitorinfo)
public class VisitorinfoActivity extends BaseActivity {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_name)
    TextView tv_name;


    @Override
    protected void initViews() {
        tv_title.setText("访客信息");
        ll_left.setVisibility(View.INVISIBLE);

        String name = "姓名*";
        SpannableStringBuilder builder = new SpannableStringBuilder(name);
        builder.setSpan(new BackgroundColorSpan(Color.RED), 1, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_name.setText(builder);


    }

    @Override
    protected void initDatas() {
//        /** 设置宽度为屏幕的0.9*/
//        WindowManager windowManager = getWindowManager();
//    /* 获取屏幕宽、高 */
//        Display display = windowManager.getDefaultDisplay();
//    /* 获取对话框当前的参数值 */
//        WindowManager.LayoutParams p = getWindow().getAttributes();
//    /* 宽度设置为屏幕的0.9 */
//        p.width = display.getWidth();
//    /* 设置透明度,0.0为完全透明，1.0为完全不透明 */
//        p.height = display.getHeight();
//    /* 设置布局参数 */
//        getWindow().setAttributes(p);
////    getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
////        ViewGroup.LayoutParams.WRAP_CONTENT);
//    /* 设置点击弹框外部不可消失 */
////        setFinishOnTouchOutside(false);
    }
}
