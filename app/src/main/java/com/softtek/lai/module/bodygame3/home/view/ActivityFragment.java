package com.softtek.lai.module.bodygame3.home.view;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.softtek.lai.module.bodygame3.activity.adapter.ActRecyclerAdapter;
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
import com.softtek.lai.module.bodygame3.activity.view.WriteFCActivity;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.home.event.UpdateFuce;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.EventDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.OneDayDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.SchelDecorator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
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
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title2)
    ArrowSpinner3 tv_title;
    @InjectView(R.id.list_activity)
    RecyclerView list_activity;
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

    private CalendarMode mode = CalendarMode.WEEKS;
    private List<ActCalendarModel> calendarModels = new ArrayList<>();
    private List<CalendarDay> calendarModel_act = new ArrayList<>();
    private List<CalendarDay> calendarModel_create = new ArrayList<>();
    private List<CalendarDay> calendarModel_reset = new ArrayList<>();
    private List<CalendarDay> calendarModel_free = new ArrayList<>();
    private String classid = "";
    private List<ClassModel> classModels = new ArrayList<>();
    private List<TodayactModel> todayactModels = new ArrayList<>();
    private String dateStr;
    private int classrole;
    private ActRecyclerAdapter actRecyclerAdapter;
    private ClassModel classModel;
    private static final int LOADCOUNT = 10;
    private int page = 1;

    public ActivityFragment() {

    }

    @Override
    protected void lazyLoad() {
        pull.setRefreshing(true);
        onRefresh();
    }

    @Override
    protected void initViews() {
        //显示创建活动按钮只要是Sp顾问
        if (String.valueOf(Constants.SP).equals(UserInfoModel.getInstance().getUser().getUserrole())) {
            fl_right.setVisibility(View.VISIBLE);
        } else {
            fl_right.setVisibility(View.GONE);
        }
        list_activity.setLayoutManager(new LinearLayoutManagerWrapper(getContext()));//RecyclerView
        ll_fuce.setOnClickListener(this);
        ll_chuDate.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    pull.setEnabled(true);
                } else {
                    pull.setEnabled(false);
                }
            }
        });
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        pull.setOnRefreshListener(this);
//        list_activity.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                int count = actRecyclerAdapter.getItemCount();
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && count > LOADCOUNT && lastVisitableItem + 1 == count) {
//                    //加载更多数据
//                    page++;
//
//                }
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
//                lastVisitableItem = llm.findLastVisibleItemPosition();
//            }
//        });
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
        material_calendar.setSelectionColor(getResources().getColor(R.color.yellow));
//        material_calendar.setTileHeight(90);
        material_calendar.setTileHeightDp(55);
        material_calendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)//周模式(WEEKS)或月模式（MONTHS）
                .commit();

        material_calendar.setShowOtherDates(0);
