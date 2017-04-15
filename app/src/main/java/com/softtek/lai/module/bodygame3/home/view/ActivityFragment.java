package com.softtek.lai.module.bodygame3.home.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner3;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActCalendarModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.model.ActscalendarModel;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.activity.view.ActivitydetailActivity;
import com.softtek.lai.module.bodygame3.activity.view.CreateActActivity;
import com.softtek.lai.module.bodygame3.activity.view.FcAuditListActivity;
import com.softtek.lai.module.bodygame3.activity.view.FcStuActivity;
import com.softtek.lai.module.bodygame3.activity.view.InitAuditListActivity;
import com.softtek.lai.module.bodygame3.activity.view.InputView;
import com.softtek.lai.module.bodygame3.activity.view.MeasureListActivity;
import com.softtek.lai.module.bodygame3.activity.view.WriteFCActivity;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.SaveclassModel;
import com.softtek.lai.module.bodygame3.home.event.SaveClassModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.home.event.UpdateFuce;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.PopUpWindow.Util;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.EventDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.EventDecoratorDot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

import static android.app.Activity.RESULT_OK;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment implements OnDateSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    //学员版的三个复测状态
    private static final int FUCEING = 2;
    private static final int FUCE_FINISH = 1;
    private static final int FUCE_NOT_START = 3;

    @InjectView(R.id.pull)
    MySwipRefreshView pull;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title2)
    ArrowSpinner3 tv_title;
    //    @InjectView(R.id.list_activity)
//    RecyclerView list_activity;
    @InjectView(R.id.material_calendar)
    MaterialCalendarView material_calendar;
    @InjectView(R.id.ll_chuDate)
    LinearLayout ll_chuDate;//初始数据录入、审核
    @InjectView(R.id.tv_initData_Name)
    TextView tv_initData_Name;//初始按钮名称
    @InjectView(R.id.tv_chustatus)
    TextView tv_chustatus;//初始数据状态
    @InjectView(R.id.reset_name)
    TextView reset_name;//复测录入/复测审核
    @InjectView(R.id.reset_time)
    TextView reset_time;//未复测、已复测
    @InjectView(R.id.ll_fuce)
    LinearLayout ll_fuce;
    @InjectView(R.id.ll_task)
    LinearLayout ll_task;

    //    @InjectView(R.id.ll_for_stu)
