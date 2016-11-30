package com.softtek.lai.module.bodygame3.activity.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.RetestTabAdapter;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by Terry on 2016/11/29.
 */
@InjectLayout(R.layout.activity_honorranking)
public class InitAuditListActivity extends BaseActivity{

    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    List<Fragment> fragments;
    FuceSevice fuceSevice;
    String[] tabtitle={"未审核","已审核"};
    @Override
    protected void initViews() {
        tv_title.setText("初始数据审核");
        fragments=new ArrayList<>();
        fragments.add(InitAuditFragment.getInstance());
        fragments.add(InitAuditedFragment.getInstance());
        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(),fragments,tabtitle));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        fuceSevice= ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        doGetData(Long.parseLong("5"), "C4E8E179-FD99-4955-8BF9-CF470898788B", 1, 2);

    }
    //获取审核列表数据
    private void doGetData(Long accountid, String classid,  int pageIndex, int pageSize) {
        fuceSevice.dogetInitAuditList(UserInfoModel.getInstance().getToken(), accountid, classid, pageIndex, pageSize, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        tabtitle[0]="未审核("+listResponseData.getData().get(0).getCount()+")";
                        tabtitle[1]="已审核("+listResponseData.getData().get(1).getCount()+")";
                        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(),fragments,tabtitle));
                        tab.setupWithViewPager(content);
                        Log.i("已审核("+tabtitle[1]+"count"+listResponseData.getData().get(1).getCount());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
