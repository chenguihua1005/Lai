package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActdetailModel;
import com.softtek.lai.module.bodygame3.activity.model.UseredModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
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

    @InjectView(R.id.scroll_view)
    ScrollView scroll_view;
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
    @InjectView(R.id.end_tv)
    Button end_tv;

    @InjectView(R.id.tv_delete)
    TextView tv_delete;

    private List<UseredModel> useredModels = new ArrayList<>();
    EasyAdapter<UseredModel> adapter;
    private String activityId;//活动I
    private int classrole;//班级角色
    public static final int ACTIVITY_DEL = 1;
    public static final int ACTIVITY_SIGN = 2;
    public static final int ACTIVITY_EXIT = 3;
    //    private boolean operation = false;
    private int operation = 0;

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
        activityId = getIntent().getStringExtra("activityId");
        classrole = getIntent().getExtras().getInt("classrole", -1);

        getalldetail();
        adapter = new EasyAdapter<UseredModel>(this, useredModels, R.layout.gird_item) {
            @Override
            public void convert(ViewHolder holder, UseredModel data, int position) {
                if (data != null) {
                    TextView activity_name = holder.getView(R.id.text);
                    if (!TextUtils.isEmpty(data.getUserName())) {
                        activity_name.setText(data.getUserName());
                    }
                    ImageView detail_head = holder.getView(R.id.head_image);
                    String path = AddressManager.get("photoHost");
                    Picasso.with(ActivitydetailActivity.this).load(path + data.getUserIcon())
                            .fit().placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(detail_head);
                }
            }
        };
        detail_view.setAdapter(adapter);
        detail_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UseredModel model = useredModels.get(i);
                Intent intent = new Intent(ActivitydetailActivity.this, PersonDetailActivity2.class);
                intent.putExtra("AccountId", model.getUserId());
                intent.putExtra("ClassId", model.getClassId());
                startActivity(intent);
            }
        });
    }

    private void getalldetail() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactdetail(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                activityId, new RequestCallback<ResponseData<ActdetailModel>>() {
                    @Override
                    public void success(ResponseData<ActdetailModel> actdetailModelResponseData, Response response) {
                        try {
                            if (200 == actdetailModelResponseData.getStatus()) {
                                scroll_view.setVisibility(View.VISIBLE);
                                tv_delete.setVisibility(View.GONE);
                                if (actdetailModelResponseData.getData() != null) {
                                    ActdetailModel actdetailModel = actdetailModelResponseData.getData();
                                    if (classrole == Constants.HEADCOACH) {
                                        if (actdetailModel.getSign()) {
                                            if (actdetailModel.getEnd()) {
                                                end_tv.setVisibility(View.VISIBLE);
                                                signup_activity.setVisibility(View.GONE);
                                                delete_activity.setVisibility(View.GONE);
                                                exit_lin.setVisibility(View.GONE);
                                            } else {
                                                end_tv.setVisibility(View.GONE);
                                                signup_activity.setVisibility(View.GONE);
                                                delete_activity.setVisibility(View.VISIBLE);
                                                exit_lin.setVisibility(View.VISIBLE);
                                            }

                                        } else {
                                            if (actdetailModel.getEnd()) {
                                                end_tv.setVisibility(View.VISIBLE);
                                                signup_activity.setVisibility(View.GONE);
                                                delete_activity.setVisibility(View.GONE);
                                                exit_lin.setVisibility(View.GONE);
                                            } else {
                                                end_tv.setVisibility(View.GONE);
                                                signup_activity.setVisibility(View.VISIBLE);
                                                delete_activity.setVisibility(View.VISIBLE);
                                                exit_lin.setVisibility(View.GONE);
                                            }
                                        }
                                    } else {
                                        if (actdetailModel.getSign()) {
                                            if (actdetailModel.getEnd()) {
                                                end_tv.setVisibility(View.VISIBLE);
                                                signup_activity.setVisibility(View.GONE);
                                                delete_activity.setVisibility(View.GONE);
                                                exit_lin.setVisibility(View.GONE);
                                            } else {
                                                end_tv.setVisibility(View.GONE);
                                                signup_activity.setVisibility(View.GONE);
                                                delete_activity.setVisibility(View.GONE);
                                                exit_lin.setVisibility(View.VISIBLE);
                                            }

                                        } else {
                                            if (actdetailModel.getEnd()) {
                                                end_tv.setVisibility(View.VISIBLE);
                                                signup_activity.setVisibility(View.GONE);
                                                delete_activity.setVisibility(View.GONE);
                                                exit_lin.setVisibility(View.GONE);
                                            } else {
                                                end_tv.setVisibility(View.GONE);
                                                signup_activity.setVisibility(View.VISIBLE);
                                                delete_activity.setVisibility(View.GONE);
                                                exit_lin.setVisibility(View.GONE);
                                            }

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
                                        count_sign.setText("(" + 0 + ")");
                                        detail_view.setVisibility(View.GONE);
                                        no_partner.setVisibility(View.VISIBLE);
                                    }

                                }
                            } else if (100 == actdetailModelResponseData.getStatus()) {
                                scroll_view.setVisibility(View.GONE);
                                tv_delete.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.signup_activity:
                ZillaApi.NormalRestAdapter.create(ActivityService.class).signup(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(), activityId, new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                try {
                                    if (200 == responseData.getStatus()) {
                                        Util.toastMsg(responseData.getMsg());
                                        operation = 1;
                                        getalldetail();
                                    } else {
                                        operation = 0;
                                        Util.toastMsg(responseData.getMsg());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                try {
                                    operation = 0;
                                    super.failure(error);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                break;
            case R.id.delete_activity:
                ZillaApi.NormalRestAdapter.create(ActivityService.class).deleteact(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(), activityId, new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                Util.toastMsg(responseData.getMsg());
                                Intent del = getIntent();
                                int counts = del.getExtras().getInt("counts");
                                int count = counts - 1;
                                del.putExtra("operation", ACTIVITY_DEL);
                                del.putExtra("count", count);
                                setResult(RESULT_OK, del);
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
                                try {
                                    if (200 == responseData.getStatus()) {
                                        Util.toastMsg(responseData.getMsg());
                                        operation = 2;
                                        getalldetail();
                                    } else {
                                        operation = 0;
                                        Util.toastMsg(responseData.getMsg());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                operation = 0;
                                super.failure(error);
                            }
                        });
                break;
            case R.id.ll_left:

                if (operation == 0) {
                    finish();
                } else if (operation == 1) {
                    com.github.snowdream.android.util.Log.i("报名活动。。。。");
                    Intent intent = getIntent();
                    intent.putExtra("operation", ACTIVITY_SIGN);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (operation == 2) {
                    com.github.snowdream.android.util.Log.i("退出活动。。。");
                    Intent intent = getIntent();
                    intent.putExtra("operation", ACTIVITY_EXIT);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (operation == 0) {
                finish();
            } else if (operation == 1) {
                com.github.snowdream.android.util.Log.i("报名活动。。。");
                Intent intent = getIntent();
                intent.putExtra("operation", ACTIVITY_SIGN);
                setResult(RESULT_OK, intent);
                finish();
            } else if (operation == 2) {
                com.github.snowdream.android.util.Log.i("退出活动。。。。。。。");
                Intent intent = getIntent();
                intent.putExtra("operation", ACTIVITY_EXIT);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
