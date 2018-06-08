package com.softtek.lai.widgets.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.TextView;


import com.softtek.lai.widgets.materialcalendarview.format.WeekDayFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.softtek.lai.R.id.view;

/**
 * Display a day of the week
 */
@Experimental
@SuppressLint("ViewConstructor")
class WeekDayView extends TextView {

    private WeekDayFormatter formatter = WeekDayFormatter.DEFAULT;
    private int dayOfWeek;

    public WeekDayView(Context context, int dayOfWeek) {
        super(context);

        setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }

        setDayOfWeek(dayOfWeek);
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        this.formatter = formatter == null ? WeekDayFormatter.DEFAULT : formatter;
        setDayOfWeek(dayOfWeek);
    }
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    public void setDayOfWeek(int dayOfWeek) {

        this.dayOfWeek = dayOfWeek;
//        setText(formatter.format(dayOfWeek));
//        switch (dayOfWeek){
//            case 1:
//                setText("一");
//                break;
//            case 2:
//                setText("二");
//                break;
//            case 3:
//                setText("三");
//                break;
//            case 4:
//                setText("四");
//                break;
//            case 5:
//                setText("五");
//                break;
//            case 6:
//                setText("六");
//                break;
//            case 7:
//                setText("日");
//                break;
//        }


        switch (dayOfWeek){
            case 1:
                setText("日");
                break;
            case 2:
                setText("一");
                break;
            case 3:
                setText("二");
                break;
            case 4:
                setText("三");
                break;
            case 5:
                setText("四");
                break;
            case 6:
                setText("五");
                break;
            case 7:
                setText("六");
                break;
        }
    }

    public void setDayOfWeek(Calendar calendar) {
        setDayOfWeek(CalendarUtils.getDayOfWeek(calendar));
    }
}
