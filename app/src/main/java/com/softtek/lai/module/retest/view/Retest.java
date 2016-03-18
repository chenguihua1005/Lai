package com.softtek.lai.module.retest.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.model.banji;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
@InjectLayout(R.layout.activity_retest)
public class Retest extends BaseActivity {
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.tv_left)
    TextView bar_left;

    private List<banji> banjiList=new ArrayList<banji>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        bar_left.setBackgroundResource(R.drawable.back_h);
        bar_title.setText("复测");
        bar_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,20)));

    }
}
