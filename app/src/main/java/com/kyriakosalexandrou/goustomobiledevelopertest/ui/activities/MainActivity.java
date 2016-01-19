package com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.SimpleProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments.ProductsFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        setProgressBarHelper(
                BaseActivity.PROGRESS_BAR_HELPER_FACTORY.getProgressBar(
                        this,
                        SimpleProgressBarHelper.ProgressBarSize.FULL_SCREEN
                )
        );

        goToProductsFragment();
    }

    private void goToProductsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ProductsFragment productsFragment = ProductsFragment.newInstance(getProgressBarHelper());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, productsFragment, ProductsFragment.TAG);
        /*
        TODO probably will need to add to backstack as i will implement
        the product details fragment to open on top of the products fragment
         */
        ft.commit();
        fm.executePendingTransactions();
    }
}
