package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinner3;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
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
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment1;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.home.event.UpdateFuce;
import com.softtek.lai.module.bodygame3.more.view.UpdateFuceTimeActivity$$ViewInjector;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.OneDayDecorator;
import com.softtek.lai.widgets.materialcalendarview.decorators.SchelDecorator;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.ParseException;
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
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@linkNoClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@linkNoClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InjectLayout(R.layout.fragment_classed)
public class ClassedFragment extends LazyBaseFragment implements OnDateSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.pull)
    MySwipRefreshView refresh;
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title1)
    ArrowSpinner3 tv_title;
    @InjectView(R.id.list_activity)
    RecyclerView list_activity;
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
    private List<CalendarDay> calendarModel_act = new ArrayList<CalendarDay>();
    private List<CalendarDay> calendarModel_create = new ArrayList<CalendarDay>();
    private List<CalendarDay> calendarModel_reset = new ArrayList<CalendarDay>();
    private List<CalendarDay> calendarModel_free = new ArrayList<CalendarDay>();
    private int datetype;
    private String classid;
    private int classrole;
    private List<ClassModel> classModels = new ArrayList<ClassModel>();
    private List<TodayactModel> todayactModels = new ArrayList<TodayactModel>();
    private String dateStr;
    private ActRecyclerAdapter actRecyclerAdapter;
    private ClassModel classModel;
    private DeleteClass deleteClass;
    private static final int LOADCOUNT = 10;
    private int page = 1;
    private int lastVisitableItem;
    private String typeDate;//复测日期

    SimpleDateFormat sf = new SimpleDateFormat("yy-mm-DD");
    String strDate = sf.format(new Date());

    public void setDeleteClass(DeleteClass deleteClass) {
        this.deleteClass = deleteClass;
    }

    public static ClassedFragment getInstance(DeleteClass deleteClass) {
        ClassedFragment fragment = new ClassedFragment();
        fragment.setDeleteClass(deleteClass);
        return fragment;
    }

    public ClassedFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {
        material_calendar.removeDecorators();
        refresh.setRefreshing(true);
        getalldatafirst();
    }

    @Override
    protected void initViews() {
        list_activity.setLayoutManager(new LinearLayoutManagerWrapper(getContext()));//RecyclerView
        ll_fuce.setOnClickListener(this);
        ll_chuDate.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
//刷新
        refresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refresh.setEnabled(true);
                } else {
                    refresh.setEnabled(false);
                }
            }
        });
        refresh.setOnRefreshListener(this);
        list_activity.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = actRecyclerAdapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && count > LOADCOUNT && lastVisitableItem + 1 == count) {
                    //加载更多数据
                    page++;


                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisitableItem = llm.findLastVisibleItemPosition();
            }
        });


        //日历
        material_calendar.setOnDateChangedListener(this);
//        material_calendar.setDatepageChangeListener(this);
        material_calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        final Calendar instance = Calendar.getInstance();
        material_calendar.setSelectedDate(instance.getTime());
        material_calendar.removeDecorators();
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR) - 1, Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) + 1, Calendar.DECEMBER, 31);

        material_calendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)//周模式(WEEKS)或月模式（MONTHS）
                .commit();
