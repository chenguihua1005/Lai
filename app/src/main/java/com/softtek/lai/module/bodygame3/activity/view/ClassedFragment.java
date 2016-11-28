package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActCalendarModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.view.HonorActivity;

import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDatePageChangeListener;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.OneDayDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.SchelDecorator;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.graphics.Color.parseColor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@linkNoClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@linkNoClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InjectLayout(R.layout.fragment_classed)
public class ClassedFragment extends LazyBaseFragment implements OnDateSelectedListener, OnDatePageChangeListener, View.OnClickListener {

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;
    @InjectView(R.id.list_activity)
    ListView list_activity;
    @InjectView(R.id.material_calendar)
    MaterialCalendarView material_calendar;
    @InjectView(R.id.ll_chuDate)
    LinearLayout ll_chuDate;//初始数据录入、审核
    @InjectView(R.id.reset_name)
    TextView reset_name;//复测录入/复测审核
    @InjectView(R.id.reset_time)
    TextView reset_time;//未复测、已复测
    @InjectView(R.id.reseticon)
    ImageView reseticon;
    @InjectView(R.id.ll_fuce)
    LinearLayout ll_fuce;
    private CalendarMode mode = CalendarMode.WEEKS;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private List<ActCalendarModel> calendarModels = new ArrayList<ActCalendarModel>();
    private int datetype;
    private String classid;
    private List<ClassModel> classModels = new ArrayList<ClassModel>();
    private List<TodayactModel> todayactModels = new ArrayList<TodayactModel>();
    private String dateStr;
    EasyAdapter<TodayactModel> adapter;

    public ClassedFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        ll_fuce.setOnClickListener(this);
        ll_chuDate.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        material_calendar.setOnDateChangedListener(this);
        material_calendar.setDatepageChangeListener(this);
        material_calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        final Calendar instance = Calendar.getInstance();
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

