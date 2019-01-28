package com.applandeo.materialcalendarview.utils;

import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.exceptions.ErrorsMessages;
import com.applandeo.materialcalendarview.exceptions.UnsupportedMethodsException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.listeners.OnSelectionAbilityListener;
import com.applandeo.materialcalendarview.model.EventDay;
import com.applandeo.materialcalendarview.model.SelectedDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class contains all properties of the calendar
 * <p>
 * Created by Mateusz Kornakiewicz on 30.10.2017.
 */
public class CalendarProperties {

    /**
     * A number of months (pages) in the calendar
     * 2401 months means 1200 months (100 years) before and 1200 months after the current month
     */
    public static final int CALENDAR_SIZE = 2401;
    public static final int FIRST_VISIBLE_PAGE = CALENDAR_SIZE / 2;

    public static final int INVALID_RES_ID = 0;

    private static final int DEFAULT_PAGES_COLOR = R.color.pageColor;

    private static final int DEFAULT_DIALOG_BUTTON_TEXT_APPEARANCE = R.style.LabelTextStyle_DialogButton;

    private static final int DEFAULT_HEADER_BACKGROUND_COLOR = R.color.headerColor;

    private static final int DEFAULT_PREVIOUS_BUTTON_SRC = R.drawable.ic_arrow_left;
    private static final int DEFAULT_FORWARD_BUTTON_SRC = R.drawable.ic_arrow_right;

    private static final int DEFAULT_MONTH_NAMES = R.array.material_calendar_months_array;
    private static final int DEFAULT_MONTH_NAME_TEXT_APPEARANCE = R.style.LabelTextStyle_MonthName;

    private static final int DEFAULT_DAY_NAMES = R.array.material_calendar_day_abbreviations_array;
    private static final int DEFAULT_DAY_NAMES_BAR_COLOR = R.color.abbreviationBarColor;
    private static final int DEFAULT_DAY_NAME_TEXT_APPEARANCE = R.style.LabelTextStyle_DayAbbreviation;

    private static final int DEFAULT_CURRENT_MONTH_DAY_BACKGROUND_SRC = R.drawable.day_item_background;
    private static final int DEFAULT_CURRENT_MONTH_DAY_TEXT_APPEARANCE = R.style.LabelTextStyle_CurrentMonthDay;

    private static final int DEFAULT_ANOTHER_MONTH_DAY_BACKGROUND_SRC = R.drawable.day_item_background;
    private static final int DEFAULT_ANOTHER_MONTH_DAY_TEXT_APPEARANCE = R.style.LabelTextStyle_AnotherMonthDay;

    private static final int DEFAULT_TODAY_BACKGROUND_SRC = R.drawable.day_item_background;
    private static final int DEFAULT_TODAY_TEXT_APPEARANCE = R.style.LabelTextStyle_Today;

    private static final int DEFAULT_SELECTED_DAY_BACKGROUND_SRC = R.drawable.background_color_circle_selector;
    private static final int DEFAULT_SELECTED_DAY_TEXT_APPEARANCE = R.style.LabelTextStyle_SelectedDay;

    private static final int DEFAULT_DISABLED_DAY_BACKGROUND_SRC = R.drawable.day_item_background;
    private static final int DEFAULT_DISABLED_DAY_TEXT_APPEARANCE = R.style.LabelTextStyle_DisabledDay;

    private int mCalendarType, mDayItemLayoutResource,
            mPagesColor, mDialogButtonTextAppearance,
            mHeaderVisibility, mHeaderBackgroundColor,
            mPreviousButtonSrc, mForwardButtonSrc,
            mMonthNames, mMonthNameTextAppearance,
            mDayNames, mDayNamesBarColor, mDayNameTextAppearance,
            mCurrentMonthDayBackgroundSrc, mCurrentMonthDayTextAppearance,
            mAnotherMonthDayBackgroundSrc, mAnotherMonthDayTextAppearance,
            mTodayDayBackgroundSrc, mTodayTextAppearance,
            mSelectedDayBackgroundSrc, mSelectedDayTextAppearance,
            mDisabledDayBackgroundSrc, mDisabledDayTextAppearance;

    private boolean mEventsEnabled;

    private Calendar mFirstPageCalendarDate = DateUtils.getCalendar();
    private Calendar mCalendar, mMinimumDate, mMaximumDate;

