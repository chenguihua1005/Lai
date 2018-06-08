package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;

import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by shelly.xu on 9/27/2016.
 */
public class SchedulEventDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;

    public SchedulEventDecorator(Collection<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.context = context;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.setBackgroundDrawable(context.getResources().getText(R.string.acode));

    }
}
