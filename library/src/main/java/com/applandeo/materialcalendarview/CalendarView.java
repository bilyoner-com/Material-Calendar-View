package com.applandeo.materialcalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.adapters.CalendarPageAdapter;
import com.applandeo.materialcalendarview.exceptions.ErrorsMessages;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.extensions.CalendarViewPager;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.model.EventDay;
import com.applandeo.materialcalendarview.model.SelectedDay;
import com.applandeo.materialcalendarview.utils.AppearanceUtils;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.applandeo.materialcalendarview.utils.CalendarProperties.FIRST_VISIBLE_PAGE;

/**
 * This class represents a view, displays to user as calendar. It allows to work in date picker
 * mode or like a normal calendar. In a normal calendar mode it can displays an image under the day
 * number. In both modes it marks today day. It also provides click on day events using
 * OnDayClickListener which returns an EventDay object.
 *
 * @see EventDay
 * @see OnDayClickListener
 * <p>
 * <p>
 * XML attributes:
 * - Set calendar page background color: pagesColor="@color/[color]"
 * - Set calendar header visibility: headerVisibility="[visible/gone]"
 * - Set calendar header color: headerBackgroundColor="@color/[color]"
 * - Set previous button resource: previousButtonSrc="@drawable/[drawable]"
 * - Ser forward button resource: forwardButtonSrc="@drawable/[drawable]"
 * - Set month names showing on header: monthNames="@array/[array]"
 * - Set header label text appearance: monthNameTextAppearance="@style/[appearance]"
 * - Set day abbreviation names: dayNames="@array/[array]"
 * - Set day abbreviation bar color: dayNamesBarColor="@color/[color]"
 * - Set day abbreviation text appearance: dayNameTextAppearance="@style/[appearance]"
 * - Set day item in current month background resource: currentMonthDayBackgroundSrc="@drawable/[drawable]"
 * - Set day item in current month text appearance: currentMonthTextAppearance="@style/[appearance]"
 * - Set day item in another month background resource: anotherMonthDayBackgroundSrc="@drawable/[drawable]"
 * - Set day item in another month text appearance: anotherMonthDayTextAppearance="@style/[appearance]"
 * - Set today label background resource: todayBackgroundSrc="@drawable/[drawable]"
 * - Set today label text appearance: todayTextAppearance="@style/[appearance]"
 * - Set selected day label background resource: selectedDayBackgroundSrc="@drawable/[drawable]"
 * - Set selected day label text appearance: selectedDayTextAppearance="@style/[appearance]"
 * - Set disabled day label background resource: disabledDayBackgroundSrc="@drawable/[drawable]"
 * - Set disabled day label text appearance: disabledDayTextAppearance="@style/[appearance]"
 * - Set events are enabled: eventsEnabled="[true/false]"
 * - Set calendar type: type="classic or one_day_picker or many_days_picker or range_picker"
 * <p>
 * Created by Mateusz Kornakiewicz on 23.05.2017.
 */
public class CalendarView extends LinearLayout {

    public static final int CLASSIC = 0;
    public static final int ONE_DAY_PICKER = 1;
    public static final int MANY_DAYS_PICKER = 2;
    public static final int RANGE_PICKER = 3;

    private Context mContext;
    private CalendarPageAdapter mCalendarPageAdapter;

    private TextView mCurrentMonthLabel;
    private int mCurrentPage;
    private CalendarViewPager mViewPager;

