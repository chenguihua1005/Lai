package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;


import com.softtek.lai.R;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class TodayEventDecorator implements DayViewDecorator {

    private HashSet<CalendarDay> dates;
    private Context context;

    public TodayEventDecorator(Collection<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape));
    }
}
