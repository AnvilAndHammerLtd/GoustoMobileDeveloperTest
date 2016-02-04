package com.kyriakosalexandrou.goustomobiledevelopertest.models;

import android.content.Context;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;

/**
 * Created by Kyriakos on 22/01/2016.
 */
public class AllCategories extends Category{
    public static final String ID = "id=all10d0e-bf7d-11e5-90a9-02fada0dd3b9";

    public AllCategories(Context context) {
        setId(ID);
        setTitle(context.getResources().getString(R.string.all_categories));
    }
}
