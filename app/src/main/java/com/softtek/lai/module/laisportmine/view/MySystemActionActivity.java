package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.adapter.MyPublicWealfareAdapter;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.MyPublicWewlListManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_systemaction)
public class MySystemActionActivity extends BaseActivity implements View.OnClickListener
       {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_public_nomessage)
    LinearLayout ll_public_nomessage;


    @Override
    protected void initViews() {
        tv_title.setText("系统消息");
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {




    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.ll_left:
                finish();
                break;
        }
    }


}
