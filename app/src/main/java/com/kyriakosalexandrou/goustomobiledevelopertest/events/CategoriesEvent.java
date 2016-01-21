package com.kyriakosalexandrou.goustomobiledevelopertest.events;

import com.kyriakosalexandrou.goustomobiledevelopertest.models.Category;

import java.util.List;

/**
 * Created by Kyriakos on 21/01/2016.
 */
public class CategoriesEvent {
    private List<Category> mCategories;
    private ErrorEvent mErrorEvent;

    public CategoriesEvent(ErrorEvent errorEvent) {
        mErrorEvent = errorEvent;
    }

    public ErrorEvent getErrorEvent() {
        return mErrorEvent;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        this.mCategories = categories;
    }
}