        list_activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TodayactModel todayactModel=todayactModels.get(i);
                    String activityId=todayactModel.getActivityId();
                Intent intent=new Intent(getContext(),ActivitydetailActivity.class);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
        //获取初始数据
        getalldatafirst();
        adapter = new EasyAdapter<TodayactModel>(getContext(), todayactModels, R.layout.activity_list) {
            @Override
            public void convert(ViewHolder holder, TodayactModel data, int position) {
                TextView activity_name = holder.getView(R.id.activity_name);
                activity_name.setText(data.getActivityName() + "(" + data.getCount() + ")");
                TextView activity_time = holder.getView(R.id.activity_time);
                activity_time.setText("集合时间 " + data.getActivityStartDate());
                ImageView activityicon = holder.getView(R.id.activityicon);
                String path = AddressManager.getHost();
                Picasso.with(getContext()).load(path + data.getActivityIcon()).into(activityicon);
            }

        };
        list_activity.setAdapter(adapter);
    }


    private void getalldatafirst() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), "C4E8E179-FD99-4955-8BF9-CF470898788B", new RequestCallback<ResponseData<ActivitydataModel>>() {
            @Override
            public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
//                calendarModels.clear();
                if (activitydataModelResponseData.getData() != null) {
                    ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                    if (activitydataModel.getList_ActCalendar() != null) {
                        calendarModels.addAll(activitydataModel.getList_ActCalendar());
                        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                    }

                    if (Constants.HEADCOACH == (activitydataModel.getClassRole()) || Constants.COACH == (activitydataModel.getClassRole()) || Constants.ASSISTANT == (activitydataModel.getClassRole())) {
                        reset_name.setText("复测审核");
                        iv_right.setImageResource(R.drawable.add_iv);
                        if (!activitydataModel.getRetest()) {
                            reset_time.setText("未审核");
                        } else {
                            reset_time.setText("已审核");
                        }
                    } else if (Constants.STUDENT == (activitydataModel.getClassRole())) {
                        reset_name.setText("复测录入");
                        iv_right.setVisibility(View.GONE);
                        if (!activitydataModel.getRetest()) {
                            reset_time.setText("未复测");
                        } else {
                            reset_time.setText("已复测");
                        }
                    }
                    //加载班级
                    classModels.clear();
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
                                if (classModels != null && !classModels.isEmpty()) {
                                    return classModels.get(position).getClassName();
                                } else {
                                    return "尚未开班";
                                }
                            }
                        });


                    }
                    //获取今天的活动
                    if (activitydataModel.getList_Activity() != null) {
                        todayactModels.addAll(activitydataModel.getList_Activity());
                        adapter.notifyDataSetChanged();
                    }


                }

            }
        });

        tv_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classid = classModels.get(i).getClassId();
                ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classid, new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
                        calendarModels.clear();
                        if (activitydataModelResponseData.getData() != null) {
                            ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                            if (activitydataModel.getList_ActCalendar() != null) {
                                calendarModels.addAll(activitydataModel.getList_ActCalendar());
                                new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                            }

                            if (Constants.HEADCOACH == (activitydataModel.getClassRole()) || Constants.COACH == (activitydataModel.getClassRole()) || Constants.ASSISTANT == (activitydataModel.getClassRole())) {
                                reset_name.setText("复测审核");
                                if (!activitydataModel.getRetest()) {
                                    reset_time.setText("未审核");
                                } else {
                                    reset_time.setText("已审核");
                                }
                            } else if (Constants.STUDENT == (activitydataModel.getClassRole())) {
                                reset_name.setText("复测录入");
                                if (!activitydataModel.getRetest()) {
                                    reset_time.setText("未复测");
                                } else {
                                    reset_time.setText("已复测");
                                }
                            }
                            //加载班级
                            classModels.clear();
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
//                                        String classname="";
//                                     if (classModels!=null&&classModels.isEmpty()) {
//                                        classname=classModels.get(position).getClassName();
//                                    }
                                        return classModels.get(position).getClassName();
                                    }
                                });


                            }
                            //获取今天的活动
                            if (activitydataModel.getList_Activity() != null) {
                                todayactModels.addAll(activitydataModel.getList_Activity());
                                adapter.notifyDataSetChanged();
                            }


                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());//字体加粗加大
        widget.invalidateDecorators();

        int YY = date.getCalendar().get(Calendar.YEAR);
        int MM = date.getCalendar().get(Calendar.MONTH) + 1;
        int DD = date.getCalendar().get(Calendar.DATE);

        dateStr = YY + "-" + MM + "-" + DD;
        todayactModels.clear();
        adapter.notifyDataSetChanged();
        gettodaydata(dateStr);


    }

    private void gettodaydata(String datestr2) {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).gettoday(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), "C4E8E179-FD99-4955-8BF9-CF470898788B", datestr2, new RequestCallback<ResponseData<TodaysModel>>() {
            @Override
            public void success(ResponseData<TodaysModel> todaysModelResponseData, Response response) {
                Util.toastMsg(todaysModelResponseData.getMsg());
                todayactModels.clear();
                if (todaysModelResponseData.getData() != null) {
                    TodaysModel model = todaysModelResponseData.getData();
                    if (model.getList_Activity() != null && !model.getList_Activity().isEmpty()) {
                        todayactModels.addAll(model.getList_Activity());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    //}
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
                startActivity(new Intent(getContext(), WriteFCActivity.class));
                break;
//            case R.id.ll_fuce:
//                startActivity(new Intent(getContext(), HonorActivity.class));
//                break;
        }
    }

    private String dateString;
    private String dateString1;
    private String dateString2;
    private String dateString3;

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
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < calendarModels.size(); i++) {
                datetype = calendarModels.get(i).getDateType();
                if (datetype == Constants.ACTIVITY) {
                    dateString = calendarModels.get(i).getMonthDate();
                    Log.e("dateString", dateString);
                    try {
                        Date date = df.parse(dateString);
                        calendar.setTime(date);
                        CalendarDay day = CalendarDay.from(calendar);
                        dates.add(day);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (datetype == Constants.CREATECLASS) {
                    dateString1 = calendarModels.get(i).getMonthDate();
                    Log.e("dateString1", dateString1);
                    try {
                        Date date = df.parse(dateString1);
                        calendar.setTime(date);
                        CalendarDay day = CalendarDay.from(calendar);
                        dates.add(day);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (datetype == Constants.RESET) {
                    dateString2 = calendarModels.get(i).getMonthDate();
                    Log.e("dateString2", dateString2);
                    try {
                        Date date = df.parse(dateString2);
                        calendar.setTime(date);
                        CalendarDay day = CalendarDay.from(calendar);
                        dates.add(day);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (datetype == Constants.FREE) {
                    dateString3 = calendarModels.get(i).getMonthDate();
                    Log.e("dateString3", dateString3);
                    try {
                        Date date = df.parse(dateString3);
                        calendar.setTime(date);
                        CalendarDay day = CalendarDay.from(calendar);
                        dates.add(day);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
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
                material_calendar.addDecorator(new SchelDecorator(datetype, calendarDays, getActivity()));
            }
        }
    }

}
