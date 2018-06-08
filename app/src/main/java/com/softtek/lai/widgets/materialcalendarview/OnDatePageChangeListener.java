package com.softtek.lai.widgets.materialcalendarview;

import android.support.annotation.NonNull;

/**
 * The callback used to indicate a date has been selected or deselected
 */
public interface OnDatePageChangeListener {

    /**
     * Called when a user clicks on a day.
     * There is no logic to prevent multiple calls for the same date and state.
     *
     * @param date     the date that was selected or unselected
     */
    void onDatePageSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date);
}
