package com.kyriakosalexandrou.goustomobiledevelopertest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Spinner;

import com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities.MainActivity;

/**
 * Created by Kyriakos on 14/02/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    @SmallTest
    public void testCategoryChanging() throws Throwable {
        final Spinner spinner = (Spinner) getActivity().findViewById(R.id.categories_spinner);
        assertNotNull(spinner);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(0);
                spinner.setSelection(1);
                spinner.setSelection(2);
                spinner.setSelection(3);
                spinner.setSelection(4);
            }
        });
    }


}
