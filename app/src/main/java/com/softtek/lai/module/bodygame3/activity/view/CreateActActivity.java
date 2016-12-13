package com.softtek.lai.module.bodygame3.activity.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivityModel;
import com.softtek.lai.module.bodygame3.activity.model.ActtypeModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomDialog;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

//创建活动
@InjectLayout(R.layout.activity_create)
public class CreateActActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.rl_activity_time)
    RelativeLayout rl_activity_time;

    @InjectView(R.id.tv_activity_time)
    TextView tv_activity_time;
    @InjectView(R.id.rl_activity_type)
    RelativeLayout rl_activity_type;

    @InjectView(R.id.tv_activity_name)
    TextView tv_activity_name;
    @InjectView(R.id.rl_activity_name)
    RelativeLayout rl_activity_name;

    @InjectView(R.id.rl_activity_mark)
    RelativeLayout rl_activity_mark;

    @InjectView(R.id.tv_activity_mark)
    TextView tv_activity_mark;
    @InjectView(R.id.tv_activity_type)
    TextView tv_activity_type;
    @InjectView(R.id.type_iv)
    ImageView type_iv;
    private LinearLayout.LayoutParams parm;
    private String date;
    EasyAdapter<ActtypeModel> adapter;
    private List<ActtypeModel> acttypeModels = new ArrayList<ActtypeModel>();
    private String classid;
    private ActivityModel activityModel;
    private int classActivityId;//活动类型Id

    @Override
    protected void initViews() {
        tv_title.setText("新建活动");
        tv_right.setText("确定");
        activityModel = new ActivityModel();
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_activity_mark.setOnClickListener(this);
        rl_activity_name.setOnClickListener(this);
        rl_activity_time.setOnClickListener(this);
        rl_activity_type.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        classid = intent.getStringExtra("classid");
        gettype();
        if (acttypeModels != null) {
            adapter = new EasyAdapter<ActtypeModel>(this, acttypeModels, R.layout.gird_item) {
                @Override
                public void convert(ViewHolder holder, ActtypeModel data, int position) {
                    TextView text = holder.getView(R.id.text);
                    text.setText(data.getActivityTypeName());
                    CircleImageView image = holder.getView(R.id.head_image);
                    String path = AddressManager.get("photoHost");
                    if (StringUtils.isNotEmpty(data.getActivityTypeIcon())) {
                        Log.e("address", path + data.getActivityTypeIcon());
                        Picasso.with(CreateActActivity.this).load(path + data.getActivityTypeIcon()).fit().error(R.drawable.default_icon_square)
                                .placeholder(R.drawable.default_icon_square).into(image);
                    }

                }

            };
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_time:
                showDateDialog();
                break;
            case R.id.rl_activity_type:
                showTypeDialog();
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_activity_name:
                Intent addClassNameIntent = new Intent(this, ActTextActivity.class);
                addClassNameIntent.putExtra("value", ActTextActivity.ADD_ACTIVITY_NAME);
                addClassNameIntent.putExtra("name_value", tv_activity_name.getText());
                startActivityForResult(addClassNameIntent, 001);
                break;
            case R.id.rl_activity_mark:
                Intent addMarkIntent = new Intent(this, ActTextActivity.class);
                addMarkIntent.putExtra("value", ActTextActivity.ADD_MARK);
                addMarkIntent.putExtra("name_value", tv_activity_mark.getText());
                startActivityForResult(addMarkIntent, 002);
                break;
            case R.id.fl_right:
                String title = tv_activity_name.getText().toString().trim();
                String startime = tv_activity_time.getText().toString().trim();
                String mark = tv_activity_mark.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    Util.toastMsg("请输入活动标题");
                    return;
                }
                if (TextUtils.isEmpty(startime)) {
                    Util.toastMsg("请选择活动日期");
                    return;
                }
                if (TextUtils.isEmpty(mark)) {
                    Util.toastMsg("请输入活动说明");
                    return;
                }

                activityModel.setAccountId(UserInfoModel.getInstance().getUserId());
                activityModel.setClassId(classid);
                activityModel.setClassActivityId(classActivityId);
                activityModel.setTitle(title);
                activityModel.setWholeDay(false);
                activityModel.setStartTime(startime);
                activityModel.setContent(mark);
                ZillaApi.NormalRestAdapter.create(ActivityService.class).commitact(UserInfoModel.getInstance().getToken(),
                        activityModel, new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                if (200 == responseData.getStatus()) {
                                    Util.toastMsg(responseData.getMsg());
                                    setResult(RESULT_OK);
                                    finish();
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 001) {
                String value = data.getStringExtra("value_name");
                tv_activity_name.setText(value);
            } else if (requestCode == 002) {
                String value_mark = data.getStringExtra("value_name");
                tv_activity_mark.setText(value_mark);
            }
        }
    }

    private void showTypeDialog() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.list_activitytype, null);
        ImageView iv_cancle = (ImageView) view.findViewById(R.id.iv_cancle);
        GridView type_view = (GridView) view.findViewById(R.id.type_view);
        LinearLayout ll_area = (LinearLayout) view.findViewById(R.id.ll_area);
        parm = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        parm.gravity = Gravity.CENTER;

        // 自定义布局控件，用来初始化并存放自定义imageView
        type_view.setAdapter(adapter);

        builder.setView(view);
        builder.show();

        type_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActtypeModel acttypeModel = acttypeModels.get(i);
//                if (acttypeModel.getActivityTypeId() == 2) {
                classActivityId = acttypeModel.getActivityTypeId();
                tv_activity_type.setText(acttypeModel.getActivityTypeName());
                String path = AddressManager.get("photoHost");
                Picasso.with(CreateActActivity.this).load(path + acttypeModel.getActivityTypeIcon()).fit().error(R.drawable.default_icon_square)
                        .placeholder(R.drawable.default_icon_square).into(type_iv);
                builder.dismiss();
//                }
            }
        });
        iv_cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });


        ll_area.setClickable(true);
        ll_area.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
//


    }

    private void gettype() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getacttype(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<ActtypeModel>>>() {
            @Override
            public void success(ResponseData<List<ActtypeModel>> listResponseData, Response response) {
                if (listResponseData.getData() != null && !listResponseData.getData().isEmpty()) {
                    acttypeModels.addAll(listResponseData.getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                date = year + "年" + (month < 10 ? ("0" + month) : month) + "月" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth) + "日";
                showTimeDialog();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void showTimeDialog() {
        final Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                dateAndTime.set(Calendar.MINUTE, minute);
                dateAndTime.set(Calendar.HOUR_OF_DAY, i);//时
                dateAndTime.set(Calendar.MINUTE, i1);//分
//                String.format("%d:%d",hourOfDay,minute)
                Log.e("time", date + "" + String.format("%d:%d", i, i1));
                tv_activity_time.setText(date + "" + String.format("%d:%d", i, i1));
            }
        }, dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true).show();
    }

}