//设置日历的长和宽间距
        material_calendar.setTileWidthDp(50);
        material_calendar.setTileHeightDp(38);
        material_calendar.setShowOtherDates(0);

    }

    @Override
    protected void initDatas() {
        material_calendar.removeDecorators();
        actRecyclerAdapter = new ActRecyclerAdapter(getContext(), todayactModels);
        list_activity.setAdapter(actRecyclerAdapter);
        //获取初始数据
        getalldatafirst();
//活动跳转到活动详情
        actRecyclerAdapter.setOnItemClickListener(new ActRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TodayactModel todayactModel = todayactModels.get(position);
                String activityId = todayactModel.getActivityId();
                Intent intent = new Intent(getContext(), ActivitydetailActivity.class);
                intent.putExtra("classrole", classrole);
                intent.putExtra("activityId", activityId);
                startActivity(intent);
            }
        });
        tv_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classid = classModels.get(i).getClassId();
                classrole = classModels.get(i).getClassRole();
                ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classid, new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
                        calendarModels.clear();
                        if (activitydataModelResponseData.getData() != null) {
                            ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                            if (activitydataModel.getList_ActCalendar() != null) {
                                calendarModels.addAll(activitydataModel.getList_ActCalendar());
                                material_calendar.removeDecorators();
                                new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                            }
                            for (int i = 0; i < calendarModels.size(); i++) {
                                if (calendarModels.get(i).getDateType() == Constants.RESET) {
                                    if (calendarModels.get(i).getMonthDate() == strDate) {
                                        typeDate = calendarModels.get(i).getMonthDate();
                                    }

                                }else {
                                    ll_fuce.setVisibility(View.GONE);
                                }
                            }

                            if (Constants.HEADCOACH == (activitydataModel.getClassRole())) {
                                ll_fuce.setBackgroundResource(R.drawable.reset_update);
                                fl_right.setVisibility(View.VISIBLE);
                                fl_right.setEnabled(true);
                                reset_name.setText("复测审核");
                                reset_time.setText("待审核" + activitydataModel.getNum());

                            }
                            if (Constants.COACH == (activitydataModel.getClassRole())) {
                                ll_fuce.setBackgroundResource(R.drawable.reset_update);
                                reset_name.setText("复测审核");
                                fl_right.setEnabled(false);
                                iv_right.setVisibility(View.GONE);
                                if (!activitydataModel.getRetest()) {
                                    reset_time.setText("未审核");
                                } else {
                                    reset_time.setText("已审核");
                                }
                            }
                            if (Constants.ASSISTANT == (activitydataModel.getClassRole())) {
                                ll_fuce.setBackgroundResource(R.drawable.reset_update);
                                reset_name.setText("复测审核");
                                fl_right.setEnabled(false);
                                iv_right.setVisibility(View.GONE);
                                if (!activitydataModel.getRetest()) {
                                    reset_time.setText("未审核");
                                } else {
                                    reset_time.setText("已审核");
                                }
                            }
////                            加载班级
                            classModels.clear();
                            if (activitydataModel.getList_Class() != null) {
                                classModels.addAll(activitydataModel.getList_Class());


                            }
                            //获取今天的活动
                            if (activitydataModel.getList_Activity() != null) {
                                todayactModels.addAll(activitydataModel.getList_Activity());
                                actRecyclerAdapter.notifyDataSetChanged();
                            }
                            for (int n = 0; n < calendarModels.size(); n++) {
                                if (java.sql.Date.valueOf(calendarModels.get(n).getMonthDate()).equals(getNowDate())) {
                                    if (calendarModels.get(n).getDateType() == Constants.ACTIVITY) {
                                        ll_fuce.setVisibility(View.GONE);
                                        list_activity.setVisibility(View.VISIBLE);
                                    } else if (calendarModels.get(n).getDateType() == Constants.RESET) {
                                        if (todayactModels != null && todayactModels.size() > 0) {
                                            list_activity.setVisibility(View.VISIBLE);
                                        } else {
                                            list_activity.setVisibility(View.GONE);
                                        }

                                        ll_fuce.setVisibility(View.VISIBLE);
                                    } else if (calendarModels.get(n).getDateType() == Constants.FREE) {
                                        ll_fuce.setVisibility(View.GONE);
                                        list_activity.setVisibility(View.GONE);
                                    } else if (calendarModels.get(n).getDateType() == Constants.CREATECLASS) {
                                        ll_fuce.setVisibility(View.GONE);
                                        list_activity.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }

                    }
                });
            }
        });


        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    private Date date_now;

    private Date getNowDate() {
        String formatStr = "yyyy-MM-dd";
        DateFormat sdf = new SimpleDateFormat(formatStr);
        String dateStr = sdf.format(new Date());
        try {
            date_now = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("leave", "now time = " + date_now);
        return date_now;
    }

    private void getalldatafirst() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                "",
                new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
                        refresh.setRefreshing(false);
                        calendarModels.clear();
                        if (activitydataModelResponseData.getData() != null) {
                            ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                            if (activitydataModel.getList_ActCalendar() != null) {
                                calendarModels.addAll(activitydataModel.getList_ActCalendar());
                                material_calendar.removeDecorators();
                                new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
                                for (int i = 0; i < calendarModels.size(); i++) {
                                    if (calendarModels.get(i).getDateType() == Constants.RESET) {
                                        if (calendarModels.get(i).getMonthDate() == strDate) {
                                            typeDate = calendarModels.get(i).getMonthDate();
                                        }

                                    }else {
                                        ll_fuce.setVisibility(View.GONE);
                                    }
                                }

                            }

                            ll_fuce.setBackgroundResource(R.drawable.reset_update);
                            fl_right.setVisibility(View.VISIBLE);
                            fl_right.setEnabled(true);
                            reset_name.setText("复测审核");
                            reset_time.setText("待审核" + activitydataModel.getNum());


                            //加载班级
                            classModels.clear();
                            if (activitydataModel.getList_Class() != null) {
                                classModels.addAll(activitydataModel.getList_Class());
                                tv_title.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
                                    @Override
                                    public void convert(ViewHolder holder, ClassModel data, int position) {
                                        TextView tv_class_name = holder.getView(R.id.tv_class_name);
                                        tv_class_name.setText(data.getClassName());
                                        ImageView iv_icon = holder.getView(R.id.iv_icon);
                                        int icon;
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
                                            default:
                                                icon = R.drawable.class_xueyuan;
                                                break;
                                        }
                                        iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                                        TextView tv_role = holder.getView(R.id.tv_role_name);
                                        int role = data.getClassRole();
                                        tv_role.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                                        TextView tv_number = holder.getView(R.id.tv_number);
                                        tv_number.setText(data.getClassCode());
                                    }

                                    @Override
                                    public String getText(int position) {
                                        if (classModels != null && !classModels.isEmpty()) {
                                            classid = classModels.get(position).getClassId();
                                            classrole = classModels.get(position).getClassRole();
                                            return classModels.get(position).getClassName();
                                        } else {
                                            return "尚未开班";
                                        }
                                    }
                                });

                            }
                            //获取今天的活动
                            todayactModels.clear();
                            if (activitydataModel.getList_Activity() != null) {
                                todayactModels.addAll(activitydataModel.getList_Activity());
                                actRecyclerAdapter.notifyDataSetChanged();
                            }

