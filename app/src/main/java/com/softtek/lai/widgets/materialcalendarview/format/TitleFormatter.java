package com.softtek.lai.widgets.materialcalendarview.format;


import com.softtek.lai.widgets.materialcalendarview.CalendarDay;

public interface TitleFormatter {

    /**
     * Converts the supplied day to a suitable month/year title
     *
     * @param day the day containing relevant month and year information
     * @return a label to display for the given month/year
     */
    CharSequence format(CalendarDay day);
}
