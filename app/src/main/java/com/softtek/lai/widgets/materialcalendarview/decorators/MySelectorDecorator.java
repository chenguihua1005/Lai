package com.softtek.lai.widgets.materialcalendarview.decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.softtek.lai.R;
import com.softtek.lai.widgets.materialcalendarview.CalendarDay;
import com.softtek.lai.widgets.materialcalendarview.DayViewDecorator;
import com.softtek.lai.widgets.materialcalendarview.DayViewFacade;


/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;

    private CalendarDay date;

    public MySelectorDecorator(Activity context) {
        date = CalendarDay.today();
        drawable = context.getResources().getDrawable(R.drawable.circle_shape);
//        drawable = context.getResources().getDrawable(R.drawable.circle_shape);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
//        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
