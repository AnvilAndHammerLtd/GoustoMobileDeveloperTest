package com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.BaseProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.FactoryProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.interfaces.CommonActivityUiLogicHelper;

import retrofit.RestAdapter;

/**
 * Created by Kyriakos on 19/01/2016.
 */
public class BaseActivity extends AppCompatActivity implements CommonActivityUiLogicHelper {
    private static final String TAG = BaseActivity.class.getName();
    public static final String BASE_URL = "https:/";
    public static final FactoryProgressBarHelper PROGRESS_BAR_HELPER_FACTORY = new FactoryProgressBarHelper();
    public static RestAdapter REST_ADAPTER = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BASE_URL).build();
    private BaseProgressBarHelper mProgressBarHelper;

    public BaseProgressBarHelper getProgressBarHelper() {
        return mProgressBarHelper;
    }

    public void setProgressBarHelper(BaseProgressBarHelper baseProgressBarHelper) {
        mProgressBarHelper = baseProgressBarHelper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
    }

    @Override
    public void bindViews() {
    }

    @Override
    public void setAdapters() {
    }

    @Override
    public void setListeners() {
    }
}