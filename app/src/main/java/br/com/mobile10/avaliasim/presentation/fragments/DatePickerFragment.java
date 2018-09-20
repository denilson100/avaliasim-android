package br.com.mobile10.avaliasim.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TabHost;

import br.com.mobile10.avaliasim.R;

public class DatePickerFragment extends DialogFragment implements View.OnClickListener {

    private OnDateSelectedListener onDateSelectedListener;

    private TabHost tabHost;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private AppCompatButton butSetDateRange;
    boolean is24HourMode;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(OnDateSelectedListener callback, boolean is24HourMode) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.initialize(callback, is24HourMode);
        return datePickerFragment;
    }

    public void initialize(OnDateSelectedListener callback,
                           boolean is24HourMode) {
        onDateSelectedListener = callback;
        this.is24HourMode = is24HourMode;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date_picker, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        tabHost = (TabHost) root.findViewById(R.id.tabHost);
        butSetDateRange = (AppCompatButton) root.findViewById(R.id.but_set_time_range);
        startDatePicker = (DatePicker) root.findViewById(R.id.start_date_picker);
        butSetDateRange.setOnClickListener(this);
        tabHost.findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec startDatePage = tabHost.newTabSpec("start");
        startDatePage.setContent(R.id.start_date_group);
        startDatePage.setIndicator("Data única");

        tabHost.addTab(startDatePage);
        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

//    public void setOnDateRangeSelectedListener(DatePickerFragment.OnDateSelectedListener callback) {
//        this.onDateSelectedListener = callback;
//    }

    @Override
    public void onClick(View v) {
        dismiss();
        onDateSelectedListener.onDateSelected(startDatePicker.getDayOfMonth(),startDatePicker.getMonth(),startDatePicker.getYear());
    }

    public interface OnDateSelectedListener {
        void onDateSelected(int day, int month, int year);
    }

}