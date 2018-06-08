package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;

import com.softtek.lai.module.bodygame3.activity.model.CalendarDayModel;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;
import com.softtek.lai.widgets.materialcalendarview.spans.Resetspan;

/**
 * Created by shelly.xu on 10/13/2016.
 */
public class ResetDecorator implements DayViewDecorator {
    private CalendarDayModel day;
    private Context context;

    public ResetDecorator(CalendarDayModel day,Context context) {

        this.day = day;
        this.context = context;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return this.day.getDay().equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new Resetspan(context,day.getWeekTh()));
    }
}
