package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;


import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;
import com.softtek.lai.widgets.materialcalendarview.spans.Textspan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by shelly.xu on 10/13/2016.
 */
public class SchelDecorator implements DayViewDecorator {
    private int mode;
    private HashSet<CalendarDay> dates;
    private Context context;
    private int role;

    public SchelDecorator(int mode, Collection<CalendarDay> dates,int role, Context context) {
        this.mode = mode;
        this.role=role;
        this.dates = new HashSet<>(dates);
        this.context = context;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new Textspan(context,mode,4,role));

    }
}
