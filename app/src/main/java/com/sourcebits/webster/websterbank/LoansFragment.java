package com.sourcebits.webster.websterbank;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bhargava Gugamsetty on 11/27/2015.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LoansFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_loans, container, false);

        return rootView;
    }

}