//    LinearLayout ll_for_stu;
    @InjectView(R.id.uncheckNum_tv)
    TextView uncheckNum_tv;

    private SaveclassModel saveclassModel;

    private List<ActCalendarModel> calendarModels = new ArrayList<>();
    private List<CalendarDay> calendarModel_act = new ArrayList<>();
    private List<CalendarDay> calendarModel_create = new ArrayList<>();
    private List<CalendarDay> calendarModel_reset = new ArrayList<>();//复测
    private List<CalendarDay> calendarModel_free = new ArrayList<>();
    private String classid = "";
    private List<ClassModel> classModels = new ArrayList<>();
    private List<TodayactModel> todayactModels = new ArrayList<>();
    private String dateStr;
    private int classrole;
    private ClassModel classModel;

    public ActivityFragment() {

    }

    @Override
    protected void lazyLoad() {
        isSelector = false;
        pull.setRefreshing(true);
        onRefresh();
    }

    @Override
    protected void onVisible() {
        if (isSelector) {
            isPrepared = false;
        }
        super.onVisible();
    }

    @Override
    protected void initViews() {
        saveclassModel = new SaveclassModel();
        saveclassModel.setDates(DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate());
        //显示创建活动按钮只要是Sp顾问
        if (String.valueOf(Constants.SP).equals(UserInfoModel.getInstance().getUser().getUserrole())) {
            fl_right.setVisibility(View.VISIBLE);
        } else {
            fl_right.setVisibility(View.GONE);
        }
        ll_fuce.setOnClickListener(this);
        ll_chuDate.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);


        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        pull.setOnRefreshListener(this);
        //日历
        material_calendar.setOnDateChangedListener(this);
        material_calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        Calendar instance = Calendar.getInstance();
        material_calendar.setSelectedDate(instance.getTime());
        material_calendar.removeDecorators();
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR) - 1, Calendar.JANUARY, 1);
        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) + 1, Calendar.DECEMBER, 31);
        material_calendar.setSelectionColor(ContextCompat.getColor(getContext(), R.color.yellow));
        material_calendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)//周模式(WEEKS)或月模式（MONTHS）
                .commit();

        material_calendar.setShowOtherDates(0);
    }

    @Override
    protected void initDatas() {
        //班级列表
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, ClassModel data, int position) {
                ImageView iv_icon = holder.getView(R.id.iv_icon);
                boolean selected = tv_title.getSelectedIndex() == position;
                int icon;
                switch (data.getClassRole()) {
                    case 1:
                        icon = selected ? R.drawable.class_zongjiaolian_re : R.drawable.class_zongjiaolian;
                        break;
                    case 2:
                        icon = selected ? R.drawable.class_jiaolian_re : R.drawable.class_jiaolian;
                        break;
                    case 3:
                        icon = selected ? R.drawable.class_zhujiao_re : R.drawable.class_zhujiao;
                        break;
                    default:
                        icon = selected ? R.drawable.class_xueyuan_re : R.drawable.class_xueyuan;
                        break;
                }
                iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                int color = selected ? 0xFF000000 : 0xFFFFFFFF;
                TextView tv_role = holder.getView(R.id.tv_role_name);
                int role = data.getClassRole();
                tv_role.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                tv_role.setTextColor(color);
                TextView tv_number = holder.getView(R.id.tv_number);
                tv_number.setText(data.getClassCode());
                tv_number.setTextColor(color);
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data.getClassName());
                tv_class_name.setTextColor(color);
                ImageView iv_sel = holder.getView(R.id.iv_select);
                iv_sel.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
                RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
                rl_bg.setBackgroundColor(selected ? 0xFFFFFFFF : 0x00FFFFFF);
            }

            @Override
            public String getText(int position) {
                if (classModels != null && !classModels.isEmpty()) {
                    classid = classModels.get(position).getClassId();
                    return classModels.get(position).getClassName();
                } else {
                    return "暂无班级";
                }
            }
        });

        tv_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classid = classModels.get(i).getClassId();
                classrole = classModels.get(i).getClassRole();
                material_calendar.invalidateDecorators();

                saveclassModel.setClassId(classModels.get(i).getClassId());
                saveclassModel.setClassName(classModels.get(i).getClassName());
                saveclassModel.setClassRole(classModels.get(i).getClassRole());
                saveclassModel.setClassWeek(classModels.get(i).getClassWeek());
                saveclassModel.setClassCode(classModels.get(i).getClassCode());
                lazyLoad();
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


//    String newTime = year
//    　　+ "-"+ ((monthOfYear + 1) < 10 "0"
//            　　+ (monthOfYear + 1)
//            　　: (monthOfYear + 1))
//            　　+ "-"
//            　　+ (dayOfMonth < 10 "0" + dayOfMonth
//    　　: dayOfMonth);

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        int year = date.getCalendar().get(Calendar.YEAR);
        int month = date.getCalendar().get(Calendar.MONTH) + 1;
        int day = date.getCalendar().get(Calendar.DATE);
        //根据点击的日期查询该天的活动列表
