package com.applandeo.materialcalendarsampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.utils.CalendarUtils;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSelectDateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openCalendarButton = findViewById(R.id.openCalendarButton);

        openCalendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        });

        Button openOneDayPicker = findViewById(R.id.openOneDayPickerButton);
        openOneDayPicker.setOnClickListener(v -> startActivity(new Intent(this, OneDayPickerActivity.class)));

        Button openManyDaysPicker = findViewById(R.id.openManyDayPickerButton);
        openManyDaysPicker.setOnClickListener(v -> startActivity(new Intent(this, ManyDaysPickerActivity.class)));

        Button openRangePicker = findViewById(R.id.openRangePickerButton);
        openRangePicker.setOnClickListener(v -> startActivity(new Intent(this, RangePickerActivity.class)));

        Button openOneDayPickerDialog = findViewById(R.id.openOneDayPickerDialogButton);
        openOneDayPickerDialog.setOnClickListener(v -> openOneDayPicker());

        Button openManyDaysPickerDialog = findViewById(R.id.openManyDaysPickerDialogButton);
        openManyDaysPickerDialog.setOnClickListener(v -> openManyDaysPicker());

        Button openRangePickerDialog = findViewById(R.id.openRangePickerDialogButton);
        openRangePickerDialog.setOnClickListener(v -> openRangePicker());
    }

    private void openOneDayPicker() {
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -5);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 3);

        DatePickerBuilder oneDayBuilder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.ONE_DAY_PICKER)
                .date(max)
                .headerBackgroundColor(R.color.colorPrimaryDark)
                .monthNameTextAppearance(R.style.SampleLabelTextStyle_MonthName)
                .selectedDayTextAppearance(R.style.SampleLabelTextStyle_SelectedDay)
                .todayTextAppearance(R.style.SampleLabelTextStyle_Today)
                .dialogButtonsTextAppearance(R.style.LabelTextStyle_DialogButton)
                .disabledDayTextAppearance(R.style.SampleLabelTextStyle_DisabledDay)
                .previousButtonSrc(R.drawable.ic_chevron_left_black_24dp)
                .forwardButtonSrc(R.drawable.ic_chevron_right_black_24dp)
                .minimumDate(min)
                .maximumDate(max)
                .disabledDays(getDisabledDays());

        DatePicker oneDayPicker = oneDayBuilder.build();
        oneDayPicker.show();
    }

    private void openManyDaysPicker() {
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, -5);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 3);

        List<Calendar> selectedDays = new ArrayList<>(getDisabledDays());
        selectedDays.add(min);
        selectedDays.add(max);

        DatePickerBuilder manyDaysBuilder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.MANY_DAYS_PICKER)
                .headerBackgroundColor(android.R.color.holo_green_dark)
                .selectedDays(selectedDays)
                .disabledDays(getDisabledDays())
                .selectedDayTextAppearance(R.style.SampleLabelTextStyle_SelectedDay)
                .todayTextAppearance(R.style.SampleLabelTextStyle_Today)
                .dialogButtonsTextAppearance(R.style.LabelTextStyle_DialogButton);

        DatePicker manyDaysPicker = manyDaysBuilder.build();
        manyDaysPicker.show();
    }

    private void openRangePicker() {
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, -5);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 3);

        List<Calendar> selectedDays = new ArrayList<>();
        selectedDays.add(min);
        selectedDays.addAll(CalendarUtils.getDatesRange(min, max));
        selectedDays.add(max);

        DatePickerBuilder rangeBuilder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.RANGE_PICKER)
                .headerBackgroundColor(R.color.sampleDark)
                .dayNamesBarColor(R.color.sampleLight)
                .dayNamesBarColor(android.R.color.white)
                .pagesColor(R.color.pageColor)
                .selectedDayTextAppearance(R.style.SampleLabelTextStyle_SelectedDay)
                .selectedDayBackgroundSrc(R.drawable.day_selected_background)
                .todayTextAppearance(R.style.SampleLabelTextStyle_Today)
                .dialogButtonsTextAppearance(R.style.LabelTextStyle_DialogButton)
                .dayNameTextAppearance(R.style.SampleLabelTextStyle_DayAbbreviation)
                .anotherMonthDayTextAppearance(R.style.SampleLabelTextStyle_AnotherMonthDay)
                .selectedDays(selectedDays)
                .disabledDays(getDisabledDays());

        DatePicker rangePicker = rangeBuilder.build();
        rangePicker.show();
    }

    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }

    @Override
    public void onSelect(List<Calendar> calendars) {
        Stream.of(calendars).forEach(calendar ->
                Toast.makeText(getApplicationContext(),
                        calendar.getTime().toString(),
                        Toast.LENGTH_SHORT).show());
    }
}
