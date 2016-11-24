package com.softtek.lai.module.bodygame3.home.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActCalendarModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.activity.view.CreateActActivity;
import com.softtek.lai.module.bodygame3.activity.view.WriteFCActivity;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDatePageChangeListener;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.EventDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.OneDayDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.SchedulEventDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.SchelDecorator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.graphics.Color.parseColor;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment implements OnDateSelectedListener, OnDatePageChangeListener, View.OnClickListener {

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;
    @InjectView(R.id.material_calendar)
    MaterialCalendarView material_calendar;
    @InjectView(R.id.ll_chuDate)
    LinearLayout ll_chuDate;//初始数据录入、审核
    private CalendarMode mode = CalendarMode.WEEKS;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private List<ActCalendarModel> calendarModels = new ArrayList<ActCalendarModel>();
    private int datetype;
    private String classid;
    private String classId_first;
    private List<ClassModel> classModels = new ArrayList<ClassModel>();
    private String dateStr;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        ll_chuDate.setOnClickListener(this);
        final List<String> data = new ArrayList<>();
        data.add("超级减重11月班");
        data.add("跑步12月班");
        data.add("测试数据3");
        data.add("测试数据4");
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), data, R.layout.class_title) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_title);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                return data.get(position);
            }
        });

        tv_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
//                    getChildFragmentManager().beginTransaction().replace(R.id.container,new StudentFragment()).commit();
                    //选择一个班级，刷新一次页面。
                } else if (i == 1) {
//                    getChildFragmentManager().beginTransaction().replace(R.id.container,new HeadCoachFragment()).commit();
                } else if (i == 2) {
//                    getChildFragmentManager().beginTransaction().replace(R.id.container,new CoachFragment()).commit();
                } else if (i == 3) {
//                    getChildFragmentManager().beginTransaction().replace(R.id.container,new AssistantFragment()).commit();
                }


            }
        });
        iv_right.setImageResource(R.drawable.bg2_add);
        fl_right.setOnClickListener(this);
        material_calendar.setOnDateChangedListener(this);
        material_calendar.setDatepageChangeListener(this);
        material_calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        material_calendar.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR) - 1, Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) + 1, Calendar.DECEMBER, 31);

//        mySelectorDecorator = new MySelectorDecorator(getActivity());

        material_calendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)//周模式(WEEKS)或月模式（MONTHS）
                .commit();

//        new ApiSimulatorToday().executeOnExecutor(Executors.newSingleThreadExecutor());//标注今天
//设置日历的长和宽间距
        material_calendar.setTileWidthDp(50);
        material_calendar.setTileHeightDp(38);

        //设置选中的背景颜色
        int color = parseColor("#FFA200");
        material_calendar.setSelectionColor(color);
        material_calendar.addDecorators(
                oneDayDecorator//选中字体变大 白色显示
        );
    }

    @Override
    protected void initDatas() {
        //获取初始数据
        getalldatafirst();

    }


    private void getalldatafirst() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUser().getUserid(), "C4E8E179-FD99-4955-8BF9-CF470898788B", new RequestCallback<ResponseData<ActivitydataModel>>() {
            @Override
            public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
                Util.toastMsg(activitydataModelResponseData.getMsg() + "++++++++++++++++");
                System.out.println("++++++++++++++++");
                if (activitydataModelResponseData.getData() != null) {
                    ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                    if (activitydataModel.getList_ActCalendar() != null) {
                        calendarModels.addAll(activitydataModel.getList_ActCalendar());
                        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                    }


                    //加载班级
                    if (activitydataModel.getList_Class() != null) {
                        classModels.addAll(activitydataModel.getList_Class());
                        tv_title.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.class_title) {
                            @Override
                            public void convert(ViewHolder holder, ClassModel data, int position) {
                                TextView tv_class_name = holder.getView(R.id.tv_title);
                                tv_class_name.setText(data.getClassName());
                            }

                            @Override
                            public String getText(int position) {
                                classId_first = classModels.get(position).getClassId();

                                return classModels.get(position).getClassName();
                            }
                        });


                    }
                }

            }
        });

//        tv_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                classid = classModels.get(i).getClassId();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());//字体加粗加大
        widget.invalidateDecorators();

        int YY = date.getCalendar().get(Calendar.YEAR);
        int MM = date.getCalendar().get(Calendar.MONTH) + 1;
        int DD = date.getCalendar().get(Calendar.DATE);

        dateStr = YY + "-" + MM + "-" + DD;
        gettodaydata();


    }

    private void gettodaydata() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).gettoday(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUser().getUserid(), "", dateStr, new RequestCallback<ResponseData<TodaysModel>>() {
            @Override
            public void success(ResponseData<TodaysModel> todaysModelResponseData, Response response) {
            Util.toastMsg(todaysModelResponseData.getMsg());
            }
        });
    }

    @Override
    public void onDatePageSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                Intent intent = new Intent(getContext(), CreateActActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_chuDate:
//                Intent intent1 = new Intent(getContext(), CreateActivity.class);
//                startActivity(intent1);
                startActivity(new Intent(getContext(), WriteFCActivity.class));
                break;
        }
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            for (int i = 0; i < calendarModels.size(); i++) {
                datetype = calendarModels.get(i).getDateType();
                String dateString = calendarModels.get(i).getMonthDate();
                try {
                    Date date = df.parse(dateString);
                    calendar.setTime(date);
                    CalendarDay day = CalendarDay.from(calendar);
                    dates.add(day);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            if (material_calendar != null) {
                material_calendar.addDecorator(new SchelDecorator(Constants.ACTIVITY, calendarDays, getActivity()));
            }
        }
    }

}
