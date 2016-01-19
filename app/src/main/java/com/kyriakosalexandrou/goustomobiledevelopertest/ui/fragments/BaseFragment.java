package com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.BaseProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.interfaces.CommonFragmentUiLogicHelper;


/**
 * Created by Kyriakos on 19/01/2016.
 */
public class BaseFragment extends Fragment implements CommonFragmentUiLogicHelper {
    private static final String TAG = BaseFragment.class.getName();
    private BaseProgressBarHelper mProgressBarHelper;

    public BaseFragment() {
    }

    protected BaseProgressBarHelper getProgressBarHelper() {
        return mProgressBarHelper;
    }

    protected void setProgressBarHelper(BaseProgressBarHelper baseProgressBarHelper) {
        mProgressBarHelper = baseProgressBarHelper;
    }

    @Override
    public void bindViews(View view) {
    }

    @Override
    public void setAdapters() {
    }

    @Override
    public void setListeners() {
    }

}
