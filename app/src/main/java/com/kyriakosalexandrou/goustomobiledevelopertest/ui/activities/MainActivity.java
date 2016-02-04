package com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.SimpleProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments.ProductsFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    private ProductsFragment mProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        setProgressBarHelper(
                BaseActivity.PROGRESS_BAR_HELPER_FACTORY.getProgressBar(
                        this,
                        SimpleProgressBarHelper.ProgressBarSize.FULL_SCREEN
                )
        );

        if (savedInstanceState != null) {
            mProductsFragment = (ProductsFragment) getSupportFragmentManager().getFragment(savedInstanceState, ProductsFragment.TAG);
        } else {
            goToProductsFragment();
        }
    }

    private void goToProductsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        mProductsFragment = ProductsFragment.newInstance(getProgressBarHelper());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_fragment, mProductsFragment, ProductsFragment.TAG);
        ft.addToBackStack(ProductsFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, ProductsFragment.TAG, mProductsFragment);
    }
}
