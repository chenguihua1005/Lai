package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;
import com.softtek.lai.module.bodygame3.more.model.ServiceTeam;
import com.softtek.lai.module.bodygame3.more.net.StudentService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
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
    ListView lv_service_team;

    List<ServiceTeam.ServiceModel> serviceModelList = new ArrayList<>();
    List<ServiceTeam.Waiter> waiterList = new ArrayList<>();
    private EasyAdapter serviceTeamAdapter;
    private int px;
    private String classId;


    @Override
    protected void initViews() {
        //计算每一个成员item的宽高，设置给每一个成员item和viewpager
        int i1 = DisplayUtil.dip2px(this, 35L); //35是箭头的宽度
        px = (getResources().getDisplayMetrics().widthPixels - i1 * 2) / 4; //除以4是显示4个，计算一个的宽度

//        makeData();
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        classId = intent.getStringExtra("classId");

        tv_title.setText("服务团队");

        StudentService service = ZillaApi.NormalRestAdapter.create(StudentService.class);
        String token = UserInfoModel.getInstance().getToken();
        service.getServiceTeam(classId,token, classId, new Callback<ResponseData<ServiceTeam>>() {
            @Override
            public void success(ResponseData<ServiceTeam> serviceTeamResponseData, Response response) {
                //暂时没判断异常的情况
                try {
                    int status = serviceTeamResponseData.getStatus();
                    switch (status) {
                        case 200:
                            final ServiceTeam serviceTeam = serviceTeamResponseData.getData();
                            Log.e(TAG, "获取数据 = " + serviceTeamResponseData.getData().toString());
                            serviceModelList.clear();
                            serviceModelList.addAll(serviceTeam.getServices());
                            if (serviceTeamAdapter == null) {
                                //listview  null？
                                View headView = View.inflate(SupportTeamActivity.this, R.layout.head_support_team, null);
                                lv_service_team.addHeaderView(headView);
                                newAdapter();
                                lv_service_team.setAdapter(serviceTeamAdapter);
                            } else {
                                serviceTeamAdapter.notifyDataSetChanged();
                            }
                            //顶部的头像和名字
                            setImage(civ_head_image, serviceTeam.getCoachImg());
                            tv_head_coach_name.setText(serviceTeam.getCoachName());
                            civ_head_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(SupportTeamActivity.this, PersonDetailActivity2.class);
                                    intent.putExtra("ClassId", classId);
                                    intent.putExtra("AccountId", serviceTeam.getCoachId());
                                    startActivity(intent);
                                }
                            });
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });

    }

    private void newAdapter() {
        serviceTeamAdapter = new EasyAdapter<ServiceTeam.ServiceModel>(this, serviceModelList, R.layout.item_service_team) {

            @Override
            public void convert(ViewHolder holder, ServiceTeam.ServiceModel serviceModel, int position) {
                //组的别名
                TextView tv_group_name = holder.getView(R.id.tv_group_name);
                tv_group_name.setText(serviceModel.getCGName());
                //序号
                TextView tv_group_number = holder.getView(R.id.tv_group_number);
                tv_group_number.setText(String.valueOf(++position));


                //设置学员adapter
//                waiterList.clear();
//                waiterList.addAll(serviceModel.getWaiters());

                viewPager(holder, serviceModel.getWaiters());

            }
        };
    }

    private void viewPager(ViewHolder holder, final List<ServiceTeam.Waiter> waiterList) {

        final ViewPager vp_test = holder.getView(R.id.vp_support_team);
        final TextView tip_tv = holder.getView(R.id.tip_tv);

        if (waiterList != null && waiterList.size() > 0) {
            vp_test.setVisibility(View.VISIBLE);
            tip_tv.setVisibility(View.GONE);


            vp_test.setAdapter(new SupportTeamVPAdapter(waiterList));
            //设置viewpager可以显示的item的数量和viewpager的大小
            vp_test.setOffscreenPageLimit(4);
//        vp_test.setPageMargin(10);
            ViewGroup.LayoutParams layoutParams = vp_test.getLayoutParams();
            layoutParams.width = px;
            layoutParams.height = px;
            vp_test.setLayoutParams(layoutParams);
            //监听事件
            RelativeLayout rl_test = holder.getView(R.id.rl_support_team);
            rl_test.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return vp_test.dispatchTouchEvent(event);
                }
            });
            ImageView btn_p = holder.getView(R.id.btn_previous);
            btn_p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = vp_test.getCurrentItem();/*.getCurrentPosition();*/
                    if (currentPosition != 0) {
                        vp_test.setCurrentItem(currentPosition - 4);
                    }
                }
            });
            ImageView btn_n = holder.getView(R.id.btn_next);
            btn_n.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = vp_test.getCurrentItem();
                    //currentPosition是索引，所以用waiterList.size()-1
                    if (currentPosition != waiterList.size() - 1) {
                        vp_test.setCurrentItem(currentPosition + 4);
                    }
                }
            });
        } else {
//            vp_test.setEnabled(false);
//            //监听事件
//            RelativeLayout rl_test = holder.getView(R.id.rl_support_team);
//            rl_test.setEnabled(false);
            vp_test.setVisibility(View.GONE);
            tip_tv.setVisibility(View.VISIBLE);
        }


    }


    private class SupportTeamVPAdapter extends PagerAdapter {

        List<ServiceTeam.Waiter> waiterList;

        private SupportTeamVPAdapter(List<ServiceTeam.Waiter> waiterList) {
            this.waiterList = waiterList;
        }

        @Override
        public int getCount() {
            return waiterList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            final ServiceTeam.Waiter waiter = waiterList.get(position);
            View view = View.inflate(SupportTeamActivity.this, R.layout.item_service_member, null);
            //成员头像
            CircleImageView civ_member = (CircleImageView) view.findViewById(R.id.civ_member);
            setImage(civ_member, waiter.getWaiterImg());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long waiterAccount = waiter.getWaiterAccount();
                    Intent intent = new Intent(SupportTeamActivity.this, PersonDetailActivity2.class);
                    intent.putExtra("ClassId", classId);
                    intent.putExtra("AccountId", waiterAccount);
                    startActivity(intent);
                }
            });
            //成员名
            TextView tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
            tv_member_name.setText(waiter.getWaiterName());
            //角色图片
            ImageView iv_identity = (ImageView) view.findViewById(R.id.iv_identity);
            String classRole = waiter.getClassRole();
            if (classRole != null) {
                switch (classRole) {
                    case "教练":
                        iv_identity.setImageResource(R.drawable.coach);
                        break;
                    case "助教":
                        iv_identity.setImageResource(R.drawable.assistant);
                        break;
                }
            }
            //设置成员item的宽高
            ViewGroup.LayoutParams params = new ViewPager.LayoutParams();
            params.width = px;
            params.height = px;
            view.setLayoutParams(params);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    private void makeData() {

        for (int i = 0; i < 10; i++) {
            ServiceTeam serviceTeam = new ServiceTeam();
            ServiceTeam.Waiter waiter = serviceTeam.new Waiter();
            waiter.setWaiterName("名字" + i);
            waiterList.add(waiter);
        }
    }

    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
            Picasso.with(SupportTeamActivity.this).load(basePath + endUrl).placeholder(R.drawable.img_default).into(civ);
        } else {
            Picasso.with(SupportTeamActivity.this).load(R.drawable.img_default).into(civ);
        }

    }

    @OnClick({R.id.ll_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
