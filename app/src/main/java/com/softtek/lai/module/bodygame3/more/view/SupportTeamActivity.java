package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ServiceTeam;
import com.softtek.lai.module.bodygame3.more.net.StudentService;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.meetmehorizontallistview.HorizontalListView;
import com.softtek.lai.widgets.meetmehorizontallistview.MyListview;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by Curry on 1/6/2017.
 */


@InjectLayout(R.layout.activity_support_team)
public class SupportTeamActivity extends BaseActivity {

    private static final String TAG = "SupportTeamActivity";
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.civ_head_image)
    CircleImageView civ_head_image;
    @InjectView(R.id.tv_identity)
    TextView tv_identity;
    @InjectView(R.id.tv_head_coach_name)
    TextView tv_head_coach_name;
    @InjectView(R.id.lv_service_team)
    MyListview lv_service_team;

    List<ServiceTeam.ServiceModel> serviceModelList = new ArrayList<>();
    List<ServiceTeam.Waiter> waiterList = new ArrayList<>();
    private EasyAdapter serviceTeamAdapter;
    private EasyAdapter waiterAdapter;

    boolean flag=true;

    @Override
    protected void initViews() {
        serviceTeamAdapter = new EasyAdapter<ServiceTeam.ServiceModel>(this, serviceModelList, R.layout.item_service_team) {

            @Override
            public void convert(ViewHolder holder, ServiceTeam.ServiceModel serviceModel, int position) {
                //组的别名
                TextView tv_group_name = holder.getView(R.id.tv_group_name);
                tv_group_name.setText(serviceModel.getCGName());
                //
                HorizontalListView hlv_service_member = holder.getView(R.id.hlv_service_member);
                //两个adapter可以判断为null的时候再创建，先这样
                waiterList.clear();
//                waiterList.addAll(serviceModel.getWaiters());
                makeData();
                hlv_service_member.setAdapter(waiterAdapter);
            }
        };
        lv_service_team.setAdapter(serviceTeamAdapter);

        waiterAdapter = new EasyAdapter<ServiceTeam.Waiter>(this, waiterList, R.layout.item_service_member) {

            @Override
            public void convert(ViewHolder holder, ServiceTeam.Waiter waiterModel, int position) {
                //成员头像
                CircleImageView civ_member = holder.getView(R.id.civ_member);
                setImage(civ_member, waiterModel.getWaiterImg());
                //成员名
                TextView tv_member_name = holder.getView(R.id.tv_member_name);
                tv_member_name.setText(waiterModel.getWaiterName());

            }
        };

    }

    private void makeData() {

        for (int i = 0; i < 10; i++) {
            ServiceTeam serviceTeam = new ServiceTeam();
            ServiceTeam.Waiter waiter = serviceTeam.new Waiter();
            waiter.setWaiterName("名字" + i);
            waiterList.add(waiter);
        }
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        String classId = intent.getStringExtra("classId");

        tv_title.setText("服务团队");

        StudentService service = ZillaApi.NormalRestAdapter.create(StudentService.class);
        String token = UserInfoModel.getInstance().getToken();
        service.getServiceTeam(token, classId, new Callback<ResponseData<ServiceTeam>>() {
            @Override
            public void success(ResponseData<ServiceTeam> serviceTeamResponseData, Response response) {
                //暂时没判断异常的情况
                ServiceTeam serviceTeam = serviceTeamResponseData.getData();
                Log.e(TAG, "获取数据 = " + serviceTeamResponseData.getData().toString());


                serviceModelList.clear();
                serviceModelList.addAll(serviceTeam.getServices());
                serviceTeamAdapter.notifyDataSetChanged();
                setImage(civ_head_image, serviceTeam.getCoachImg());
                tv_head_coach_name.setText(serviceTeam.getCoachName());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
            Picasso.with(SupportTeamActivity.this).load(basePath + endUrl).into(civ);
        }
    }


    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }


}