//        dateStr = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sdf.format(date.getDate());
        saveclassModel.setDates(dateStr);
        ll_fuce.setVisibility(View.GONE);

        ll_task.removeAllViews();
        if (!TextUtils.isEmpty(classid)) {
            gettodaydata(dateStr);
        }


    }

    private void gettodaydata(final String datestr) {
        pull.setRefreshing(false);
        ZillaApi.NormalRestAdapter.create(ActivityService.class).gettoday(classid, UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                classid, datestr, new RequestCallback<ResponseData<TodaysModel>>() {
                    @Override
                    public void success(ResponseData<TodaysModel> data, Response response) {
                        try {
                            if (data.getData() != null) {
                                TodaysModel model = data.getData();
                                int resetstatus = model.getRetestStatus();//获取用户选择日期的复测状态
                                BtnTag tag = new BtnTag();
                                tag.role = model.getClassRole();
                                tag.date = datestr;
                                if (model.getClassRole() == Constants.STUDENT) {//如果这个人是学员
                                    reset_name.setText("复测录入");
                                    switch (resetstatus) {
                                        case 1://已过去的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);

                                            if (model.getIsRetest() == 1) {
                                                ll_fuce.setEnabled(false);
                                                reset_time.setText("未复测");
                                                tag.resetstatus = model.getIsRetest();
                                            } else if (model.getIsRetest() == 2) {
                                                ll_fuce.setEnabled(false);
                                                reset_time.setText("未审核");
                                                tag.resetstatus = model.getIsRetest();
                                            } else if (model.getIsRetest() == 3) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("已审核");
                                                tag.resetstatus = model.getIsRetest();

                                            }
                                            tag.status = FUCE_FINISH;
                                            break;
                                        case 2://进行中的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(true);
                                            if (model.getIsRetest() == 1) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("未复测");
                                                tag.resetstatus = model.getIsRetest();
                                            } else if (model.getIsRetest() == 2) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("待审核");
                                                tag.resetstatus = model.getIsRetest();
                                            } else if (model.getIsRetest() == 3) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("已审核");
                                                tag.resetstatus = model.getIsRetest();
                                            }
                                            tag.status = FUCEING;
                                            break;
                                        case 3://未开始的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(false);
                                            reset_time.setText("未开始");
                                            tag.status = FUCE_NOT_START;
                                            break;
                                        default:
                                            ll_fuce.setVisibility(View.GONE);
                                            break;
                                    }
                                    ll_fuce.setTag(tag);
                                } else {//非学员
                                    reset_name.setText("复测审核");

                                    switch (resetstatus) {
                                        case 1://已过去的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(true);
                                            reset_time.setText("待审核" + model.getNum());

                                            uncheckNum_tv.setText("未测量X人");

                                            tag.status = FUCE_FINISH;
                                            break;
                                        case 2://进行中的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(true);

                                            uncheckNum_tv.setText("未测量X人");

                                            reset_time.setText("待审核" + model.getNum() + "人");
                                            tag.status = FUCEING;
                                            break;
                                        case 3://未开始的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(false);
                                            reset_time.setText("未开始");

                                            uncheckNum_tv.setText("未开始");

                                            tag.status = FUCE_NOT_START;
                                            break;
                                        default:
                                            ll_fuce.setVisibility(View.GONE);
                                            break;
                                    }
                                }
                                ll_fuce.setTag(tag);

                                ll_task.removeAllViews();
                                if (model.getList_Activity() != null && !model.getList_Activity().isEmpty()) {
                                    ll_task.setVisibility(View.VISIBLE);
                                    todayactModels.clear();
                                    todayactModels.addAll(model.getList_Activity());
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    for (int i = 0; i < todayactModels.size(); i++) {
                                        TodayactModel model1 = todayactModels.get(i);
                                        int counts = todayactModels.size();
                                        if (ll_task != null) {
                                            View view = new InputView(ActivityFragment.this, model1, counts, classid, classrole);
                                            ll_task.addView(view, lp);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                Intent intent = new Intent(getContext(), CreateActActivity.class);
                intent.putExtra("classid", classid);
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_chuDate: { //学员：初始数据录入   其他角色：初始数据审核
                BtnTag tag = (BtnTag) view.getTag();
                if (tag == null) {
                    return;
                }
                if (tag.role == Constants.STUDENT) {//学员
                    com.github.snowdream.android.util.Log.i("初始数据的tag=" + tag.toString());
                    Intent chuDate = new Intent(getContext(), WriteFCActivity.class);
                    chuDate.putExtra("typeDate", tag.date);
                    chuDate.putExtra("firststatus", tag.isfirst);//初始数据录入状态 1：未录入，2：未审核，3：已审核
                    chuDate.putExtra("classId", classid);
                    startActivityForResult(chuDate, 2);

                } else {
                    com.github.snowdream.android.util.Log.i("初始数据的tag=" + tag.toString());
                    Intent chuDate = new Intent(getContext(), InitAuditListActivity.class);
                    chuDate.putExtra("typeDate", tag.date);
                    chuDate.putExtra("classId", classid);
//                    startActivity(chuDate);
                    startActivityForResult(chuDate, 2);
                }
            }
            break;
            case R.id.ll_left:
                getActivity().finish();
                break;
            case R.id.ll_fuce://学员：复测录入    其他：复测审核
            {
                BtnTag tag = (BtnTag) view.getTag();
                if (tag == null) {
                    return;
                }
                if (tag.role == Constants.STUDENT) {//学员
                    com.github.snowdream.android.util.Log.i("复测按钮的tag=" + tag.toString());
                    Intent fuce = new Intent(getContext(), FcStuActivity.class);
                    fuce.putExtra("classId", classid);
                    fuce.putExtra("resetstatus", tag.resetstatus);//复测状态：1：未复测 2：未审核 3：已复测
                    fuce.putExtra("resetdatestatus", tag.status);//复测日状态  1:已过去 2：进行中 3：未开始
                    fuce.putExtra("typeDate", tag.date);
                    Log.i("数据测验", "复测状态" + tag.resetstatus + "复测日状态" + tag.status + "班级id" + classid + "日期" + tag.date);
                    startActivityForResult(fuce, 3);
                } else {
                    com.github.snowdream.android.util.Log.i("复测按钮的tag=" + tag.toString());
                    Intent fuce = new Intent(getContext(), FcAuditListActivity.class);
                    fuce.putExtra("classId", classid);
                    fuce.putExtra("resetdatestatus", tag.status);//复测日状态  1:已过去 2：进行中 3：未开始
                    fuce.putExtra("typeDate", tag.date);
                    startActivityForResult(fuce, 3);
                }
            }
            break;
//            case R.id.ll_for_stu: {
//                Intent intent1 = new Intent(getContext(), MeasureListActivity.class);
//                intent1.putExtra("classId", classid);
//                startActivity(intent1);
//            }
//            break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                material_calendar.removeDecorators();

                String acttime = data.getStringExtra("acttime");
                CalendarDay calendarDay = getCalendarDay(acttime);
                if (calendarModel_free.contains(calendarDay)) {
                    calendarModel_free.remove(calendarDay);
                    calendarModel_act.add(calendarDay);
                }

                //加载数据
                if (!TextUtils.isEmpty(saveclassModel.getDates())) {
                    gettodaydata(saveclassModel.getDates());
                }

                new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                new ApiSimulatorDot().executeOnExecutor(Executors.newSingleThreadExecutor());


            } else if (requestCode == 2) {
//                lazyLoad();
                if (classrole == Constants.STUDENT) {
                    int IsInitW = data.getExtras().getInt("IsInitW");
                    com.github.snowdream.android.util.Log.i("IsInitW" + IsInitW);

                    if (IsInitW == 1) {
                        tv_chustatus.setText("待审核");
                        if (ll_chuDate.getTag() != null) {
                            BtnTag tag = (BtnTag) ll_chuDate.getTag();
                            tag.isfirst = 2;
                            ll_chuDate.setTag(tag);
                        }
//                        BtnTag tag = new BtnTag();
//                        tag.isfirst = 2;
//                        tag.role=Constants.STUDENT;
//                        tag.date=saveclassModel.getDates();
//                        ll_chuDate.setTag(tag);
                    }
                } else {
                    int numbers = data.getExtras().getInt("Auditnum");
                    Log.i("numbers", numbers + "");
                    tv_chustatus.setText("待审核" + numbers + "人");
                }

            } else if (requestCode == 3) {
                com.github.snowdream.android.util.Log.i("复测更新。。。。。。。。。。。。。。");
                if (classrole == Constants.STUDENT) {
                    int IsFcSt = data.getExtras().getInt("IsFcSt");
                    if (IsFcSt == 1) {
                        reset_time.setText("待审核");
                        if (ll_fuce.getTag() != null) {
                            BtnTag tag = (BtnTag) ll_fuce.getTag();
                            tag.resetstatus = 2;
                            ll_fuce.setTag(tag);
                        }
                    }
                } else {
                    int numbers = data.getExtras().getInt("Auditnum");
                    Log.i("待审核人数", numbers + "");
                    reset_time.setText("待审核" + numbers + "人");
                }
            } else if (requestCode == 110) {
                int operation = data.getExtras().getInt("operation");
                if (operation == ActivitydetailActivity.ACTIVITY_DEL) {
                    int counts = data.getExtras().getInt("count");
                    if (counts == 0) {
                        material_calendar.removeDecorators();
                        CalendarDay calendarDay = getCalendarDay(saveclassModel.getDates());
                        if (calendarModel_act.contains(calendarDay)) {
                            calendarModel_act.remove(calendarDay);
                            calendarModel_free.add(calendarDay);
                        }
                        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                        new ApiSimulatorDot().executeOnExecutor(Executors.newSingleThreadExecutor());
                        if (!TextUtils.isEmpty(saveclassModel.getDates())) {
                            gettodaydata(saveclassModel.getDates());
                        }
                    } else if (counts < todayactModels.size()) {
                        if (!TextUtils.isEmpty(saveclassModel.getDates())) {
                            gettodaydata(saveclassModel.getDates());
                        }
                    }

                } else if (operation == ActivitydetailActivity.ACTIVITY_EXIT) {
                    if (!TextUtils.isEmpty(saveclassModel.getDates())) {
                        gettodaydata(saveclassModel.getDates());
                    }
                } else if (operation == ActivitydetailActivity.ACTIVITY_SIGN) {
                    if (!TextUtils.isEmpty(saveclassModel.getDates())) {
                        gettodaydata(saveclassModel.getDates());
                    }
                }
            }

        }

    }


    private EventDecorator decorator;
    private EventDecorator decorator_act;
    private EventDecorator decorator_free;
    private EventDecorator decorator_create;
    String now = DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();

    @Override
    public void onRefresh() {
        material_calendar.removeDecorators();
        material_calendar.removeDecorator(decorator);
        material_calendar.removeDecorator(decorator_act);
        material_calendar.removeDecorator(decorator_create);
        material_calendar.removeDecorator(decorator_free);
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(classid, UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(), classid, saveclassModel.getDates(), new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> data, Response response) {
                        try {

                            pull.setRefreshing(false);
                            if (data.getData() != null) {
                                ActivitydataModel activitydataModel = data.getData();
                                classrole = activitydataModel.getClassRole();
                                if (Constants.HEADCOACH == classrole) {
                                    fl_right.setVisibility(View.VISIBLE);
                                } else {
                                    fl_right.setVisibility(View.GONE);
                                }
                                //加载班级
                                classModels.clear();
                                if (activitydataModel.getList_Class() != null && !activitydataModel.getList_Class().isEmpty()) {
                                    ll_chuDate.setVisibility(View.VISIBLE);
                                    classModels.addAll(activitydataModel.getList_Class());
                                    if (TextUtils.isEmpty(classid)) {
                                        classid = classModels.get(0).getClassId();
                                        classrole = classModels.get(0).getClassRole();
                                        tv_title.setSelected(0);
                                        tv_title.notifChange();

                                    } else {
                                        for (int i = 0, j = classModels.size(); i < j; i++) {
                                            ClassModel model = classModels.get(i);
                                            if (model.getClassId().equals(classid)) {
                                                tv_title.setSelected(i);
                                                tv_title.notifChange();
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    //如果班级是空
                                    ll_chuDate.setVisibility(View.GONE);
                                    ll_fuce.setVisibility(View.GONE);
                                    tv_title.setText("暂无班级");
                                    return;
                                }
                                //标记日历中的活动/复测等标记
                                if (activitydataModel.getList_ActCalendar() != null && !activitydataModel.getList_ActCalendar().isEmpty()) {
                                    calendarModels.clear();
                                    calendarModels.addAll(activitydataModel.getList_ActCalendar());
                                    material_calendar.removeDecorators();
                                    filterTypesData();
                                    new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

                                    new ApiSimulatorDot().executeOnExecutor(Executors.newSingleThreadExecutor());

                                }
                                //判断是否显示初始数据录入根据此用户在该班级的角色
                                if (activitydataModel.getClassRole() == Constants.STUDENT) {//是学员
                                    tv_initData_Name.setText("初始数据录入");
                                    BtnTag tag = new BtnTag();
                                    tag.role = activitydataModel.getClassRole();
                                    if (activitydataModel.getIsFirst() == 1) {
                                        tv_chustatus.setText("未录入");
                                    } else if (activitydataModel.getIsFirst() == 2) {
                                        tv_chustatus.setText("待审核");
                                    } else if (activitydataModel.getIsFirst() == 3) {
                                        tv_chustatus.setText("已审核");
                                    }
                                    tag.isfirst = activitydataModel.getIsFirst();//初始数据录入状态： 1：未录入 2：未审核 3：已审核
                                    tag.date = now;
                                    ll_chuDate.setTag(tag);
                                } else {//非学员
                                    tv_initData_Name.setText("初始数据审核");
                                    tv_chustatus.setText("待审核" + activitydataModel.getIsFirst() + "人");
                                    BtnTag tag = new BtnTag();
                                    tag.role = activitydataModel.getClassRole();
                                    ll_chuDate.setTag(tag);
                                }
                                //获取当前日期是否需要复测，活动
                                Log.i("act and reset", calendarModels.toString());
                                for (ActCalendarModel model : calendarModels) {
                                    if (DateUtil.getInstance(DateUtil.yyyy_MM_dd).isEq(model.getMonthDate(), saveclassModel.getDates())) {
                                        //如果是复测类型
                                        if (model.getDateType() == 3 || model.getDateType() == Constants.CREATECLASS) {
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            if (activitydataModel.getClassRole() == Constants.STUDENT) {//是学员
                                                reset_name.setText("复测录入");
                                                //学员复测的状态：未复测，未审核，已审核
                                                BtnTag tag = new BtnTag();
                                                tag.role = activitydataModel.getClassRole();
                                                if (activitydataModel.getRetestStatus() == 2) {//进行中
                                                    tag.status = FUCEING;//复测日状态
                                                    tag.date = now;
                                                    if (activitydataModel.getIsRetest() == 1) {
                                                        reset_time.setText("未复测");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    } else if (activitydataModel.getIsRetest() == 2) {
                                                        reset_time.setText("待审核");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    } else if (activitydataModel.getIsRetest() == 3) {
                                                        reset_time.setText("已审核");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    }
                                                } else if (activitydataModel.getRetestStatus() == 1) {//已过去的复测日

                                                    tag.status = FUCE_FINISH;//复测日状态
                                                    tag.date = saveclassModel.getDates();
                                                    if (activitydataModel.getIsRetest() == 1) {
                                                        ll_fuce.setEnabled(false);
                                                        reset_time.setText("未复测");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    } else if (activitydataModel.getIsRetest() == 2) {
                                                        ll_fuce.setEnabled(false);
                                                        reset_time.setText("待审核");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    } else if (activitydataModel.getIsRetest() == 3) {
                                                        ll_fuce.setEnabled(true);
                                                        reset_time.setText("已审核");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    }
                                                } else {//未开始的复测日
                                                    ll_fuce.setEnabled(false);
                                                    tag.status = FUCE_NOT_START;
                                                    reset_time.setText("未开始");

                                                }
                                                ll_fuce.setTag(tag);
                                            } else {//非学员
//                                                ll_for_stu.setVisibility(View.VISIBLE);

                                                BtnTag tag = new BtnTag();
                                                tag.role = activitydataModel.getClassRole();
                                                if (activitydataModel.getRetestStatus() == 3) {
                                                    ll_fuce.setEnabled(false);
                                                    tag.status = FUCE_NOT_START;
                                                    reset_time.setText("未开始");
                                                } else {
                                                    Log.i("reteststatus", activitydataModel.getRetestStatus() + "");
                                                    ll_fuce.setEnabled(true);
                                                    reset_name.setText("复测审核");
                                                    reset_time.setText("待审核" + activitydataModel.getNum() + "人");
                                                    tag.status = activitydataModel.getRetestStatus();
                                                    tag.date = saveclassModel.getDates();

                                                }
                                                ll_fuce.setTag(tag);

                                            }
                                            ll_task.removeAllViews();
                                            if (!activitydataModel.getList_Activity().isEmpty()) {
                                                ll_task.setVisibility(View.VISIBLE);
                                                Log.i("act and date", activitydataModel.getList_Activity().toString() + "," + saveclassModel.getDates());
                                                todayactModels.clear();
                                                //有活动
                                                todayactModels.addAll(activitydataModel.getList_Activity());
                                                Log.e("活动列表", todayactModels.toString());
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                for (int i = 0; i < todayactModels.size(); i++) {
                                                    TodayactModel model1 = todayactModels.get(i);
                                                    int counts = todayactModels.size();
                                                    View view = new InputView(ActivityFragment.this, model1, counts, classid, classrole);
                                                    if (ll_task != null) {
                                                        com.github.snowdream.android.util.Log.i("添加活动");
                                                        ll_task.addView(view, lp);
                                                    }
                                                }
                                            }
                                        }
                                        if (model.getDateType() == 4) {
                                            ll_fuce.setVisibility(View.GONE);
                                            ll_task.removeAllViews();
                                        }
                                        //如果是活动类型
                                        if (model.getDateType() == 1 && !activitydataModel.getList_Activity().isEmpty()) {
                                            ll_task.setVisibility(View.VISIBLE);
                                            ll_task.removeAllViews();
                                            ll_fuce.setVisibility(View.GONE);
                                            todayactModels.clear();
                                            //有活动
                                            todayactModels.addAll(activitydataModel.getList_Activity());
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            for (int i = 0; i < todayactModels.size(); i++) {
                                                TodayactModel model1 = todayactModels.get(i);
                                                int counts = todayactModels.size();
                                                View view = new InputView(ActivityFragment.this, model1, counts, classid, classrole);
                                                if (ll_task != null) {
                                                    ll_task.addView(view, lp);
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            pull.setRefreshing(false);
                            super.failure(error);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


//        }
    }


    //日历上活动信息展示
    public class ApiSimulator extends AsyncTask<List<ActscalendarModel>, Void, Void> {

        @Override
        protected Void doInBackground(List<ActscalendarModel>... lists) {
            //把各种类型改得数据过滤出来
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            if (material_calendar != null) {
                decorator = new EventDecorator(Constants.RESET, calendarModel_reset, classrole, getActivity());
                material_calendar.addDecorator(decorator);
                decorator_act = new EventDecorator(Constants.ACTIVITY, calendarModel_act, classrole, getActivity());
                material_calendar.addDecorator(decorator_act);
                decorator_create = new EventDecorator(Constants.CREATECLASS, calendarModel_create, classrole, getActivity());
                material_calendar.addDecorator(decorator_create);
                decorator_free = new EventDecorator(Constants.FREE, calendarModel_free, classrole, getActivity());
                material_calendar.addDecorator(decorator_free);
            }
        }
    }

    EventDecoratorDot eventDecorator_reset;
    EventDecoratorDot eventDecoratorDot_act;

    private class ApiSimulatorDot extends AsyncTask<List<CalendarDay>, Void, Void> {
        @Override
        protected Void doInBackground(List<CalendarDay>... lists) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            if (material_calendar != null) {
                eventDecorator_reset = new EventDecoratorDot(Color.rgb(135, 199, 67), calendarModel_reset, getActivity());
                material_calendar.addDecorator(eventDecorator_reset);
                eventDecoratorDot_act = new EventDecoratorDot(Color.rgb(237, 118, 108), calendarModel_act, getActivity());
                material_calendar.addDecorator(eventDecoratorDot_act);
            }
        }
    }

    private CalendarDay getCalendarDay(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        CalendarDay day = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            calendar.setTime(date);
            day = CalendarDay.from(calendar);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return day;
    }

    // 过滤各种类型的数据表
    private void filterTypesData() {
        calendarModel_reset.clear();
        calendarModel_act.clear();
        calendarModel_create.clear();
        calendarModel_free.clear();
        for (int i = 0; i < calendarModels.size(); i++) {
            int datetype = calendarModels.get(i).getDateType();
            String date = calendarModels.get(i).getMonthDate();
            if (Constants.CREATECLASS == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    calendarModel_create.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                }
            } else if (Constants.RESET == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    calendarModel_reset.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                }
            } else if (Constants.ACTIVITY == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    calendarModel_act.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                }
            } else if (Constants.FREE == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    calendarModel_free.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                }
            }
        }
    }

    //更新班级
    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 0) {
            //更新班级姓名
            for (ClassModel model : classModels) {
                if (model.getClassCode().equals(clazz.getModel().getClassCode())) {
                    model.setClassName(clazz.getModel().getClassName());
                    model.setClassRole(clazz.getModel().getClassRole());
                    model.setClassCode(clazz.getModel().getClassCode());
                    model.setClassId(clazz.getModel().getClassId());
                    break;
                }
            }
            tv_title.getAdapter().notifyDataSetChanged();
            tv_title.setSelected(tv_title.getSelectedIndex());
        } else if (clazz.getStatus() == 1) {
            //添加新班级
            com.github.snowdream.android.util.Log.i("添加新班级。。。。。。。。。。。。。");
            ClassModel model = new ClassModel();
            model.setClassId(clazz.getModel().getClassId());
            model.setClassCode(clazz.getModel().getClassCode());
            model.setClassName(clazz.getModel().getClassName());
            model.setClassRole(clazz.getModel().getClassRole());
            this.classModels.add(model);
            tv_title.notifChange();
        } else if (clazz.getStatus() == 2) {
            //删除班级
            Iterator<ClassModel> iter = classModels.iterator();
            while (iter.hasNext()) {
                ClassModel model = iter.next();
                if (model.getClassId().equals(clazz.getModel().getClassId())) {
                    iter.remove();
                    break;
                }
            }
            tv_title.notifChange();
            if (classModels.isEmpty()) {
                classid = "";
                this.classModel = null;
            } else {
                tv_title.setSelected(0);
                this.classModel = classModels.get(0);
                classid = this.classModel.getClassId();

            }
            lazyLoad();
        }
    }

    @Subscribe
    public void updatefuce(UpdateFuce updateFuce) {

        if (updateFuce.getClassId().equals(classid)) {
            lazyLoad();
        }
    }

    private boolean isSelector;

    @Subscribe
    public void classSelect(SaveClassModel saveClassModel) {
        isSelector = true;
        classid = saveClassModel.classId;
    }

    static class BtnTag {
        public int role;
        public int status;//复测日状态
        public String date;
        public int isfirst;//初始录入状态
        public int resetstatus;//复测状态

        @Override
        public String toString() {
            return "BtnTag{" +
                    "role=" + role +
                    ", status=" + status +
                    ", date='" + date + '\'' +
                    ", isfirst=" + isfirst +
                    ", resetstatus=" + resetstatus +
                    '}';
        }
    }


}
