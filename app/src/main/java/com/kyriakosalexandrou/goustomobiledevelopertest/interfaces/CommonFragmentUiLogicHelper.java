package com.kyriakosalexandrou.goustomobiledevelopertest.interfaces;

import android.view.View;

/**
 * Created by Kyriakos on 19/01/2016.
 * <p/>
 * Have a fragment implement this for UI logic help
 */
public interface CommonFragmentUiLogicHelper {
    void bindViews(View view);

    void setAdapters();

    void setListeners();

}