    private CalendarProperties mCalendarProperties;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
        initCalendar();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
        initCalendar();
    }

    //protected constructor to create CalendarView for the dialog date picker
    protected CalendarView(Context context, CalendarProperties calendarProperties) {
        super(context);
        mContext = context;
        mCalendarProperties = calendarProperties;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.calendar_view, this);

        initUiElements();
        initAttributes();
        initCalendar();
    }

    private void initControl(Context context, AttributeSet attrs) {
        mContext = context;
        mCalendarProperties = new CalendarProperties();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.calendar_view, this);

        initUiElements();
        setAttributes(attrs);
    }

    /**
     * This method set xml values for calendar elements
     *
     * @param attrs A set of xml attributes
     */
    private void setAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            initCalendarProperties(typedArray);
            initAttributes();
        } finally {
            typedArray.recycle();
        }
    }

    private void initCalendarProperties(TypedArray typedArray) {

        int pagesColor = typedArray.getResourceId(R.styleable.CalendarView_pagesColor, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setPagesColor(pagesColor);

        int headerVisibility = typedArray.getInt(R.styleable.CalendarView_headerVisibility, View.VISIBLE);
        mCalendarProperties.setHeaderVisibility(headerVisibility);

        int headerBackgroundColor = typedArray.getResourceId(R.styleable.CalendarView_headerBackgroundColor, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setHeaderBackgroundColor(headerBackgroundColor);

        int previousButtonSrc = typedArray.getResourceId(R.styleable.CalendarView_previousButtonSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setPreviousButtonSrc(previousButtonSrc);

        int forwardButtonSrc = typedArray.getResourceId(R.styleable.CalendarView_forwardButtonSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setForwardButtonSrc(forwardButtonSrc);

        int monthNames = typedArray.getResourceId(R.styleable.CalendarView_monthNames, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setMonthNames(monthNames);

        int monthNameTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_monthNameTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setMonthNameTextAppearance(monthNameTextAppearance);

        int dayNames = typedArray.getResourceId(R.styleable.CalendarView_dayNames, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setDayNames(dayNames);

        int dayNamesBarColor = typedArray.getResourceId(R.styleable.CalendarView_dayNamesBarColor, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setDayNamesBarColor(dayNamesBarColor);

        int dayNameTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_dayNameTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setDayNameTextAppearance(dayNameTextAppearance);

        int currentMonthDayBackgroundSrc = typedArray.getResourceId(R.styleable.CalendarView_currentMonthDayBackgroundSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setCurrentMonthDayBackgroundSrc(currentMonthDayBackgroundSrc);

        int currentMonthTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_currentMonthTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setCurrentMonthDayTextAppearance(currentMonthTextAppearance);

        int anotherMonthDayBackgroundSrc = typedArray.getResourceId(R.styleable.CalendarView_anotherMonthDayBackgroundSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setAnotherMonthDayBackgroundSrc(anotherMonthDayBackgroundSrc);

        int anotherMonthTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_anotherMonthDayTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setAnotherMonthDayTextAppearance(anotherMonthTextAppearance);

        int todayBackgroundSrc = typedArray.getResourceId(R.styleable.CalendarView_todayBackgroundSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setTodayBackgroundSrc(todayBackgroundSrc);

        int todayTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_todayTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setTodayTextAppearance(todayTextAppearance);

        int selectedDayBackgroundSrc = typedArray.getResourceId(R.styleable.CalendarView_selectedDayBackgroundSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setSelectedDayBackgroundSrc(selectedDayBackgroundSrc);

        int selectedDayTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_selectedDayTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setSelectedDayTextAppearance(selectedDayTextAppearance);

        int disabledMonthDayBackgroundSrc = typedArray.getResourceId(R.styleable.CalendarView_disabledDayBackgroundSrc, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setDisabledDayBackgroundSrc(disabledMonthDayBackgroundSrc);

        int disabledDayTextAppearance = typedArray.getResourceId(R.styleable.CalendarView_disabledDayTextAppearance, CalendarProperties.INVALID_RES_ID);
        mCalendarProperties.setDisabledDayTextAppearance(disabledDayTextAppearance);

        boolean eventsEnabled = typedArray.getBoolean(R.styleable.CalendarView_eventsEnabled,
                mCalendarProperties.getCalendarType() == CLASSIC);
        mCalendarProperties.setEventsEnabled(eventsEnabled);

        int calendarType = typedArray.getInt(R.styleable.CalendarView_type, CLASSIC);
        mCalendarProperties.setCalendarType(calendarType);
    }

    private void initAttributes() {
        AppearanceUtils.setPagesColor(getRootView(), mCalendarProperties.getPagesColor());

        AppearanceUtils.setHeaderVisibility(getRootView(), mCalendarProperties.getHeaderVisibility());

        AppearanceUtils.setHeaderBackgroundColor(getRootView(), mCalendarProperties.getHeaderBackgroundColor());

        AppearanceUtils.setPreviousButtonImage(getRootView(), mCalendarProperties.getPreviousButtonSrc());

        AppearanceUtils.setForwardButtonImage(getRootView(), mCalendarProperties.getForwardButtonSrc());

        AppearanceUtils.setHeaderLabelTextAppearance(getRootView(), mCalendarProperties.getMonthNameTextAppearance());

        AppearanceUtils.setDayNames(getRootView(),
                mCalendarProperties.getDayNames(),
                mCalendarProperties.getDayNameTextAppearance(),
                mCalendarProperties.getFirstPageCalendarDate().getFirstDayOfWeek());

        AppearanceUtils.setDayNamesBarColor(getRootView(), mCalendarProperties.getDayNamesBarColor());

        // Sets layout for date picker or normal calendar
        setCalendarRowLayout();
    }

    private void setCalendarRowLayout() {
        if (mCalendarProperties.getEventsEnabled()) {
            mCalendarProperties.setDayItemLayoutResource(R.layout.calendar_view_day);
        } else {
            mCalendarProperties.setDayItemLayoutResource(R.layout.calendar_view_picker_day);
        }
    }

    private void initUiElements() {
        ImageButton forwardButton = findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(onNextClickListener);

        ImageButton previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(onPreviousClickListener);

        mCurrentMonthLabel = findViewById(R.id.currentDateLabel);

        mViewPager = findViewById(R.id.calendarViewPager);
    }

    private void initCalendar() {
        mCalendarPageAdapter = new CalendarPageAdapter(mContext, mCalendarProperties);

        mViewPager.setAdapter(mCalendarPageAdapter);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

        setUpCalendarPosition(Calendar.getInstance());
    }

    private void setUpCalendarPosition(Calendar calendar) {
        DateUtils.setMidnight(calendar);

        if (mCalendarProperties.getCalendarType() == CalendarView.ONE_DAY_PICKER) {
            mCalendarProperties.setSelectedDay(calendar);
        }

        mCalendarProperties.getFirstPageCalendarDate().setTime(calendar.getTime());
        mCalendarProperties.getFirstPageCalendarDate().add(Calendar.MONTH, -FIRST_VISIBLE_PAGE);

        mViewPager.setCurrentItem(FIRST_VISIBLE_PAGE);
    }

    public void setOnPreviousPageChangeListener(OnCalendarPageChangeListener listener) {
        mCalendarProperties.setOnPreviousPageChangeListener(listener);
    }

    public void setOnForwardPageChangeListener(OnCalendarPageChangeListener listener) {
        mCalendarProperties.setOnForwardPageChangeListener(listener);
    }

    /**
     * @param onDayClickListener OnDayClickListener interface responsible for handle clicks on calendar cells
     * @see OnDayClickListener
     */
    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mCalendarProperties.setOnDayClickListener(onDayClickListener);
    }

    private final OnClickListener onNextClickListener =
            v -> mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

    private final OnClickListener onPreviousClickListener =
            v -> mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);

    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        /**
         * This method set calendar header label
         *
         * @param position Current ViewPager position
         * @see ViewPager.OnPageChangeListener
         */
        @Override
        public void onPageSelected(int position) {
            Calendar calendar = (Calendar) mCalendarProperties.getFirstPageCalendarDate().clone();
            calendar.add(Calendar.MONTH, position);

            if (!isScrollingLimited(calendar, position)) {
                setHeaderName(calendar, position, mCalendarProperties.getMonthNames());
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private boolean isScrollingLimited(Calendar calendar, int position) {
        if (DateUtils.isMonthBefore(mCalendarProperties.getMinimumDate(), calendar)) {
            mViewPager.setCurrentItem(position + 1);
            return true;
        }

        if (DateUtils.isMonthAfter(mCalendarProperties.getMaximumDate(), calendar)) {
            mViewPager.setCurrentItem(position - 1);
            return true;
        }

        return false;
    }

    private void setHeaderName(Calendar calendar, int position, @ArrayRes int monthLabels) {
        mCurrentMonthLabel.setText(DateUtils.getMonthAndYearDate(mContext, calendar, monthLabels));
        callOnPageChangeListeners(position);
    }

    // This method calls page change listeners after swipe calendar or click arrow buttons
    private void callOnPageChangeListeners(int position) {
        if (position > mCurrentPage && mCalendarProperties.getOnForwardPageChangeListener() != null) {
            mCalendarProperties.getOnForwardPageChangeListener().onChange();
        }

        if (position < mCurrentPage && mCalendarProperties.getOnPreviousPageChangeListener() != null) {
            mCalendarProperties.getOnPreviousPageChangeListener().onChange();
        }

        mCurrentPage = position;
    }

    /**
     * This method set a current and selected date of the calendar using Date object.
     *
     * @param currentDate A date to which the calendar will be set
     */
    public void setDate(Date currentDate) throws OutOfDateRangeException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        setDate(calendar);
    }

    /**
     * This method set a current and selected date of the calendar using Calendar object.
     *
     * @param date A Calendar object representing a date to which the calendar will be set
     */
    public void setDate(Calendar date) throws OutOfDateRangeException {
        if (mCalendarProperties.getMinimumDate() != null && date.before(mCalendarProperties.getMinimumDate())) {
            throw new OutOfDateRangeException(ErrorsMessages.OUT_OF_RANGE_MIN);
        }

        if (mCalendarProperties.getMaximumDate() != null && date.after(mCalendarProperties.getMaximumDate())) {
            throw new OutOfDateRangeException(ErrorsMessages.OUT_OF_RANGE_MAX);
        }

        setUpCalendarPosition(date);

        mCurrentMonthLabel.setText(DateUtils.getMonthAndYearDate(mContext, date, mCalendarProperties.getMonthNames()));
        mCalendarPageAdapter.notifyDataSetChanged();
    }

    /**
     * This method is used to set a list of events displayed in calendar cells,
     * visible as images under the day number.
     *
     * @param eventDays List of EventDay objects
     * @see EventDay
     */
    public void setEvents(List<EventDay> eventDays) {
        if (mCalendarProperties.getEventsEnabled()) {
            mCalendarProperties.setEventDays(eventDays);
            mCalendarPageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @return List of Calendar object representing a selected dates
     */
    public List<Calendar> getSelectedDates() {
        return Stream.of(mCalendarPageAdapter.getSelectedDays())
                .map(SelectedDay::getCalendar)
                .sortBy(calendar -> calendar).toList();
    }

    public void setSelectedDates(List<Calendar> selectedDates) {
        mCalendarProperties.setSelectedDays(selectedDates);
        mCalendarPageAdapter.notifyDataSetChanged();
    }

    /**
     * @return Calendar object representing a selected date
     */
    @Deprecated
    public Calendar getSelectedDate() {
        return getFirstSelectedDate();
    }

    /**
     * @return Calendar object representing a selected date
     */
    public Calendar getFirstSelectedDate() {
        return Stream.of(mCalendarPageAdapter.getSelectedDays())
                .map(SelectedDay::getCalendar).findFirst().get();
    }

    /**
     * @return Calendar object representing a date of current calendar page
     */
    public Calendar getCurrentPageDate() {
        Calendar calendar = (Calendar) mCalendarProperties.getFirstPageCalendarDate().clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, mViewPager.getCurrentItem());
        return calendar;
    }

    /**
     * This method set a minimum available date in calendar
     *
     * @param calendar Calendar object representing a minimum date
     */
    public void setMinimumDate(Calendar calendar) {
        mCalendarProperties.setMinimumDate(calendar);
    }

    /**
     * This method set a maximum available date in calendar
     *
     * @param calendar Calendar object representing a maximum date
     */
    public void setMaximumDate(Calendar calendar) {
        mCalendarProperties.setMaximumDate(calendar);
    }

    /**
     * This method is used to return to current month page
     */
    public void showCurrentMonthPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem()
                - DateUtils.getMonthsBetweenDates(DateUtils.getCalendar(), getCurrentPageDate()), true);
    }

    public void setDisabledDays(List<Calendar> disabledDays) {
        mCalendarProperties.setDisabledDays(disabledDays);
    }

    /**
     * Sets what the first day of the week is; e.g,
     * {@link Calendar#SUNDAY} in the U.S.,
     * {@link Calendar#MONDAY} in France.
     *
     * @param day the given first day of the week.
     */
    public void setFirstDayOfWeek(int day) {
        mCalendarProperties.setFirstDayOfWeek(day);
        initAttributes();
    }
}
