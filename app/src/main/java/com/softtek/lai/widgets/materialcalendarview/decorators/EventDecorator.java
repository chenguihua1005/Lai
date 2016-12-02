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

    public EventDecorator(int color,int mode, Collection<CalendarDay> dates, Context context) {
        this.color = color;
        this.mode=mode;
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        Iterator<CalendarDay> it = dates.iterator();
//        while(it.hasNext()){
//            CalendarDay day = it.next();
//            if (CalendarDay.today().equals(day)){
//                  view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_my_selector));
//            }else {
//                view.addSpan(new DotSpan(5, color));
//            }
//        }
//        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_my_selector));

        view.addSpan(new DotSpan(3, color,mode));
    }
}
