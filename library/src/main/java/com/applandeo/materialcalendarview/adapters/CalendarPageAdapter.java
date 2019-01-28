package com.applandeo.materialcalendarview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.extensions.CalendarGridView;
import com.applandeo.materialcalendarview.listeners.DayRowClickListener;
import com.applandeo.materialcalendarview.model.SelectedDay;
import com.applandeo.materialcalendarview.utils.CalendarProperties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for loading a calendar page content.
 * <p>
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */
public class CalendarPageAdapter extends PagerAdapter {

    private Context mContext;
    private CalendarGridView mCalendarGridView;

    private CalendarProperties mCalendarProperties;

    private int mPageMonth;

    public CalendarPageAdapter(Context context, CalendarProperties calendarProperties) {
        mContext = context;
        mCalendarProperties = calendarProperties;
        informDatePicker();
    }

    @Override
    public int getCount() {
        return CalendarProperties.CALENDAR_SIZE;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mCalendarGridView = (CalendarGridView) inflater
                .inflate(R.layout.calendar_view_grid, container, false);

        loadMonth(position);

        mCalendarGridView.setOnItemClickListener(
                new DayRowClickListener(this, mCalendarProperties, mPageMonth));

        container.addView(mCalendarGridView);

        return mCalendarGridView;
    }

    public void addOrRemoveSelectedDay(SelectedDay selectedDay) {
        if (!mCalendarProperties.getSelectedDays().contains(selectedDay)) {
            mCalendarProperties.getSelectedDays().add(selectedDay);
            informDatePicker();
            return;
        }

        mCalendarProperties.getSelectedDays().remove(selectedDay);
        informDatePicker();
    }

    public List<SelectedDay> getSelectedDays() {
        return mCalendarProperties.getSelectedDays();
    }

    public SelectedDay getSelectedDay(int position) {
        return mCalendarProperties.getSelectedDays().get(position);
    }

    public void setSelectedDay(SelectedDay selectedDay) {
        mCalendarProperties.setSelectedDay(selectedDay);
        informDatePicker();
    }

    /**
     * This method inform DatePicker about ability to return selected days
     */
    private void informDatePicker() {
        if (mCalendarProperties.getOnSelectionAbilityListener() != null) {
            mCalendarProperties.getOnSelectionAbilityListener()
                    .onChange(mCalendarProperties.getSelectedDays().size() > 0);
        }
    }

    /**
     * This method fill calendar GridView with days
     *
     * @param position Position of current page in ViewPager
     */
    private void loadMonth(int position) {
        ArrayList<Date> days = new ArrayList<>();

        // Get Calendar object instance
        Calendar calendar = (Calendar) mCalendarProperties.getFirstPageCalendarDate().clone();

        // Add months to Calendar (a number of months depends on ViewPager position)
        calendar.add(Calendar.MONTH, position);

        // Set day of month as 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Get a number of the first day of the week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Count when month is beginning
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int monthBeginningCell = (dayOfWeek < firstDayOfWeek ? 7 : 0) + dayOfWeek - firstDayOfWeek;

        // Subtract a number of beginning days, it will let to load a part of a previous month
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        /*
        Get all days of one page (42 is a number of all possible cells in one page
        (a part of previous month, current month and a part of next month))
         */
        while (days.size() < 42) {
            days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mPageMonth = calendar.get(Calendar.MONTH) - 1;
        CalendarDayAdapter calendarDayAdapter = new CalendarDayAdapter(mContext, this,
                mCalendarProperties, days, mPageMonth);

        mCalendarGridView.setAdapter(calendarDayAdapter);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
