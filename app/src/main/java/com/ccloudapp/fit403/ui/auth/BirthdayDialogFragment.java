package com.ccloudapp.fit403.ui.auth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;


import com.ccloudapp.fit403.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : amit
 * Date : 9/5/17.
 */

public class BirthdayDialogFragment extends DialogFragment {

    @BindView(R.id.day_number_picker)
    NumberPicker dayNumberPicker;
    @BindView(R.id.month_number_picker)
    NumberPicker monthNumberPicker;
    @BindView(R.id.year_number_picker)
    NumberPicker yearNumberPicker;

    private static final String TAG = "BirthDayActivity";

    private int mSelectedYear = 1996;
    private int mSelectedMonth = 0;
    private int mSelectedDay = 1;

    private Listener mListener;


    private static final String[] sMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public BirthdayDialogFragment() {
    }


    public static BirthdayDialogFragment newInstance() {
        return new BirthdayDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement BirthdayDialogFragment.Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_date_of_birth, null);
        ButterKnife.bind(this, view);
        Calendar calendar = Calendar.getInstance();

        int mCurrentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int mCurrentMonth = calendar.get(Calendar.MONTH);
        int mCurrentYear = calendar.get(Calendar.YEAR);
        Log.i(TAG, "onCreate: " + mCurrentDayOfMonth + " " + mCurrentYear + "  " + mCurrentMonth);

        yearNumberPicker.setMaxValue(mCurrentYear - 5);
        yearNumberPicker.setMinValue(1980);
        yearNumberPicker.setWrapSelectorWheel(false);

        dayNumberPicker.setMaxValue(31);
        dayNumberPicker.setMinValue(1);

        monthNumberPicker.setMinValue(0);
        monthNumberPicker.setMaxValue(11);
        monthNumberPicker.setDisplayedValues(sMonths);

        dayNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            mSelectedDay = newVal;
            if (oldVal - newVal > 26) {
                mSelectedMonth += 1;
                if (mSelectedMonth > 11) mSelectedMonth = mSelectedMonth - 12;
                monthNumberPicker.setValue(mSelectedMonth);
                if (mSelectedDay == 1) {
                    checkAndPrepareDayPicker(mSelectedMonth - 1);
                } else {
                    checkAndPrepareDayPicker(mSelectedMonth);
                }
            } else if (oldVal > newVal && oldVal - newVal < 26) {
                if (mSelectedDay == 1) {
                    checkAndPrepareDayPicker(mSelectedMonth - 1);
                } else {
                    checkAndPrepareDayPicker(mSelectedMonth);
                }
            } else if (newVal - oldVal > 26) {
                mSelectedMonth -= 1;
                if (mSelectedMonth < 0) mSelectedMonth = mSelectedMonth + 12;
                monthNumberPicker.setValue(mSelectedMonth);
            } else if (newVal > oldVal && newVal - oldVal < 26) {
                checkAndPrepareDayPicker(mSelectedMonth);
            }

            Log.i(TAG, "MONTH: " + mSelectedMonth);
        });

        monthNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            mSelectedMonth = newVal;
            if (mSelectedDay == 1) {
                checkAndPrepareDayPicker(mSelectedMonth - 1);
            } else if (mSelectedDay > 29 && mSelectedMonth == 1) {
                if (isLeapYear(mSelectedYear)) {
                    prepareDayPicker(1, 29);
                } else {
                    prepareDayPicker(1, 28);
                }
            } else if (mSelectedDay == 31 && (mSelectedMonth == 3
                    || mSelectedMonth == 5
                    || mSelectedMonth == 8
                    || mSelectedMonth == 10)) {
                mSelectedDay = 30;
                prepareDayPicker(1, 30);
            } else {
                checkAndPrepareDayPicker(mSelectedMonth);
            }
        });

        yearNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            mSelectedYear = newVal;
            if (mSelectedDay == 1) {
                checkAndPrepareDayPicker(mSelectedMonth - 1);
            } else {
                checkAndPrepareDayPicker(mSelectedMonth);
            }
        });

        yearNumberPicker.setValue(mSelectedYear);
        dayNumberPicker.setValue(1);
        monthNumberPicker.setValue(0);
        builder.setView(view)
                .setTitle("Select your birthdate")
                .setPositiveButton(R.string.dialog_action_ok, (dialog, which) ->
                        mListener.onDateSelectListener(mSelectedDay
                                + "-" + sMonths[mSelectedMonth]
                                + "-" + mSelectedYear, mSelectedYear+"-"+(mSelectedMonth+1)+"-"+mSelectedDay)
                )
                .setNegativeButton("CANCEL", (dialog, which) -> {});

        return builder.create();
    }

    private void checkAndPrepareDayPicker(int selectedMonth) {
        // 0, 2, 4, 6, 7, 9, 11
        if (selectedMonth == 0
                || selectedMonth == 2
                || selectedMonth == 4
                || selectedMonth == 6
                || selectedMonth == 7
                || selectedMonth == 9
                || selectedMonth == 11) {
            prepareDayPicker(1, 31);
        } else if (selectedMonth == 3
                || selectedMonth == 5
                || selectedMonth == 8
                || selectedMonth == 10) {
            prepareDayPicker(1, 30);
        } else if (selectedMonth == 1) {
            if (isLeapYear(mSelectedYear)) {
                Log.i(TAG, "onCreate: " + true);
                prepareDayPicker(1, 29);
            } else {
                Log.i(TAG, "onCreate: " + false);
                prepareDayPicker(1, 28);
            }
        }
    }

    private void prepareDayPicker(int minDayValue, int maxDayValue) {
        Log.d(TAG, "prepareDayPicker() called with: minDayValue = ["
                + minDayValue
                + "], maxDayValue = ["
                + maxDayValue
                + "]");
        dayNumberPicker.setMaxValue(maxDayValue);
        dayNumberPicker.setMinValue(minDayValue);
        Log.i(TAG, "prepareDayPicker: " + mSelectedDay + " " + maxDayValue);
        if (mSelectedDay >= maxDayValue) {
            mSelectedDay = maxDayValue;
            dayNumberPicker.setValue(maxDayValue);
        } else {
            dayNumberPicker.setValue(mSelectedDay);
        }
    }

    public boolean isLeapYear(int newVal) {
        return newVal % 400 == 0 || newVal % 100 != 0 && newVal % 4 == 0;
    }

    public interface Listener {
        void onDateSelectListener(String selectedDate, String formattedDate);
    }
}
