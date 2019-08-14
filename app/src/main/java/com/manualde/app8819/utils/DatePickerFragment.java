package com.manualde.app8819.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerFragmentOnDateSetListener datePickerFragmentOnDateSetListener;

    public DatePickerFragment(DatePickerFragmentOnDateSetListener datePickerFragmentOnDateSetListener) {
        this.datePickerFragmentOnDateSetListener = datePickerFragmentOnDateSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        datePickerFragmentOnDateSetListener.onDateSet(view, year, month, day);
    }

    public interface DatePickerFragmentOnDateSetListener {
        void onDateSet(DatePicker view, int year, int month, int day);
    }

}
