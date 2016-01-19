package com.kyriakosalexandrou.goustomobiledevelopertest.events;

/**
 * Created by Kyriakos on 19/01/2016.
 * <p/>
 * A generic event for simple error messaged
 */
public class ErrorEvent {
    private String mErrorMessage;

    public ErrorEvent(String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
