package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;
import android.util.TypedValue;

import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;
import com.softtek.lai.widgets.materialcalendarview.spans.DotSpanMy;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class EventDecoratorDot implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Context context;



    public EventDecoratorDot(int color, Collection<CalendarDay> dates, Context context) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        float value= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,context.getResources().getDisplayMetrics());
        view.addSpan(new DotSpanMy(value, color));
    }
}