//        material_calendar.invalidateDecorators();
    }

    @Override
    protected void initDatas() {
        actRecyclerAdapter = new ActRecyclerAdapter(getContext(), todayactModels);
        list_activity.setAdapter(actRecyclerAdapter);
        //活动跳转到活动详情
        actRecyclerAdapter.setOnItemClickListener(new ActRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TodayactModel todayactModel = todayactModels.get(position);
                String activityId = todayactModel.getActivityId();
                Intent intent = new Intent(getContext(), ActivitydetailActivity.class);
                intent.putExtra("classrole", classrole);
                intent.putExtra("activityId", activityId);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
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

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        int year = date.getCalendar().get(Calendar.YEAR);
        int month = date.getCalendar().get(Calendar.MONTH) + 1;
        int day = date.getCalendar().get(Calendar.DATE);
        //根据点击的日期查询该天的活动列表
        dateStr = year + "-" + month + "-" + day;
        ll_fuce.setVisibility(View.GONE);
        todayactModels.clear();
        actRecyclerAdapter.notifyDataSetChanged();
        gettodaydata(dateStr);


    }

    private void gettodaydata(final String datestr) {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).gettoday(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
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
                                    ll_fuce.setBackgroundResource(R.drawable.reset_back);
                                    reset_name.setText("复测录入");
                                    switch (resetstatus) {
                                        case 1://已过去的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            if (model.getIsRetest() == 1) {
                                                ll_fuce.setEnabled(false);
                                                reset_time.setText("未复测");
                                            } else if (model.getIsRetest() == 2) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("未审核");
                                            } else if (model.getIsRetest() == 3) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("已审核");
                                                tag.status = FUCE_FINISH;
                                            }
                                            break;
                                        case 2://进行中的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(true);
                                            if (model.getIsRetest() == 1) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("未复测");
                                                tag.resetstatus = model.getRetestStatus();
                                            } else if (model.getIsRetest() == 2) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("未审核");
                                                tag.resetstatus = model.getRetestStatus();
                                            } else if (model.getIsRetest() == 3) {
                                                ll_fuce.setEnabled(true);
                                                reset_time.setText("已审核");
                                                tag.resetstatus = model.getRetestStatus();
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
                                    ll_fuce.setBackgroundResource(R.drawable.reset_update);
                                    reset_name.setText("复测审核");
                                    switch (resetstatus) {
                                        case 1://已过去的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(true);
                                            reset_time.setText("待审核" + model.getNum());
                                            tag.status = FUCE_FINISH;
                                            break;
                                        case 2://进行中的复测日
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            ll_fuce.setEnabled(true);
                                            reset_time.setText("待审核" + model.getNum());
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
                                }
                                ll_fuce.setTag(tag);
                                if (model.getList_Activity() != null && !model.getList_Activity().isEmpty()) {
                                    todayactModels.addAll(model.getList_Activity());
                                    actRecyclerAdapter.notifyDataSetChanged();
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
            case R.id.ll_chuDate: {
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
//                    startActivity(chuDate);
                } else {
                    com.github.snowdream.android.util.Log.i("初始数据的tag=" + tag.toString());
                    Intent chuDate = new Intent(getContext(), InitAuditListActivity.class);
                    chuDate.putExtra("typeDate", tag.date);
                    chuDate.putExtra("classId", classid);
                    startActivity(chuDate);
                }
            }
            break;
            case R.id.ll_left:
                getActivity().finish();
                break;
            case R.id.ll_fuce://复测审核
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
                    startActivity(fuce);
                } else {
                    com.github.snowdream.android.util.Log.i("复测按钮的tag=" + tag.toString());
                    Intent fuce = new Intent(getContext(), FcAuditListActivity.class);
                    String str = classid + tag.status + tag.date;
                    fuce.putExtra("classId", classid);
                    fuce.putExtra("resetdatestatus", tag.status);//复测日状态  1:已过去 2：进行中 3：未开始
                    fuce.putExtra("typeDate", tag.date);
                    startActivity(fuce);
                }
            }
            break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                com.github.snowdream.android.util.Log.i("活动更新。。。。。。。。。。。。。。");
                lazyLoad();
            } else if (requestCode == 1) {
                com.github.snowdream.android.util.Log.i("活动更新啦啦啦啦。。。。。。。。。。。。。。");
                todayactModels.remove(data.getStringExtra("activityId"));
                actRecyclerAdapter.notifyDataSetChanged();
                lazyLoad();

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
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(), classid, new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> data, Response response) {
                        try {
                            pull.setRefreshing(false);
                            if (data.getData() != null) {
                                ActivitydataModel activitydataModel = data.getData();
                                classrole = activitydataModel.getClassRole();
                                //加载班级
                                if (activitydataModel.getList_Class() != null && !activitydataModel.getList_Class().isEmpty()) {
                                    ll_chuDate.setVisibility(View.VISIBLE);
                                    classModels.clear();
                                    classModels.addAll(activitydataModel.getList_Class());
                                    tv_title.getAdapter().notifyDataSetChanged();
                                    if (TextUtils.isEmpty(classid)) {
                                        classid = classModels.get(0).getClassId();
                                        classrole = classModels.get(0).getClassRole();
                                        tv_title.setSelected(0);
                                    } else {
                                        for (int i = 0, j = classModels.size(); i < j; i++) {
                                            ClassModel model = classModels.get(i);
                                            if (model.getClassId().equals(classid)) {
                                                tv_title.setSelected(i);
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    //如果班级是空
                                    return;
                                }
                                //标记日历中的活动/复测等标记
                                if (activitydataModel.getList_ActCalendar() != null && !activitydataModel.getList_ActCalendar().isEmpty()) {
                                    calendarModels.clear();
                                    calendarModels.addAll(activitydataModel.getList_ActCalendar());
                                    material_calendar.removeDecorators();
//                                    material_calendar.invalidateDecorators();
                                    new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                                }
                                //判断是否显示初始数据录入根据此用户在该班级的角色
                                if (activitydataModel.getClassRole() == Constants.STUDENT) {//是学员
                                    tv_initData_Name.setText("初始数据录入");
                                    BtnTag tag = new BtnTag();
                                    tag.role = activitydataModel.getClassRole();
                                    if (activitydataModel.getIsFirst() == 1) {
                                        tv_chustatus.setText("未录入");
                                    } else if (activitydataModel.getIsFirst() == 2) {
                                        tv_chustatus.setText("未审核");
                                    } else if (activitydataModel.getIsFirst() == 3) {
                                        tv_chustatus.setText("已审核");
                                    }
                                    tag.isfirst = activitydataModel.getIsFirst();//初始数据录入状态： 1：未录入 2：未审核 3：已审核
                                    tag.date = now;
                                    ll_chuDate.setTag(tag);
                                } else {//非学员
                                    tv_initData_Name.setText("初始数据审核");
                                    BtnTag tag = new BtnTag();
                                    tag.role = activitydataModel.getClassRole();
                                    ll_chuDate.setTag(tag);
                                }
                                //获取当前日期是否需要复测，活动
                                for (ActCalendarModel model : calendarModels) {
                                    if (DateUtil.getInstance(DateUtil.yyyy_MM_dd).isEq(model.getMonthDate(), now)) {
                                        //如果是复测类型
                                        if (model.getDateType() == 3 || model.getDateType() == Constants.CREATECLASS) {
                                            ll_fuce.setVisibility(View.VISIBLE);
                                            if (activitydataModel.getClassRole() == Constants.STUDENT) {//是学员
                                                reset_name.setText("复测录入");
                                                ll_fuce.setBackgroundResource(R.drawable.reset_back);//复测录入背景图
                                                //学员复测的状态：未复测，未审核，已审核
                                                BtnTag tag = new BtnTag();
                                                if (activitydataModel.getRetestStatus() == 2) {//进行中
                                                    tag.role = activitydataModel.getClassRole();
                                                    tag.status = FUCEING;
                                                    tag.date = now;
                                                    if (activitydataModel.getIsRetest() == 1) {
                                                        reset_time.setText("未复测");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    } else if (activitydataModel.getIsRetest() == 2) {
                                                        reset_time.setText("未审核");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    } else if (activitydataModel.getIsRetest() == 3) {
                                                        reset_time.setText("已审核");
                                                        tag.resetstatus = activitydataModel.getIsRetest();
                                                    }
                                                }
                                                ll_fuce.setTag(tag);
                                            } else {//非学员
                                                ll_fuce.setBackgroundResource(R.drawable.reset_update);//复测审核背景图
                                                reset_name.setText("复测审核");
                                                reset_time.setText("待审核" + activitydataModel.getNum());
                                                BtnTag tag = new BtnTag();
                                                tag.role = activitydataModel.getClassRole();
                                                tag.status = FUCEING;
                                                tag.date = now;
                                                ll_fuce.setTag(tag);
                                            }

                                            if (activitydataModel != null && !activitydataModel.getList_Activity().isEmpty()) {
                                                //有活动
                                                todayactModels.clear();
                                                todayactModels.addAll(activitydataModel.getList_Activity());
                                                actRecyclerAdapter.notifyDataSetChanged();
                                            }
                                        }
                                        //如果是活动类型
                                        if (model.getDateType() == 1 &&
                                                activitydataModel != null && !activitydataModel.getList_Activity().isEmpty()) {
                                            //有活动
                                            todayactModels.clear();
                                            todayactModels.addAll(activitydataModel.getList_Activity());
                                            actRecyclerAdapter.notifyDataSetChanged();
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
    }

    //日历上活动信息展示
    public class ApiSimulator extends AsyncTask<List<ActscalendarModel>, Void, Void> {

        @Override
        protected Void doInBackground(List<ActscalendarModel>... lists) {

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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            if (material_calendar != null) {
                material_calendar.removeDecorators();
//                material_calendar.removeDecorator(decorator);
//                material_calendar.removeDecorator(decorator_act);
//                material_calendar.removeDecorator(decorator_create);
//                material_calendar.removeDecorator(decorator_free);
//                material_calendar.invalidateDecorators();
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

    //更新班级
    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (classModel != null) {
            if (clazz.getStatus() == 0) {
                com.github.snowdream.android.util.Log.i("更新班级名。。。。。。。。。。。。。");
                //更新班级姓名
                lazyLoad();
//                ClassModel model = new ClassModel();
//                model.setClassId(clazz.getModel().getClassId());
//                model.setClassCode(clazz.getModel().getClassCode());
//                model.setClassName(clazz.getModel().getClassName());
//                model.setClassRole(clazz.getModel().getClassRole());
//                this.classModel.setClassName(model.getClassName());
//                tv_title.setText(model.getClassName());
//                tv_title.getAdapter().notifyDataSetChanged();
            } else if (clazz.getStatus() == 1) {
                //添加新班级
                com.github.snowdream.android.util.Log.i("添加新班级。。。。。。。。。。。。。");
                lazyLoad();
//                ClassModel model = new ClassModel();
//                model.setClassId(clazz.getModel().getClassId());
//                model.setClassCode(clazz.getModel().getClassCode());
//                model.setClassName(clazz.getModel().getClassName());
//                model.setClassRole(clazz.getModel().getClassRole());
//                this.classModels.add(model);
//                tv_title.getAdapter().notifyDataSetChanged();
            } else if (clazz.getStatus() == 2) {
                com.github.snowdream.android.util.Log.i("删除班级。。。。。。。。。。。。。。。。");
                //删除班级
                lazyLoad();
//                for (ClassModel model : classModels) {
//                    if (model.getClassCode().equals(clazz.getModel().getClassCode())) {
//                        this.classModels.remove(model);
//                        tv_title.getAdapter().notifyDataSetChanged();
//                        break;
//                    }
//                }

                if (classModels.isEmpty()) {
                    lazyLoad();
                } else {
                    tv_title.setSelected(0);
                    this.classModel = classModels.get(0);

                }

            }
        }

    }

    @Subscribe
    public void updatefuce(UpdateFuce updateFuce) {
        Log.i("修改复测日。。。。。。。。。。。。。。。。。。", "修改复测日");
        if (classid.equals(updateFuce.getClassId())) {
            for (int i = 0; i < updateFuce.getFuceDate().size(); i++) {
                calendarModel_reset.add(getCalendarDay(updateFuce.getFuceDate().get(i).getMeasureDate()));
            }
            material_calendar.removeDecorator(decorator);
            decorator = new EventDecorator(Constants.RESET, calendarModel_reset, classrole, getActivity());
            material_calendar.addDecorator(decorator);

        }


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
