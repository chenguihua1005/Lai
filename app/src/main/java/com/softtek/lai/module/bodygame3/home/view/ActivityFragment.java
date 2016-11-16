package com.softtek.lai.module.bodygame3.home.view;


import android.os.Handler;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;
import com.softtek.lai.widgets.materialcalendarview.OnDatePageChangeListener;
import com.softtek.lai.widgets.materialcalendarview.OnDateSelectedListener;
import com.softtek.lai.widgets.materialcalendarview.decorators.OneDayDecorator;

import java.util.Calendar;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

import static android.graphics.Color.parseColor;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment implements OnDateSelectedListener, OnDatePageChangeListener{
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
}
