package com.softtek.lai.module.bodygame3.more.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyTypeAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateFuce;
import com.softtek.lai.module.bodygame3.more.model.FuceDate;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_update_fuce_time)
public class UpdateFuceTimeActivity extends BaseActivity{

    private static final int IS_END=0;
    private static final int IS_NO_END=1;
    private static final int IS_EQ=2;

    public static final int REQUEST_CODE = 233;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;

    private List<FuceDate> dates=new ArrayList<>();
    private EasyTypeAdapter<FuceDate> adapter;

    private String classId;
    @Override
    protected void initViews() {
        tv_title.setText("修改复测日");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        classId=getIntent().getStringExtra("classId");
        adapter=new EasyTypeAdapter<FuceDate>(dates) {
            @Override
            public int getItemViewType(int position) {
                if (dates.isEmpty()){
                    return 0;
                }
                FuceDate date=dates.get(position);
                int compare=DateUtil.getInstance(DateUtil.yyyy_MM_dd).compare(date.getMeasureDate(),
                        DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate());
                return compare==-1?IS_END:compare==0?IS_EQ:IS_NO_END;
            }

            @Override
            public int getViewTypeCount() {
                return 3;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                int type=getItemViewType(position);
                FuceDate date=dates.get(position);
                DateUtil dateUtil=DateUtil.getInstance(DateUtil.yyyy_MM_dd);
                String fuce=dateUtil.convertDateStr(date.getMeasureDate(),"yyyy年MM月dd日");
                String week=dateUtil.getWeek(dateUtil.convert2Date(date.getMeasureDate()));
                ViewHolder holder;
                if(type==IS_END){
                    holder=ViewHolder.get(UpdateFuceTimeActivity.this,
                            R.layout.item_fuce_time_end,
                            convertView,parent);

                }else {
                    holder=ViewHolder.get(UpdateFuceTimeActivity.this,
                            R.layout.item_fuce_time,
                            convertView,parent);
                    TextView tv_lable=holder.getView(R.id.tv_lable);
                    ImageView iv_icon=holder.getView(R.id.iv_icon);
                    if(type==IS_EQ){
                        tv_lable.setVisibility(View.VISIBLE);
                        iv_icon.setVisibility(View.GONE);
                    }else {
                        tv_lable.setVisibility(View.GONE);
                        iv_icon.setVisibility(View.VISIBLE);
                        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showDateDialog(position);
                            }
                        });
                    }

                }
                TextView tv_serial=holder.getView(R.id.tv_serial);
                tv_serial.setText(date.getWeekNum()+"");
                TextView tv_date=holder.getView(R.id.tv_date);
                tv_date.setText(fuce);
                TextView tv_week=holder.getView(R.id.tv_week);
                tv_week.setText(week);

                return holder.getConvertView();
            }
        };
        lv.setAdapter(adapter);
        dialogShow();
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getMeasureDateList(classId,UserInfoModel.getInstance().getToken(),
                        classId,
                        new RequestCallback<ResponseData<List<FuceDate>>>() {
                            @Override
                            public void success(ResponseData<List<FuceDate>> data, Response response) {
                                dialogDissmiss();
                                try {
                                    if(data.getStatus()==200){
                                        dates.addAll(data.getData());
                                        adapter.notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
    }

    private void showDateDialog(final int position) {
        //从当次复测日的上一次复测日的下一天开始选择日期
        //如果position为0，则就今日的下一天开始
        Calendar c = Calendar.getInstance();
        if(position>0){
            String fuceDate=dates.get(position-1).getMeasureDate();
            String currentDate=DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();
            int compare=DateUtil.getInstance(DateUtil.yyyy_MM_dd).compare(fuceDate,currentDate);
            if(compare<1){//表示上一次的复测日其是小于或者等于今天的则取今日
                c.setTime(DateUtil.getInstance(DateUtil.yyyy_MM_dd).convert2Date(currentDate));
            }else {//上一次复测日其大于今天
                c.setTime(DateUtil.getInstance(DateUtil.yyyy_MM_dd).convert2Date(fuceDate));
            }
        }
        //天数加1
        c.add(Calendar.DAY_OF_YEAR,1);
        final DatePickerDialog dialog=
            new DatePickerDialog(this, null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(c.getTime().getTime());
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = dialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                final String date = year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
                //输出当前日期
                dialogShow("提交中");
                ZillaApi.NormalRestAdapter.create(MoreService.class)
                        .updateMeasureDate(classId,UserInfoModel.getInstance().getToken(),
                                classId,
                                dates.get(position).getWeekNum(),
                                date,
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        dialogDissmiss();
                                        try {
                                            if(responseData.getStatus()==200){
                                                for (int i=position;i<dates.size();i++){
                                                    FuceDate fuceDate=dates.get(i);
                                                    fuceDate.setMeasureDate(DateUtil.getInstance(DateUtil.yyyy_MM_dd)
                                                            .jumpDateByDay(date,(i-position)*7));
                                                }
                                                adapter.notifyDataSetChanged();
                                                EventBus.getDefault().post(new UpdateFuce(classId,dates));
                                            }else {
                                                Util.toastMsg(responseData.getMsg());
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        dialogDissmiss();
                                        super.failure(error);
                                    }
                                });

            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
