package com.softtek.lai.module.bodygame3.activity.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActdetailModel;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.softtek.lai.module.bodygame3.activity.model.UseredModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_activitydetail)
public class ActivitydetailActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.detail_view)
    GridView detail_view;//已报名的小伙伴
    @InjectView(R.id.detail_activity_name)
    TextView detail_activity_name;
    @InjectView(R.id.detail_activity_time)
    TextView detail_activity_time;
    @InjectView(R.id.detail_activity_mark)
    TextView detail_activity_mark;
    @InjectView(R.id.count_sign)
    TextView count_sign;//人数
    @InjectView(R.id.sign_lin)
    LinearLayout sign_lin;//报名活动/删除活动
    @InjectView(R.id.exit_lin)
    LinearLayout exit_lin;//退出
    @InjectView(R.id.exit_tv)
    TextView exit_tv;//退出活动
    @InjectView(R.id.delete_activity)
    Button delete_activity;
    @InjectView(R.id.signup_activity)
    Button signup_activity;
    @InjectView(R.id.no_partner)
    TextView no_partner;
    private List<UseredModel> useredModels = new ArrayList<UseredModel>();
    EasyAdapter<UseredModel> adapter;
    private String activityId;//活动I
    private int classrole;//班级角色

    @Override
    protected void initViews() {
        tv_title.setText("活动详情");
        signup_activity.setOnClickListener(this);
        delete_activity.setOnClickListener(this);
        exit_tv.setOnClickListener(this);
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        if (UserInfoModel.getInstance().getUser().getUserrole().equals(Constants.SP)) {
            sign_lin.setVisibility(View.VISIBLE);
            delete_activity.setVisibility(View.VISIBLE);
        } else {
            sign_lin.setVisibility(View.VISIBLE);
            delete_activity.setVisibility(View.GONE);
        }
        activityId = getIntent().getStringExtra("activityId");
        classrole = getIntent().getExtras().getInt("classrole");
        Log.e("classrole", classrole + "");
        getalldetail();
        adapter = new EasyAdapter<UseredModel>(this, useredModels, R.layout.gird_item) {
            @Override
            public void convert(ViewHolder holder, UseredModel data, int position) {
                TextView activity_name = holder.getView(R.id.text);
                activity_name.setText(data.getUserName());
                ImageView detail_head = holder.getView(R.id.head_image);
                String path = AddressManager.get("photoHost");
                    Picasso.with(ActivitydetailActivity.this).load(path + data.getUserIcon())
                            .fit().placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(detail_head);
            }
        };
        detail_view.setAdapter(adapter);
    }

    private void getalldetail() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactdetail(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                activityId, new RequestCallback<ResponseData<ActdetailModel>>() {
                    @Override
                    public void success(ResponseData<ActdetailModel> actdetailModelResponseData, Response response) {
                        if (actdetailModelResponseData.getData() != null) {
                            ActdetailModel actdetailModel = actdetailModelResponseData.getData();
                            if (classrole == Constants.HEADCOACH) {
                                if (actdetailModel.getSign()) {
                                    signup_activity.setVisibility(View.GONE);
                                    delete_activity.setVisibility(View.VISIBLE);
                                    exit_lin.setVisibility(View.GONE);
                                } else {
                                    signup_activity.setVisibility(View.VISIBLE);
                                    delete_activity.setVisibility(View.VISIBLE);
                                    exit_lin.setVisibility(View.GONE);
                                }
                            } else if (classrole == Constants.COACH) {
                                if (actdetailModel.getSign()) {
                                    signup_activity.setVisibility(View.GONE);
                                    delete_activity.setVisibility(View.GONE);
                                    exit_lin.setVisibility(View.VISIBLE);
                                } else {
                                    signup_activity.setVisibility(View.VISIBLE);
                                    delete_activity.setVisibility(View.GONE);
                                    exit_lin.setVisibility(View.GONE);
                                }
                            } else if (classrole == Constants.ASSISTANT) {
                                if (actdetailModel.getSign()) {
                                    signup_activity.setVisibility(View.GONE);
                                    delete_activity.setVisibility(View.GONE);
                                    exit_lin.setVisibility(View.VISIBLE);
                                } else {
                                    signup_activity.setVisibility(View.VISIBLE);
                                    delete_activity.setVisibility(View.GONE);
                                    exit_lin.setVisibility(View.GONE);
                                }
                            } else if (classrole == Constants.STUDENT) {
                                if (actdetailModel.getSign()) {
                                    signup_activity.setVisibility(View.GONE);
                                    delete_activity.setVisibility(View.GONE);
                                    exit_lin.setVisibility(View.VISIBLE);
                                } else {
                                    signup_activity.setVisibility(View.VISIBLE);
                                    delete_activity.setVisibility(View.GONE);
                                    exit_lin.setVisibility(View.GONE);
                                }
                            }

                            detail_activity_name.setText(actdetailModel.getTitle());
                            detail_activity_time.setText(actdetailModel.getStartTime());
                            detail_activity_mark.setText(actdetailModel.getContent());
                            useredModels.clear();
                            if (actdetailModel.getUsers() != null && !actdetailModel.getUsers().isEmpty()) {
                                detail_view.setVisibility(View.VISIBLE);
                                no_partner.setVisibility(View.GONE);
                                useredModels.addAll(actdetailModel.getUsers());
                                count_sign.setText("(" + useredModels.size() + ")");
                                adapter.notifyDataSetChanged();
                            } else {
                                detail_view.setVisibility(View.GONE);
                                no_partner.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_activity:
                ZillaApi.NormalRestAdapter.create(ActivityService.class).signup(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), activityId, new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        if (200 == responseData.getStatus()) {
                            Util.toastMsg(responseData.getMsg());
                            if (classrole == Constants.HEADCOACH) {
                                signup_activity.setVisibility(View.GONE);
                                delete_activity.setVisibility(View.VISIBLE);
                                exit_lin.setVisibility(View.GONE);
                            } else if (classrole == Constants.COACH) {
                                signup_activity.setVisibility(View.GONE);
                                delete_activity.setVisibility(View.GONE);
                                exit_lin.setVisibility(View.VISIBLE);
                            } else if (classrole == Constants.ASSISTANT) {
                                signup_activity.setVisibility(View.GONE);
                                delete_activity.setVisibility(View.GONE);
                                exit_lin.setVisibility(View.VISIBLE);
                            } else if (classrole == Constants.STUDENT) {
                                signup_activity.setVisibility(View.GONE);
                                delete_activity.setVisibility(View.GONE);
                                exit_lin.setVisibility(View.VISIBLE);
                            }

                            ;
                            getalldetail();
                        } else {
                            Util.toastMsg(responseData.getMsg());
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
                break;
            case R.id.delete_activity:
                ZillaApi.NormalRestAdapter.create(ActivityService.class).deleteact(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), activityId, new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        Util.toastMsg(responseData.getMsg());
                        setResult(RESULT_OK,new Intent().putExtra("activityid",activityId));

                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
                break;
            case R.id.exit_tv:
                ZillaApi.NormalRestAdapter.create(ActivityService.class).exitact(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        activityId, new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                Util.toastMsg(responseData.getMsg());
                                setResult(RESULT_OK,new Intent().putExtra("activityid",activityId));
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                            }
                        });
                break;
            case R.id.ll_left:
                setResult(RESULT_OK,new Intent().putExtra("activityid",activityId));
                finish();
                break;
        }
    }


}
