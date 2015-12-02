package com.sourcebits.webster.websterbank;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import com.sourcebits.webster.websterbank.LoansFragment;

/**
 * Created by Bhargava Gugamsetty on 11/27/2015.
 */
public class WebsterPagerAdapter extends FragmentPagerAdapter {

    public WebsterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Savings fragment activity
                return new SavingsFragment();
            case 1:
                // Checkings fragment activity
                return new CheckingsFragement();
            case 2:
                // Loans fragment activity
             return new LoansFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}