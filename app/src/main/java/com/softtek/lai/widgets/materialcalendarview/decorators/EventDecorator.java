package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;


import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;
import com.softtek.lai.widgets.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Context context;
    private int mode;

    public EventDecorator(int mode, Collection<CalendarDay> dates, Context context) {
        this.mode = mode;
        this.dates = new HashSet<>(dates);
        this.context = context;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {


        view.addSpan(new DotSpan(context,mode,4));
    }
}
