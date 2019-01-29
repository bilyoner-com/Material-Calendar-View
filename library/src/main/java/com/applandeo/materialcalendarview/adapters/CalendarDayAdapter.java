package com.applandeo.materialcalendarview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.model.SelectedDay;
import com.applandeo.materialcalendarview.utils.AppearanceUtils;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.applandeo.materialcalendarview.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class is responsible for loading a one day cell.
 * <p>
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */
class CalendarDayAdapter extends ArrayAdapter<Date> {

    private LayoutInflater mLayoutInflater;
    private CalendarPageAdapter mCalendarPageAdapter;
    private int mPageMonth;
    private Calendar mToday = DateUtils.getCalendar();

    private CalendarProperties mCalendarProperties;

    CalendarDayAdapter(Context context,
                       CalendarPageAdapter calendarPageAdapter,
                       CalendarProperties calendarProperties,
                       ArrayList<Date> dates,
                       int pageMonth) {

        super(context, calendarProperties.getDayItemLayoutResource(), dates);
        mLayoutInflater = LayoutInflater.from(context);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getDayItemLayoutResource(), parent, false);
        }

        TextView dayLabel = view.findViewById(R.id.dayLabel);
        ImageView dayIcon = view.findViewById(R.id.dayIcon);

        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));

        // Loading an image of the event
        if (dayIcon != null) {
            loadIcon(dayIcon, day);
        }

        setLabelColors(dayLabel, day);

        dayLabel.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));

        return view;
    }

    private void setLabelColors(TextView dayLabel, Calendar day) {

        // Setting disabled days color
        if (isDisabledDay(day)) {
            AppearanceUtils.setDisabledDayTextAppearance(dayLabel, mCalendarProperties);
            return;
        }

        // Setting selected days color
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(dayLabel));

            AppearanceUtils.setSelectedDayTextAppearance(dayLabel, mCalendarProperties);
            return;
        }

        // Setting today color
        if (isToday(day)) {
            AppearanceUtils.setTodayTextAppearance(dayLabel, mCalendarProperties);
            return;
        }

        // Setting current month day color
        if (isCurrentMonthDay(day)) {
            AppearanceUtils.setCurrentMonthDayTextAppearance(dayLabel, mCalendarProperties);
            return;
        }

        // Setting another month day color
        AppearanceUtils.setAnotherMonthDayTextAppearance(dayLabel, mCalendarProperties);
    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC
                && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isDisabledDay(Calendar day) {
        return mCalendarProperties.getDisabledDays().contains(day);
    }

    private boolean isToday(Calendar day) {
        return DateUtils.isDateEquals(day, mToday);
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth
                && !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private void loadIcon(ImageView dayIcon, Calendar day) {
        if (mCalendarProperties.getEventDays() == null || !mCalendarProperties.getEventsEnabled()) {
            dayIcon.setVisibility(View.GONE);
            return;
        }

        Stream.of(mCalendarProperties.getEventDays()).filter(eventDate ->
                eventDate.getCalendar().equals(day)).findFirst().executeIfPresent(eventDay -> {

            ImageUtils.loadImage(dayIcon, eventDay.getImageDrawable());

            // If a day doesn't belong to current month then image is transparent
            if (!isCurrentMonthDay(day) || !isDisabledDay(day)) {
                dayIcon.setAlpha(0.12f);
            }
        });
    }
}
