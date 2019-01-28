package com.applandeo.materialcalendarview.builders;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.utils.CalendarProperties;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Mateusz Kornakiewicz on 12.10.2017.
 */
public class DatePickerBuilder {

    private Context mContext;
    private CalendarProperties mCalendarProperties;

    public DatePickerBuilder(Context context, OnSelectDateListener onSelectDateListener) {
        mContext = context;
        mCalendarProperties = new CalendarProperties();
        mCalendarProperties.setCalendarType(CalendarView.ONE_DAY_PICKER);
        mCalendarProperties.setOnSelectDateListener(onSelectDateListener);
    }

    public DatePicker build() {
        return new DatePicker(mContext, mCalendarProperties);
    }

    public DatePickerBuilder pickerType(int calendarType) {
        mCalendarProperties.setCalendarType(calendarType);
        return this;
    }

    public DatePickerBuilder date(Calendar calendar) {
        mCalendarProperties.setCalendar(calendar);
        return this;
    }

    public DatePickerBuilder selectedDays(List<Calendar> selectedDays) {
        mCalendarProperties.setSelectedDays(selectedDays);
        return this;
    }

    public DatePickerBuilder disabledDays(List<Calendar> disabledDays) {
        mCalendarProperties.setDisabledDays(disabledDays);
        return this;
    }

    public DatePickerBuilder minimumDate(Calendar calendar) {
        mCalendarProperties.setMinimumDate(calendar);
        return this;
    }

    public DatePickerBuilder maximumDate(Calendar calendar) {
        mCalendarProperties.setMaximumDate(calendar);
        return this;
    }

    public DatePickerBuilder pagesColor(@ColorRes int color) {
        mCalendarProperties.setPagesColor(color);
        return this;
    }

    public DatePickerBuilder dialogButtonsTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setDialogButtonTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder headerVisibility(int visibility) {
        mCalendarProperties.setHeaderVisibility(visibility);
        return this;
    }

    public DatePickerBuilder headerBackgroundColor(@ColorRes int color) {
        mCalendarProperties.setHeaderBackgroundColor(color);
        return this;
    }

    public DatePickerBuilder previousButtonSrc(@DrawableRes int drawable) {
        mCalendarProperties.setPreviousButtonSrc(drawable);
        return this;
    }

    public DatePickerBuilder previousPageChangeListener(OnCalendarPageChangeListener listener) {
        mCalendarProperties.setOnPreviousPageChangeListener(listener);
        return this;
    }

    public DatePickerBuilder forwardButtonSrc(@DrawableRes int drawable) {
        mCalendarProperties.setForwardButtonSrc(drawable);
        return this;
    }

    public DatePickerBuilder forwardPageChangeListener(OnCalendarPageChangeListener listener) {
        mCalendarProperties.setOnForwardPageChangeListener(listener);
        return this;
    }

    public DatePickerBuilder monthNames(@ArrayRes int arrayRes) {
        mCalendarProperties.setMonthNames(arrayRes);
        return this;
    }

    public DatePickerBuilder monthNameTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setMonthNameTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder dayNames(@ArrayRes int arrayRes) {
        mCalendarProperties.setDayNames(arrayRes);
        return this;
    }

    public DatePickerBuilder dayNamesBarColor(@ColorRes int color) {
        mCalendarProperties.setDayNamesBarColor(color);
        return this;
    }

    public DatePickerBuilder dayNameTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setDayNameTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder currentMonthDayBackgroundSrc(@DrawableRes int drawable) {
        mCalendarProperties.setCurrentMonthDayBackgroundSrc(drawable);
        return this;
    }

    public DatePickerBuilder currentMonthDayTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setCurrentMonthDayTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder anotherMonthDayBackgroundSrc(@DrawableRes int drawable) {
        mCalendarProperties.setAnotherMonthDayBackgroundSrc(drawable);
        return this;
    }

    public DatePickerBuilder anotherMonthDayTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setAnotherMonthDayTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder todayBackgroundSrc(@DrawableRes int drawable) {
        mCalendarProperties.setTodayBackgroundSrc(drawable);
        return this;
    }

    public DatePickerBuilder todayTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setTodayTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder selectedDayBackgroundSrc(@DrawableRes int drawable) {
        mCalendarProperties.setSelectedDayBackgroundSrc(drawable);
        return this;
    }

    public DatePickerBuilder selectedDayTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setSelectedDayTextAppearance(textAppearance);
        return this;
    }

    public DatePickerBuilder disabledDayBackgroundSrc(@DrawableRes int drawable) {
        mCalendarProperties.setDisabledDayBackgroundSrc(drawable);
        return this;
    }

    public DatePickerBuilder disabledDayTextAppearance(@StyleRes int textAppearance) {
        mCalendarProperties.setDisabledDayTextAppearance(textAppearance);
        return this;
    }
}
