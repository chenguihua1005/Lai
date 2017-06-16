package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.content.Context;
import android.util.TypedValue;

import com.softtek.lai.module.bodygame3.activity.model.CalendarDayModel;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;
import com.softtek.lai.widgets.materialcalendarview.spans.DotSpanMy;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class ResetDecoratorDot implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDayModel> dates;
    private Context context;



    public ResetDecoratorDot(int color, Collection<CalendarDayModel> dates, Context context) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for (CalendarDayModel model:dates){
            if(model.getDay().equals(day)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {

        float value= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,context.getResources().getDisplayMetrics());
        view.addSpan(new DotSpanMy(value, color));
    }
}