    private OnDayClickListener mOnDayClickListener;
    private OnSelectDateListener mOnSelectDateListener;
    private OnSelectionAbilityListener mOnSelectionAbilityListener;
    private OnCalendarPageChangeListener mOnPreviousPageChangeListener;
    private OnCalendarPageChangeListener mOnForwardPageChangeListener;

    private List<EventDay> mEventDays = new ArrayList<>();
    private List<Calendar> mDisabledDays = new ArrayList<>();
    private List<SelectedDay> mSelectedDays = new ArrayList<>();

    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public Calendar getFirstPageCalendarDate() {
        return mFirstPageCalendarDate;
    }

    public Calendar getMinimumDate() {
        return mMinimumDate;
    }

    public void setMinimumDate(Calendar minimumDate) {
        mMinimumDate = minimumDate;
    }

    public Calendar getMaximumDate() {
        return mMaximumDate;
    }

    public void setMaximumDate(Calendar maximumDate) {
        mMaximumDate = maximumDate;
    }

    public OnDayClickListener getOnDayClickListener() {
        return mOnDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public OnSelectDateListener getOnSelectDateListener() {
        return mOnSelectDateListener;
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        mOnSelectDateListener = onSelectDateListener;
    }

    public OnSelectionAbilityListener getOnSelectionAbilityListener() {
        return mOnSelectionAbilityListener;
    }

    public void setOnSelectionAbilityListener(OnSelectionAbilityListener onSelectionAbilityListener) {
        mOnSelectionAbilityListener = onSelectionAbilityListener;
    }

    public OnCalendarPageChangeListener getOnPreviousPageChangeListener() {
        return mOnPreviousPageChangeListener;
    }

    public void setOnPreviousPageChangeListener(OnCalendarPageChangeListener onPreviousButtonClickListener) {
        mOnPreviousPageChangeListener = onPreviousButtonClickListener;
    }

    public OnCalendarPageChangeListener getOnForwardPageChangeListener() {
        return mOnForwardPageChangeListener;
    }

    public void setOnForwardPageChangeListener(OnCalendarPageChangeListener onForwardButtonClickListener) {
        mOnForwardPageChangeListener = onForwardButtonClickListener;
    }

    public boolean getEventsEnabled() {
        return mEventsEnabled;
    }

    public void setEventsEnabled(boolean eventsEnabled) {
        mEventsEnabled = eventsEnabled;
    }

    public List<EventDay> getEventDays() {
        return mEventDays;
    }

    public void setEventDays(List<EventDay> eventDays) {
        mEventDays = eventDays;
    }

    public List<Calendar> getDisabledDays() {
        return mDisabledDays;
    }

    public void setDisabledDays(List<Calendar> disabledDays) {
        mSelectedDays.removeAll(disabledDays);

        mDisabledDays = Stream.of(disabledDays)
                .map(calendar -> {
                    DateUtils.setMidnight(calendar);
                    return calendar;
                }).toList();
    }

    public List<SelectedDay> getSelectedDays() {
        return mSelectedDays;
    }

    public void setSelectedDay(Calendar calendar) {
        setSelectedDay(new SelectedDay(calendar));
    }

    public void setSelectedDay(SelectedDay selectedDay) {
        mSelectedDays.clear();
        mSelectedDays.add(selectedDay);
    }

    public void setSelectedDays(List<Calendar> selectedDays) {
        if (mCalendarType == CalendarView.ONE_DAY_PICKER) {
            throw new UnsupportedMethodsException(ErrorsMessages.ONE_DAY_PICKER_MULTIPLE_SELECTION);
        }

        if (mCalendarType == CalendarView.RANGE_PICKER && !DateUtils.isFullDatesRange(selectedDays)) {
            throw new UnsupportedMethodsException(ErrorsMessages.RANGE_PICKER_NOT_RANGE);
        }

        mSelectedDays = Stream.of(selectedDays)
                .map(calendar -> {
                    DateUtils.setMidnight(calendar);
                    return new SelectedDay(calendar);
                }).filterNot(value -> mDisabledDays.contains(value.getCalendar()))
                .toList();
    }

    public int getCalendarType() {
        return mCalendarType;
    }

    public void setCalendarType(int calendarType) {
        mCalendarType = calendarType;
    }

    public int getDayItemLayoutResource() {
        return mDayItemLayoutResource;
    }

    public void setDayItemLayoutResource(int itemLayoutResource) {
        mDayItemLayoutResource = itemLayoutResource;
    }

    public int getPagesColor() {
        if (mPagesColor != INVALID_RES_ID) {
            return mPagesColor;
        }

        return DEFAULT_PAGES_COLOR;
    }

    public void setPagesColor(@ColorRes int pagesColor) {
        mPagesColor = pagesColor;
    }

    public int getDialogButtonTextAppearance() {
        if (mDialogButtonTextAppearance != INVALID_RES_ID) {
            return mDialogButtonTextAppearance;
        }

        return DEFAULT_DIALOG_BUTTON_TEXT_APPEARANCE;
    }

    public void setDialogButtonTextAppearance(@StyleRes int dialogButtonsTextAppearance) {
        mDialogButtonTextAppearance = dialogButtonsTextAppearance;
    }

    public int getHeaderVisibility() {
        return mHeaderVisibility;
    }

    public void setHeaderVisibility(int headerVisibility) {
        mHeaderVisibility = headerVisibility;
    }

    public int getHeaderBackgroundColor() {
        if (mHeaderBackgroundColor != INVALID_RES_ID) {
            return mHeaderBackgroundColor;
        }

        return DEFAULT_HEADER_BACKGROUND_COLOR;
    }

    public void setHeaderBackgroundColor(@ColorRes int headerBackgroundColor) {
        mHeaderBackgroundColor = headerBackgroundColor;
    }

    public int getPreviousButtonSrc() {
        if (mPreviousButtonSrc != INVALID_RES_ID) {
            return mPreviousButtonSrc;
        }

        return DEFAULT_PREVIOUS_BUTTON_SRC;
    }

    public void setPreviousButtonSrc(@DrawableRes int previousButtonSrc) {
        mPreviousButtonSrc = previousButtonSrc;
    }

    public int getForwardButtonSrc() {
        if (mForwardButtonSrc != INVALID_RES_ID) {
            return mForwardButtonSrc;
        }

        return DEFAULT_FORWARD_BUTTON_SRC;
    }

    public void setForwardButtonSrc(@DrawableRes int forwardButtonSrc) {
        mForwardButtonSrc = forwardButtonSrc;
    }

    public int getMonthNames() {
        if (mMonthNames != INVALID_RES_ID) {
            return mMonthNames;
        }

        return DEFAULT_MONTH_NAMES;
    }

    public void setMonthNames(@ArrayRes int monthNamesRes) {
        this.mMonthNames = monthNamesRes;
    }

    public int getMonthNameTextAppearance() {
        if (mMonthNameTextAppearance != INVALID_RES_ID) {
            return mMonthNameTextAppearance;
        }

        return DEFAULT_MONTH_NAME_TEXT_APPEARANCE;
    }

    public void setMonthNameTextAppearance(@StyleRes int monthNameTextAppearance) {
        this.mMonthNameTextAppearance = monthNameTextAppearance;
    }

    public int getDayNames() {
        if (mDayNames != INVALID_RES_ID) {
            return mDayNames;
        }

        return DEFAULT_DAY_NAMES;
    }

    public void setDayNames(@ArrayRes int dayNamesRes) {
        this.mDayNames = dayNamesRes;
    }

    public int getDayNamesBarColor() {
        if (mDayNamesBarColor != INVALID_RES_ID) {
            return mDayNamesBarColor;
        }

        return DEFAULT_DAY_NAMES_BAR_COLOR;
    }

    public void setDayNamesBarColor(@ColorRes int dayNamesBarColor) {
        this.mDayNamesBarColor = dayNamesBarColor;
    }

    public int getDayNameTextAppearance() {
        if (mDayNameTextAppearance != INVALID_RES_ID) {
            return mDayNameTextAppearance;
        }

        return DEFAULT_DAY_NAME_TEXT_APPEARANCE;
    }

    public void setDayNameTextAppearance(@StyleRes int dayNameTextAppearance) {
        this.mDayNameTextAppearance = dayNameTextAppearance;
    }

    public int getCurrentMonthDayBackgroundSrc() {
        if (mCurrentMonthDayBackgroundSrc != INVALID_RES_ID) {
            return mCurrentMonthDayBackgroundSrc;
        }

        return DEFAULT_CURRENT_MONTH_DAY_BACKGROUND_SRC;
    }

    public void setCurrentMonthDayBackgroundSrc(@DrawableRes int mCurrentMonthDayBackgroundSrc) {
        this.mCurrentMonthDayBackgroundSrc = mCurrentMonthDayBackgroundSrc;
    }

    public int getCurrentMonthDayTextAppearance() {
        if (mCurrentMonthDayTextAppearance != INVALID_RES_ID) {
            return mCurrentMonthDayTextAppearance;
        }

        return DEFAULT_CURRENT_MONTH_DAY_TEXT_APPEARANCE;
    }

    public void setCurrentMonthDayTextAppearance(@StyleRes int mCurrentMonthDayTextAppearance) {
        this.mCurrentMonthDayTextAppearance = mCurrentMonthDayTextAppearance;
    }

    public int getAnotherMonthDayBackgroundSrc() {
        if (mAnotherMonthDayBackgroundSrc != INVALID_RES_ID) {
            return mAnotherMonthDayBackgroundSrc;
        }

        return DEFAULT_ANOTHER_MONTH_DAY_BACKGROUND_SRC;
    }

    public void setAnotherMonthDayBackgroundSrc(@DrawableRes int anotherMonthDayBackgroundSrc) {
        this.mAnotherMonthDayBackgroundSrc = anotherMonthDayBackgroundSrc;
    }

    public int getAnotherMonthDayTextAppearance() {
        if (mAnotherMonthDayTextAppearance != INVALID_RES_ID) {
            return mAnotherMonthDayTextAppearance;
        }

        return DEFAULT_ANOTHER_MONTH_DAY_TEXT_APPEARANCE;
    }

    public void setAnotherMonthDayTextAppearance(@StyleRes int anotherMonthDayTextAppearance) {
        this.mAnotherMonthDayTextAppearance = anotherMonthDayTextAppearance;
    }

    public int getTodayDayBackgroundSrc() {
        if (mTodayDayBackgroundSrc != INVALID_RES_ID) {
            return mTodayDayBackgroundSrc;
        }

        return DEFAULT_TODAY_BACKGROUND_SRC;
    }

    public void setTodayBackgroundSrc(@DrawableRes int todayDayBackgroundSrc) {
        this.mTodayDayBackgroundSrc = todayDayBackgroundSrc;
    }

    public int getTodayTextAppearance() {
        if (mTodayTextAppearance != INVALID_RES_ID) {
            return mTodayTextAppearance;
        }

        return DEFAULT_TODAY_TEXT_APPEARANCE;
    }

    public void setTodayTextAppearance(@StyleRes int todayTextAppearance) {
        this.mTodayTextAppearance = todayTextAppearance;
    }

    public int getSelectedDayBackgroundSrc() {
        if (mSelectedDayBackgroundSrc != INVALID_RES_ID) {
            return mSelectedDayBackgroundSrc;
        }

        return DEFAULT_SELECTED_DAY_BACKGROUND_SRC;
    }

    public void setSelectedDayBackgroundSrc(@DrawableRes int selectedDayBackgroundSrc) {
        this.mSelectedDayBackgroundSrc = selectedDayBackgroundSrc;
    }

    public int getSelectedDayTextAppearance() {
        if (mSelectedDayTextAppearance != INVALID_RES_ID) {
            return mSelectedDayTextAppearance;
        }

        return DEFAULT_SELECTED_DAY_TEXT_APPEARANCE;
    }

    public void setSelectedDayTextAppearance(@StyleRes int selectedDayTextAppearance) {
        this.mSelectedDayTextAppearance = selectedDayTextAppearance;
    }

    public int getDisabledDayBackgroundSrc() {
        if (mDisabledDayBackgroundSrc != INVALID_RES_ID) {
            return mDisabledDayBackgroundSrc;
        }

        return DEFAULT_DISABLED_DAY_BACKGROUND_SRC;
    }

    public void setDisabledDayBackgroundSrc(@DrawableRes int disabledDayBackgroundSrc) {
        this.mDisabledDayBackgroundSrc = disabledDayBackgroundSrc;
    }

    public int getDisabledDayTextAppearance() {
        if (mDisabledDayTextAppearance != INVALID_RES_ID) {
            return mDisabledDayTextAppearance;
        }

        return DEFAULT_DISABLED_DAY_TEXT_APPEARANCE;
    }

    public void setDisabledDayTextAppearance(@StyleRes int disabledDayTextAppearance) {
        this.mDisabledDayTextAppearance = disabledDayTextAppearance;
    }
}
