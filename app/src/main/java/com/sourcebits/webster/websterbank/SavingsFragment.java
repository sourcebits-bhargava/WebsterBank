package com.sourcebits.webster.websterbank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bhargava Gugamsetty on 11/27/2015.
 */
public class SavingsFragment extends Fragment
    {

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_savings, container, false);

        return rootView;
    }
    }