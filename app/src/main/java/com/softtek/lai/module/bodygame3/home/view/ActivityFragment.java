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
import android.widget.RadioButton;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.ListDialog;
import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActCalendarModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.model.CalendarDayModel;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.activity.view.ActivitydetailActivity;
import com.softtek.lai.module.bodygame3.activity.view.CreateActActivity;
import com.softtek.lai.module.bodygame3.activity.view.InputView;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.SaveclassModel;
import com.softtek.lai.module.bodygame3.home.event.SaveClassModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.home.event.UpdateFuce;
import com.softtek.lai.module.bodygame3.more.view.UpdateFuceTimeActivity;
import com.softtek.lai.module.customermanagement.view.GymClubActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.EventDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.EventDecoratorDot;
import com.softtek.lai.widgets.materialcalendarview.decorators.ResetDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.ResetDecoratorDot;

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
    private static final String TAG = "ActivityFragment";

    //学员版的三个复测状态
    private static final int FUCEING = 2;
    private static final int FUCE_FINISH = 1;
    private static final int FUCE_NOT_START = 3;

    private static int IsFirst_save = 0;

    @InjectView(R.id.pull)
    MySwipRefreshView pull;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title2)
    ListDialog tv_title;

    @InjectView(R.id.material_calendar)
    MaterialCalendarView material_calendar;


    @InjectView(R.id.ll_task)
    LinearLayout ll_task;

    @InjectView(R.id.ll_change_date)
    LinearLayout mChangedDate;

    @InjectView(R.id.ll_first_data)
    LinearLayout mFirstData;
    private SaveclassModel saveclassModel;

    private List<ActCalendarModel> calendarModels = new ArrayList<>();
    private List<CalendarDay> calendarModel_act = new ArrayList<>();
    private List<CalendarDayModel> calendarModel_reset = new ArrayList<>();//复测
    private List<CalendarDay> calendarModel_free = new ArrayList<>();
    private String classid = "";
    private List<ClassModel> classModels = new ArrayList<>();
    private List<TodayactModel> todayactModels = new ArrayList<>();
    private String dateStr;
    private int classrole;
    private ClassModel classModel;
    private int HasInitMeasuredData;//是否有初录入数据 0：没有 1：有
    private String classId;
    private boolean isWorker;
    private int index = 0;

    public ActivityFragment() {

    }

    @Override
    protected void lazyLoad() {
        isSelector = false;
        pull.setRefreshing(true);
        onRefresh();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (pull != null && isVisibleToUser) {
//            pull.setRefreshing(true);
//            onRefresh();
//        }
//    }
//
//    @Override
//    protected void onVisible() {
//        if (isSelector) {
//            isPrepared = false;
//        }
//        super.onVisible();
//    }

    @Override
    protected void initViews() {
        if (getArguments() != null) {
            classId = getArguments().getString("classId");
        }
        saveclassModel = new SaveclassModel();
        saveclassModel.setDates(DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate());
        //显示创建活动按钮只要是Sp顾问
        if (String.valueOf(Constants.SP).equals(UserInfoModel.getInstance().getUser().getUserrole())) {
            fl_right.setVisibility(View.VISIBLE);
        } else {
            fl_right.setVisibility(View.GONE);
        }
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        mChangedDate.setOnClickListener(this);

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
        mFirstData.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        //班级列表
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, ClassModel data, int position) {
                ImageView iv_icon = holder.getView(R.id.iv_icon);
                boolean selected = tv_title.getSelectedIndex() == position;
                int icon = R.drawable.class_xueyuan;
                isWorker = data.isWorker();//是否是俱乐部工作人员，true-是，false-否
                if (isWorker) {
                    icon = R.drawable.worker;
                }
                switch (data.getClassRole()) {
                    case 1:
                        icon = R.drawable.class_zongjiaolian;
                        break;
                    case 2:
                        icon = R.drawable.class_jiaolian;
                        break;
                    case 3:
                        icon = R.drawable.class_zhujiao;
                        break;
                    case 4:
                        icon = R.drawable.class_xueyuan;
                        break;
                }
                iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                TextView tv_number = holder.getView(R.id.tv_number);
                tv_number.setText("班级编号:" + data.getClassCode());
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data.getClassName());
                RadioButton iv_sel = holder.getView(R.id.iv_select);
                iv_sel.setChecked(selected);
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
                index = i;
                classid = classModels.get(i).getClassId();
                classrole = classModels.get(i).getClassRole();
                material_calendar.invalidateDecorators();

                saveclassModel.setClassId(classModels.get(i).getClassId());
                saveclassModel.setClassName(classModels.get(i).getClassName());
                saveclassModel.setClassRole(classModels.get(i).getClassRole());
                saveclassModel.setClassWeek(classModels.get(i).getClassWeek());
                saveclassModel.setClassCode(classModels.get(i).getClassCode());
                lazyLoad();
                pull.setRefreshing(true);
                onRefresh();
            }
        });
        EventBus.getDefault().register(this);
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //根据点击的日期查询该天的活动列表
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sdf.format(date.getDate());
        saveclassModel.setDates(dateStr);

        ll_task.removeAllViews();
        if (!TextUtils.isEmpty(classid)) {
            gettodaydata(dateStr);
        }


    }

    private void gettodaydata(final String datestr) {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).gettoday(classid, UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                classid, datestr, new RequestCallback<ResponseData<TodaysModel>>() {
                    @Override
                    public void success(ResponseData<TodaysModel> data, Response response) {
                        try {
                            if (data.getData() != null) {
                                TodaysModel model = data.getData();
                                Log.i(TAG, "获取到的数据 = " + new Gson().toJson(model));
                                int resetstatus = model.getRetestStatus();//获取用户选择日期的复测状态
                                BtnTag tag = new BtnTag();
                                tag.date = datestr;
                                tag.role = model.getClassRole();
                                if (model.getClassRole() == Constants.STUDENT) {//如果这个人是学员
                                    switch (resetstatus) {
                                        case 1://已过去的复测日
                                            tag.status = FUCE_FINISH;
                                            break;
                                        case 2://进行中的复测日
                                            tag.status = FUCEING;
                                            break;
                                        case 3://未开始的复测日
                                            tag.status = FUCE_NOT_START;
                                            break;
                                        default:
                                            break;
                                    }
                                } else {//非学员
                                    switch (resetstatus) {
                                        case 1://已过去的复测日
                                            tag.status = FUCE_FINISH;
                                            break;
                                        case 2://进行中的复测日
                                            tag.status = FUCEING;
                                            break;
                                        case 3://未开始的复测日
                                            tag.status = FUCE_NOT_START;
                                            break;
                                        default:
                                            break;
                                    }
                                }

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
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                Intent intent = new Intent(getContext(), CreateActActivity.class);
                intent.putExtra("classid", classid);
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_left:
                if (classId != null) {
                    startActivity(new Intent(getActivity(), GymClubActivity.class));
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.ll_change_date:
                Intent dateIntent = new Intent(getActivity(), UpdateFuceTimeActivity.class);
                dateIntent.putExtra("classId", classid);
                startActivityForResult(dateIntent, UpdateFuceTimeActivity.REQUEST_CODE);
                break;
            case R.id.ll_first_data:
                Intent firstIntent;
                if (!isWorker && classrole != 1) {
                    firstIntent = new Intent(getActivity(), InitialDetailActivity.class);
                    firstIntent.putExtra("phone",UserInfoModel.getInstance().getUser().getMobile());
                    firstIntent.putExtra("accountId",(int)UserInfoModel.getInstance().getUserId());
                    firstIntent.putExtra("userName",UserInfoModel.getInstance().getUser().getNickname());
                } else {
                    firstIntent = new Intent(getActivity(), InitialDataActivity.class);
                }
                firstIntent.putExtra("classId", classid);
                startActivity(firstIntent);
                break;
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
                if (classrole == Constants.STUDENT) {
                    HasInitMeasuredData = 1;
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
            } else if (requestCode == UpdateFuceTimeActivity.REQUEST_CODE) {
                lazyLoad();
                pull.setRefreshing(true);
                onRefresh();
            }

        }

    }


    String now = DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();

    @Override
    public void onRefresh() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(classid, UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(), classid, saveclassModel.getDates(), new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> data, Response response) {
                        try {
                            pull.setRefreshing(false);
                            if (data.getData() != null) {
                                material_calendar.removeDecorators();
                                ActivitydataModel activitydataModel = data.getData();
                                HasInitMeasuredData = activitydataModel.getHasInitMeasuredData();
                                classrole = activitydataModel.getClassRole();
                                classrole = activitydataModel.getList_Class().get(index).getClassRole();
                                isWorker = activitydataModel.getList_Class().get(index).isWorker();
                                if (Constants.HEADCOACH == classrole) {
                                    fl_right.setVisibility(View.VISIBLE);
                                } else {
                                    fl_right.setVisibility(View.GONE);
                                }
                                //加载班级
                                classModels.clear();
                                if (activitydataModel.getList_Class() != null && !activitydataModel.getList_Class().isEmpty()) {
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
                                    BtnTag tag = new BtnTag();
                                    tag.role = activitydataModel.getClassRole();
                                    IsFirst_save = activitydataModel.getIsFirst();//获取
                                    tag.date = now;
                                } else {//非学员
                                    BtnTag tag = new BtnTag();
                                    tag.date = now;
                                    tag.role = activitydataModel.getClassRole();
                                }
                                //获取当前日期是否需要复测，活动
                                for (ActCalendarModel model : calendarModels) {
                                    if (DateUtil.getInstance(DateUtil.yyyy_MM_dd).isEq(model.getMonthDate(), saveclassModel.getDates())) {
                                        //如果是复测类型
                                        if (model.getDateType() == 3 || model.getDateType() == Constants.CREATECLASS) {
                                            if (activitydataModel.getClassRole() == Constants.STUDENT) {//是学员
                                                //学员复测的状态：未复测，未审核，已审核
                                                BtnTag tag = new BtnTag();
                                                tag.role = activitydataModel.getClassRole();
                                                if (activitydataModel.getRetestStatus() == 2) {//进行中
                                                    tag.status = FUCEING;//复测日状态
                                                    tag.date = now;
                                                } else if (activitydataModel.getRetestStatus() == 1) {//已过去的复测日

                                                    tag.status = FUCE_FINISH;//复测日状态
                                                    tag.date = saveclassModel.getDates();
                                                } else {//未开始的复测日
                                                    tag.status = FUCE_NOT_START;

                                                }
                                            } else {//非学员
                                                BtnTag tag = new BtnTag();
                                                tag.role = activitydataModel.getClassRole();
                                                if (activitydataModel.getRetestStatus() == 3) {
                                                    tag.status = FUCE_NOT_START;
                                                } else {
                                                    if (activitydataModel.getWeekth() > 0) {
                                                    } else {
                                                    }
                                                    tag.status = activitydataModel.getRetestStatus();
                                                    tag.date = saveclassModel.getDates();

                                                }

                                            }
                                            ll_task.removeAllViews();
                                            if (!activitydataModel.getList_Activity().isEmpty()) {
                                                ll_task.setVisibility(View.VISIBLE);
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
                                        }
                                        if (model.getDateType() == 4) {
                                            ll_task.removeAllViews();
                                        }
                                        //如果是活动类型
                                        if (model.getDateType() == 1 && !activitydataModel.getList_Activity().isEmpty()) {
                                            ll_task.setVisibility(View.VISIBLE);
                                            ll_task.removeAllViews();
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
                        if (!isWorker && classrole != 1) {
                            mChangedDate.setVisibility(View.GONE);
                        }else {
                            mChangedDate.setVisibility(View.VISIBLE);
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
    }


    //日历上活动信息展示
    public class ApiSimulator extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            if (material_calendar != null) {
                for (CalendarDayModel model : calendarModel_reset) {
                    ResetDecorator decorator = new ResetDecorator(model, getActivity());
                    material_calendar.addDecorator(decorator);
                }
                EventDecorator decorator_act = new EventDecorator(Constants.ACTIVITY, calendarModel_act, classrole, getActivity());
                material_calendar.addDecorator(decorator_act);
                EventDecorator decorator_free = new EventDecorator(Constants.FREE, calendarModel_free, classrole, getActivity());
                material_calendar.addDecorator(decorator_free);
            }
        }
    }

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
                ResetDecoratorDot eventDecorator_reset = new ResetDecoratorDot(Color.rgb(135, 199, 67), calendarModel_reset, getActivity());
                material_calendar.addDecorator(eventDecorator_reset);
                EventDecoratorDot eventDecoratorDot_act = new EventDecoratorDot(Color.rgb(237, 118, 108), calendarModel_act, getActivity());
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
            ex.printStackTrace();
        }
        return day;
    }

    // 过滤各种类型的数据表
    private void filterTypesData() {
        calendarModel_reset.clear();
        calendarModel_act.clear();
        calendarModel_free.clear();
        for (ActCalendarModel actCalendarModel : calendarModels) {
            int datetype = actCalendarModel.getDateType();
            String date = actCalendarModel.getMonthDate();
            if (Constants.RESET == datetype || Constants.CREATECLASS == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    CalendarDayModel model = new CalendarDayModel(getCalendarDay(actCalendarModel.getMonthDate()), actCalendarModel.getWeekth());
                    calendarModel_reset.add(model);
                }
            } else if (Constants.ACTIVITY == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    calendarModel_act.add(getCalendarDay(actCalendarModel.getMonthDate()));
                }
            } else if (Constants.FREE == datetype) {
                if (!TextUtils.isEmpty(date)) {
                    calendarModel_free.add(getCalendarDay(actCalendarModel.getMonthDate()));
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
            pull.setRefreshing(true);
            onRefresh();
        }
    }

    @Subscribe
    public void updatefuce(UpdateFuce updateFuce) {
        if (updateFuce.getClassId().equals(classid)) {
            lazyLoad();
            pull.setRefreshing(true);
            onRefresh();
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
    }


}