//复测与活动的显示

                            for (int n = 0; n < calendarModels.size(); n++) {
                                if (java.sql.Date.valueOf(calendarModels.get(n).getMonthDate()).equals(getNowDate())) {
                                    if (calendarModels.get(n).getDateType() == Constants.ACTIVITY) {
                                        ll_fuce.setVisibility(View.GONE);
                                        list_activity.setVisibility(View.VISIBLE);
                                    } else if (calendarModels.get(n).getDateType() == Constants.RESET) {
                                        if (todayactModels != null && !todayactModels.isEmpty()) {
                                            list_activity.setVisibility(View.VISIBLE);
                                        } else {
                                            list_activity.setVisibility(View.GONE);
                                        }

                                        ll_fuce.setVisibility(View.VISIBLE);
                                    } else if (calendarModels.get(n).getDateType() == Constants.FREE) {
                                        ll_fuce.setVisibility(View.GONE);
                                        list_activity.setVisibility(View.GONE);
                                    } else if (calendarModels.get(n).getDateType() == Constants.CREATECLASS) {
                                        ll_fuce.setVisibility(View.GONE);
                                        list_activity.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        refresh.setRefreshing(false);
                        super.failure(error);
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
        for (int i = 0; i < calendarModels.size(); i++) {
            String dates = calendarModels.get(i).getMonthDate();
            if (java.sql.Date.valueOf(dateStr).equals(java.sql.Date.valueOf(dates))) {
                if (calendarModels.get(i).getDateType() == Constants.ACTIVITY) {
                    ll_fuce.setVisibility(View.GONE);
                    list_activity.setVisibility(View.VISIBLE);
                }
                if (calendarModels.get(i).getDateType() == Constants.RESET) {
                    typeDate = dates;
                    list_activity.setVisibility(View.VISIBLE);
                    ll_fuce.setVisibility(View.VISIBLE);
                }
                if (calendarModels.get(i).getDateType() == Constants.FREE) {
                    ll_fuce.setVisibility(View.GONE);
                    list_activity.setVisibility(View.GONE);
                }
                if (calendarModels.get(i).getDateType() == Constants.CREATECLASS) {
                    ll_fuce.setVisibility(View.GONE);
                    list_activity.setVisibility(View.GONE);
                }
            }
        }
        todayactModels.clear();
        actRecyclerAdapter.notifyDataSetChanged();
        gettodaydata(dateStr);
    }

    private void gettodaydata(String datestr2) {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).gettoday(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                classid, datestr2, new RequestCallback<ResponseData<TodaysModel>>() {
                    @Override
                    public void success(ResponseData<TodaysModel> todaysModelResponseData, Response response) {
                        todayactModels.clear();
                        if (todaysModelResponseData.getData() != null) {
                            TodaysModel model = todaysModelResponseData.getData();
                            if (model.getList_Activity() != null && !model.getList_Activity().isEmpty()) {
                                todayactModels.addAll(model.getList_Activity());
                                actRecyclerAdapter.notifyDataSetChanged();
                            }
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
            case R.id.ll_chuDate:
                Intent chuDate=new Intent(getContext(), InitAuditListActivity.class);
                chuDate.putExtra("classId",classid);
                startActivity(chuDate);
                break;
            case R.id.ll_left:
                getActivity().finish();
                break;
            case R.id.ll_fuce://复测审核
                Log.e("classde",classid+typeDate);
                Intent fuce = new Intent(getContext(), FcAuditListActivity.class);
                fuce.putExtra("classId", classid);
                fuce.putExtra("typeDate", typeDate);
                startActivity(fuce);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (resultCode == 0) {
                progressDialogs.show();
                getalldatafirst();
            }
        }

    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(true);
        getalldatafirst();
    }

    private SchelDecorator decorator;
    private SchelDecorator decorator_act;
    private SchelDecorator decorator_free;
    private SchelDecorator decorator_create;

    //日历上活动信息展示
    public class ApiSimulator extends AsyncTask<List<ActscalendarModel>, Void, Void> {


        @Override
        protected Void doInBackground(List<ActscalendarModel>... lists) {

            for (int i = 0; i < calendarModels.size(); i++) {
                datetype = calendarModels.get(i).getDateType();
                if (Constants.ACTIVITY == datetype) {
                    if (!TextUtils.isEmpty(calendarModels.get(i).getMonthDate())) {
                        calendarModel_act.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                    }
                }
                if (Constants.RESET == datetype) {
                    if (!TextUtils.isEmpty(calendarModels.get(i).getMonthDate())) {

                        calendarModel_reset.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                    }
                }
                if (Constants.FREE == datetype) {
                    if (!TextUtils.isEmpty(calendarModels.get(i).getMonthDate())) {

                        calendarModel_free.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
                    }
                }
                if (Constants.CREATECLASS == datetype) {
                    if (!TextUtils.isEmpty(calendarModels.get(i).getMonthDate())) {

                        calendarModel_create.add(getCalendarDay(calendarModels.get(i).getMonthDate()));
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
                decorator = new SchelDecorator(Constants.RESET, calendarModel_reset, getActivity());
                material_calendar.addDecorator(decorator);
                decorator_act = new SchelDecorator(Constants.ACTIVITY, calendarModel_act, getActivity());
                material_calendar.addDecorator(decorator_act);
                decorator_create = new SchelDecorator(Constants.CREATECLASS, calendarModel_create, getActivity());
                material_calendar.addDecorator(decorator_create);
                decorator_free = new SchelDecorator(Constants.FREE, calendarModel_free, getActivity());
                material_calendar.addDecorator(decorator_free);

            }
        }

    }

    private CalendarDay getCalendarDay(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        CalendarDay day = null;
//        ArrayList<CalendarDay> dates = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            calendar.setTime(date);
            day = CalendarDay.from(calendar);
//            dates.add(day);
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
                //更新班级姓名
                ClassModel model = new ClassModel();
                model.setClassId(clazz.getModel().getClassId());
                model.setClassCode(clazz.getModel().getClassCode());
                model.setClassName(clazz.getModel().getClassName());
                model.setClassRole(clazz.getModel().getClassRole());
                this.classModel.setClassName(model.getClassName());
                tv_title.setText(model.getClassName());
                tv_title.getAdapter().notifyDataSetChanged();
            } else if (clazz.getStatus() == 1) {
                //添加新班级
                ClassModel model = new ClassModel();
                model.setClassId(clazz.getModel().getClassId());
                model.setClassCode(clazz.getModel().getClassCode());
                model.setClassName(clazz.getModel().getClassName());
                model.setClassRole(clazz.getModel().getClassRole());
                this.classModels.add(model);
                tv_title.getAdapter().notifyDataSetChanged();
            } else if (clazz.getStatus() == 2) {
                //删除班级
                for (ClassModel model : classModels) {
                    if (model.getClassCode().equals(clazz.getModel().getClassCode())) {
                        this.classModels.remove(model);
                        tv_title.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }

                if (classModels.isEmpty()) {
                    if (deleteClass != null) {
                        deleteClass.deletClass();
                    }
                } else {
                    tv_title.setSelected(0);
                    this.classModel = classModels.get(0);

                }

            }
        }

    }

    public interface DeleteClass {
        void deletClass();
    }

    @Subscribe
    public void updatefuce(UpdateFuce updateFuce) {
        Log.i("修改复测日。。。。。。。。。。。。。。。。。。", "修改复测日");
        if (classid.equals(updateFuce.getClassId())) {
            for (int i = 0; i < updateFuce.getFuceDate().size(); i++) {
                calendarModel_reset.add(getCalendarDay(updateFuce.getFuceDate().get(i).getMeasureDate()));
            }
            material_calendar.removeDecorator(decorator);
            decorator = new SchelDecorator(Constants.RESET, calendarModel_reset, getActivity());
            material_calendar.addDecorator(decorator);

        }


    }


}
