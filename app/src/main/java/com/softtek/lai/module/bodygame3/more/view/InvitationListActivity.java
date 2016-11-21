package com.softtek.lai.module.bodygame3.more.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.more.adapter.MyExpandableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * 邀请列表界面
 */
@InjectLayout(R.layout.activity_invitation_list)
public class InvitationListActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.lv)
    ExpandableListView lv;
    MyExpandableAdapter adapter;
    Map<String,List<String>> datas=new HashMap<>();
    private String[] parentList = new String[]{"first", "second", "third"};
    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.invitation_add));
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        for (int i=0;i<parentList.length;i++){
            List<String> data=new ArrayList<>();
            for (int j=0;j<4;j++){
                data.add(j+"child"+i+"item");
            }
            datas.put(parentList[i],data);
        }
        adapter=new MyExpandableAdapter(this,datas,parentList);
        lv.setAdapter(adapter);
        for (int i = 0; i < parentList.length; i++) {
            lv.expandGroup(i);
        }
        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:{
                finish();
            }
                break;
            case R.id.fl_right:{
                //跳转邀请小伙伴
            }
                break;
        }
    }
}
