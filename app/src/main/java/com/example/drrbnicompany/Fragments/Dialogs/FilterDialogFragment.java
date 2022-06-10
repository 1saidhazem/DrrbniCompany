package com.example.drrbnicompany.Fragments.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.drrbnicompany.Models.Filters;
import com.example.drrbnicompany.R;


public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialog";

    public FilterDialogFragment() {
        // Required empty public constructor
    }

    interface FilterListener {
        void onFilter(Filters filters);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false);
    }

    public void resetFilters() {
        /*
        if (mRootView != null) {
            mCategorySpinner.setSelection(0);
            mCitySpinner.setSelection(0);
            mPriceSpinner.setSelection(0);
            mSortSpinner.setSelection(0);
        }
         */
    }

}