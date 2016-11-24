package com.softtek.lai.module.bodygame3.activity.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.ActtypeModel;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.more.view.EditorTextActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CustomDialog;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.InjectView;
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
    private LinearLayout.LayoutParams parm;
    private String date;
    EasyAdapter<ActtypeModel> adapter;
    private List<ActtypeModel> acttypeModels;
    @Override
    protected void initViews() {
        tv_title.setText("新建活动");
        tv_right.setText("确定");
        ll_left.setOnClickListener(this);
        rl_activity_mark.setOnClickListener(this);
        rl_activity_name.setOnClickListener(this);
        rl_activity_time.setOnClickListener(this);
        rl_activity_type.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

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
                Intent addClassNameIntent = new Intent(this, EditorTextActivity.class);
                addClassNameIntent.putExtra("flag", EditorTextActivity.ADD_ACTIVITY_NAME);
                addClassNameIntent.putExtra("name", tv_activity_name.getText());
                startActivityForResult(addClassNameIntent, 001);
                break;
            case R.id.rl_activity_mark:
                Intent addMarkIntent = new Intent(this, EditorTextActivity.class);
                addMarkIntent.putExtra("flag", EditorTextActivity.ADD_MARK);
                addMarkIntent.putExtra("name", tv_activity_mark.getText());
                startActivityForResult(addMarkIntent, 002);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 001) {
                String value = data.getStringExtra("value");
                tv_activity_name.setText(value);
            }else if(requestCode==002){
                String value_mark=data.getStringExtra("value");
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


        builder.setView(view);
        builder.show();
        gettype();
        adapter = new EasyAdapter<ActtypeModel>(this, acttypeModels, R.layout.gird_item) {
            @Override
            public void convert(ViewHolder holder, ActtypeModel data, int position) {
              TextView text=holder.getView(R.id.text);
                text.setText(data.getActivityTypeName());
                ImageView image=holder.getView(R.id.image);
                String path=AddressManager.getHost();
                Picasso.with(CreateActActivity.this).load(path+data.getActivityTypeIcon()).into(image);
            }

        };
        type_view.setAdapter(adapter);

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


//        final CustomDialog.Builder b = new CustomDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        final View view = inflater.inflate(R.layout.progress_dialog, null);
//
//        ImageView closeBtn = (ImageView) view.findViewById(R.id.close_btn);
//
//        ll_viewArea = (LinearLayout) view.findViewById(R.id.ll_viewArea);
//        parm = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
//        parm.gravity = Gravity.CENTER;
//
//        // 自定义布局控件，用来初始化并存放自定义imageView
//        viewArea = new ViewArea(this, bitmap);
//
//        ll_viewArea.addView(viewArea, parm);
//
//        b.setView(view);
//        b.show();
//
//
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                DeleteDialog(position, b);
//            }
//        });
//
//
//        ll_viewArea.setClickable(true);
//        ll_viewArea.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                b.dismiss();
//            }
//        });
    }

    private void gettype() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getacttype(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<ActtypeModel>>>() {
            @Override
            public void success(ResponseData<List<ActtypeModel>> listResponseData, Response response) {
                Util.toastMsg(listResponseData.getMsg());
                if(listResponseData.getData()!=null){
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
                tv_activity_time.setText(date + "" + i + ":" + i1);
            }
        }, dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true).show();
    }

}
