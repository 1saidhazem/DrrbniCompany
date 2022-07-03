package com.example.drrbnicompany.Fragments.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.drrbnicompany.Notification.FcmNotificationsSender;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentNotificationInterviewDateBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.util.Calendar;

public class NotificationInterviewDateFragment extends DialogFragment {

    private FragmentNotificationInterviewDateBinding binding;
    private FcmNotificationsSender notificationsSender;
    private Calendar calendar;
    private int year, month, day;
    public static final String TAG = "InterviewDateDialog";

    //    private ProfileViewModel profileViewModel;
    public NotificationInterviewDateFragment() {
    }

    public static NotificationInterviewDateFragment newInstance() {
        return new NotificationInterviewDateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationInterviewDateBinding.inflate(getLayoutInflater(), container, false);
//        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.notificationChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        month = i1 + 1;
                        String date = day+"/"+month+"/"+year;
                        binding.notificationChooseDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();

            }
        });

        binding.notificationChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected date and time
//                SendAcceptNotification();
            }
        });

        binding.btnChancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void SendAcceptNotification(String recipientToken, String date, String senderName,
                                       String adsId, Activity mActivity) {
        notificationsSender = new FcmNotificationsSender(recipientToken, date, senderName, adsId, mActivity);
        notificationsSender.SendAcceptNotifications();
    }

}