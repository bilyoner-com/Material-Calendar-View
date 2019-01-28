package com.applandeo.materialcalendarview.utils;

import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.applandeo.materialcalendarview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Kornakiewicz on 04.01.2018.
 */
public final class AppearanceUtils {

    private AppearanceUtils() {
    }

    public static void setPagesColor(View view, @ColorRes int color) {
        if (color == 0) {
            return;
        }

        View calendar = view.findViewById(R.id.calendarViewPager);
        calendar.setBackgroundColor(ContextCompat.getColor(view.getContext(), color));
    }

    public static void setHeaderVisibility(View view, int visibility) {
        ConstraintLayout calendarHeader = view.findViewById(R.id.calendarHeader);
        calendarHeader.setVisibility(visibility);
    }

    public static void setHeaderBackgroundColor(View view, @ColorRes int color) {
        if (color == 0) {
            return;
        }

        ConstraintLayout calendarHeader = view.findViewById(R.id.calendarHeader);
        calendarHeader.setBackgroundColor(ContextCompat.getColor(view.getContext(), color));
    }

    public static void setPreviousButtonImage(View view, @DrawableRes int drawable) {
        if (drawable == 0) {
            return;
        }

        ((ImageButton) view.findViewById(R.id.previousButton)).setImageResource(drawable);
    }

    public static void setForwardButtonImage(View view, @DrawableRes int drawable) {
        if (drawable == 0) {
            return;
        }

        ((ImageButton) view.findViewById(R.id.forwardButton)).setImageResource(drawable);
    }

    public static void setHeaderLabelTextAppearance(View view, @StyleRes int textAppearance) {
        if (textAppearance == 0) {
            return;
        }

        ((TextView) view.findViewById(R.id.currentDateLabel))
                .setTextAppearance(view.getContext(), textAppearance);
    }

    public static void setDayNames(View view, @ArrayRes int dayNames, @StyleRes int textAppearance, int firstDayOfWeek) {
        List<TextView> labels = getDayNameLabels(view);

        String[] abbreviations = view.getContext().getResources().getStringArray(dayNames);

        for (int i = 0; i < 7; i++) {
            TextView label = labels.get(i);
            label.setText(abbreviations[(i + firstDayOfWeek - 1) % 7]);

            if (textAppearance != 0) {
                label.setTextAppearance(label.getContext(), textAppearance);
            }
        }
    }

    public static void setDayNamesBarColor(View view, @ColorRes int color) {
        if (color == 0) {
            return;
        }

        View abbreviationsBar = view.findViewById(R.id.abbreviationsBar);
        abbreviationsBar.setBackgroundColor(ContextCompat.getColor(view.getContext(), color));
    }

    private static List<TextView> getDayNameLabels(View view) {
        List<TextView> labels = new ArrayList<>();

        labels.add(view.findViewById(R.id.mondayLabel));
        labels.add(view.findViewById(R.id.tuesdayLabel));
        labels.add(view.findViewById(R.id.wednesdayLabel));
        labels.add(view.findViewById(R.id.thursdayLabel));
        labels.add(view.findViewById(R.id.fridayLabel));
        labels.add(view.findViewById(R.id.saturdayLabel));
        labels.add(view.findViewById(R.id.sundayLabel));

        return labels;
    }

    public static void setDisabledDayTextAppearance(TextView dayLabel, CalendarProperties calendarProperties) {
        setDayTextAppearance(dayLabel,
                calendarProperties.getDisabledDayTextAppearance(),
                calendarProperties.getDisabledDayBackgroundSrc());
    }

    public static void setSelectedDayTextAppearance(TextView dayLabel, CalendarProperties calendarProperties) {
        setDayTextAppearance(dayLabel,
                calendarProperties.getSelectedDayTextAppearance(),
                calendarProperties.getSelectedDayBackgroundSrc());
    }

    public static void setTodayTextAppearance(TextView dayLabel, CalendarProperties calendarProperties) {
        setDayTextAppearance(dayLabel,
                calendarProperties.getTodayTextAppearance(),
                calendarProperties.getTodayDayBackgroundSrc());
    }

    public static void setCurrentMonthDayTextAppearance(TextView dayLabel, CalendarProperties calendarProperties) {
        setDayTextAppearance(dayLabel,
                calendarProperties.getCurrentMonthDayTextAppearance(),
                calendarProperties.getCurrentMonthDayBackgroundSrc());
    }

    public static void setAnotherMonthDayTextAppearance(TextView dayLabel, CalendarProperties calendarProperties) {
        setDayTextAppearance(dayLabel,
                calendarProperties.getAnotherMonthDayTextAppearance(),
                calendarProperties.getAnotherMonthDayBackgroundSrc());
    }

    private static void setDayTextAppearance(TextView textView, int textAppearance, int background) {
        if (textView == null) {
            return;
        }

        textView.setTextAppearance(textView.getContext(), textAppearance);
        textView.setBackgroundResource(background);
    }
}
