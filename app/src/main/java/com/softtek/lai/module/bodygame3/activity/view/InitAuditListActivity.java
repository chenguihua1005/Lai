package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    String classId;
    int Auditnum=0;
    int Auditednum=0;
    @Override
    protected void initViews() {
        tv_title.setText("初始数据审核");
        classId=getIntent().getStringExtra("classId");
        fragments=new ArrayList<>();
        fragments.add(InitAuditFragment.getInstance(classId));
        fragments.add(InitAuditedFragment.getInstance(classId));
        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(),fragments,tabtitle));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("Auditnum",Auditnum);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        fuceSevice= ZillaApi.NormalRestAdapter.create(FuceSevice.class);

        doGetData(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()), classId, 1, 100);

    }
    //获取审核列表数据
    private void doGetData(Long accountid, String classid,  int pageIndex, int pageSize) {
        fuceSevice.dogetInitAuditList(UserInfoModel.getInstance().getToken(), accountid, classid, pageIndex, pageSize, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                try {
                    switch (status)
                    {
                        case 200:
                            if(listResponseData.getData().size()==0)
                            {
                                tabtitle[0] = "未审核(" + "0"+ ")";
                                tabtitle[1] = "已审核(" + "0" + ")";
                                TabLayout.Tab tab1=tab.getTabAt(0);
                                tab1.setText(tabtitle[0]);
                                TabLayout.Tab tab2=tab.getTabAt(1);
                                tab2.setText(tabtitle[1]);
                            } else {
                                Auditnum=Integer.parseInt(listResponseData.getData().get(0).getCount());
                                Auditednum=Integer.parseInt(listResponseData.getData().get(1).getCount());
                                tabtitle[0] = "未审核(" + Auditnum + ")";
                                tabtitle[1] = "已审核(" + Auditednum + ")";
                                TabLayout.Tab tab1=tab.getTabAt(0);
                                tab1.setText(tabtitle[0]);
                                TabLayout.Tab tab2=tab.getTabAt(1);
                                tab2.setText(tabtitle[1]);
                            }
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public  void update(){
        tabtitle[0] = "未审核(" + (--Auditnum )+ ")";
        tabtitle[1] = "已审核(" + (++Auditednum) + ")";
        TabLayout.Tab tab1=tab.getTabAt(0);
        tab1.setText(tabtitle[0]);
        TabLayout.Tab tab2=tab.getTabAt(1);
        tab2.setText(tabtitle[1]);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent=new Intent();
            intent.putExtra("Auditnum",Auditnum);
            setResult(RESULT_OK,intent);
//            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}