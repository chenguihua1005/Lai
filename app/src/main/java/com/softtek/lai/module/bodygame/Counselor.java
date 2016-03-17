package com.softtek.lai.module.bodygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_counselor)
public class Counselor extends BaseActivity {
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.tv_left)
    TextView bar_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        User user= (User) aCache.getAsObject(Constants.USER_ACACHE_KEY);
//        user.getUserrole();
    }

    @Override
    protected void initViews() {
        bar_title.setText("体管赛（顾问版）");
        bar_left.setBackgroundResource(R.drawable.back_h);
        bar_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,20)));


    }

    @Override
    protected void initDatas() {

    }
}
