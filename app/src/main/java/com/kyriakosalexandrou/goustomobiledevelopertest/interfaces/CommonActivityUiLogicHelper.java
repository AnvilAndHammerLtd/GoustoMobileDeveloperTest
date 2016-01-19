package com.kyriakosalexandrou.goustomobiledevelopertest.interfaces;

import android.os.Bundle;

/**
 * Created by Kyriakos on 19/01/2016.
 * <p/>
 * Have an activity implement this for UI logic help
 */
public interface CommonActivityUiLogicHelper {
    /**
     * <p>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</p>
     *
     * @param savedInstanceState
     * @param layoutId           the layout id to load to the activity
     */
    void onCreate(Bundle savedInstanceState, int layoutId);

    void setAdapters();

    void bindViews();

    void setListeners();
}
