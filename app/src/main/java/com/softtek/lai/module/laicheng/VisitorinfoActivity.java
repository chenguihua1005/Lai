package com.softtek.lai.module.laicheng;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visitorinfo)
public class VisitorinfoActivity extends BaseActivity {


    @Override
    protected void initViews() {

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
