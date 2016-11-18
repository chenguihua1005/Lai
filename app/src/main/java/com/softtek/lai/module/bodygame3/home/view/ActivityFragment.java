package com.softtek.lai.module.bodygame3.home.view;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.activity.CreateActivity;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDatePageChangeListener;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.OneDayDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

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
    private CalendarMode mode = CalendarMode.WEEKS;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
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

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());//字体加粗加大
        widget.invalidateDecorators();

        int YY = date.getCalendar().get(Calendar.YEAR);
        int MM = date.getCalendar().get(Calendar.MONTH) + 1;
        int DD = date.getCalendar().get(Calendar.DATE);

        String dateStr = YY + "/" + MM + "/" + DD;

    }

    @Override
    public void onDatePageSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                Intent intent = new Intent(getContext(), CreateActivity.class);
                startActivity(intent);
                break;
        }
    }


}
