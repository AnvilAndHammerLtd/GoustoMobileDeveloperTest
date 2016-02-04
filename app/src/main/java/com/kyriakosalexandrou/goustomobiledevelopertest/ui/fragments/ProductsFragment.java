package com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.Util;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.CategoriesEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ErrorEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ProductsEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.BaseProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.AllCategories;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Category;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.kyriakosalexandrou.goustomobiledevelopertest.services.ProductsServicesMediator;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities.BaseActivity;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters.CategoriesSpinnerAdapter;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters.ProductsAdapter;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Kyriakos on 19/01/2016.
 */
public class ProductsFragment extends BaseFragment {
    public static final String TAG = ProductsFragment.class.getName();
    private static final String PRODUCTS = "PRODUCTS";
    private static final String CATEGORIES = "CATEGORIES";

    private ListView mProductsList;
    private ProductsAdapter mProductsAdapter;

    private ImageView mTopBannerImage;
    private Spinner mCategoriesSpinner;

    private List<Product> mProducts;
    private List<Category> mCategories;

    private ProductsServicesMediator mProductsServicesMediator;
    private CategoriesSpinnerAdapter mCategoriesSpinnerAdapter;

    public ProductsFragment() {
    }

    public static ProductsFragment newInstance(BaseProgressBarHelper baseProgressBarHelper) {
        ProductsFragment fragment = new ProductsFragment();
        fragment.setProgressBarHelper(baseProgressBarHelper);
        fragment.mProductsServicesMediator = new ProductsServicesMediator(BaseActivity.REST_ADAPTER);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        bindViews(view);
        setAdapters();
        setListeners();

        if (savedInstanceState != null) {
            mProducts = (List<Product>) savedInstanceState.getSerializable(PRODUCTS);
            mCategories = (List<Category>) savedInstanceState.getSerializable(CATEGORIES);

            setProductsAdapter();
            setCategoriesSpinnerAdapter();
            mProductFullDetailsFragment = (ProductFullDetailsFragment) getActivity().getSupportFragmentManager().getFragment(savedInstanceState, ProductFullDetailsFragment.TAG);

        } else {
            getProductsRequest();
        }

        return view;
    }

    @Override
    public void bindViews(View view) {
        mProductsList = (ListView) view.findViewById(R.id.products_list);
        mTopBannerImage = (ImageView) view.findViewById(R.id.top_banner_image);
        mCategoriesSpinner = (Spinner) view.findViewById(R.id.categories_spinner);
    }

    @Override
    public void setListeners() {
        mProductsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToProductFullDetailsFragment(mProducts.get(position));
            }
        });

        mCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategoriesSpinner.setSelection(position);
                Category category = mCategoriesSpinnerAdapter.getItem(position);
                mProductsAdapter.filterByCategory(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private ProductFullDetailsFragment mProductFullDetailsFragment;

    private void goToProductFullDetailsFragment(Product product) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        mProductFullDetailsFragment = ProductFullDetailsFragment.newInstance(product, getProgressBarHelper());
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(R.id.main_fragment);

        Log.v(TAG, "back stack count: " + fm.getBackStackEntryCount());


        if (getResources().getBoolean(R.bool.is_landscape)) {
            ft.replace(R.id.right_fragment, mProductFullDetailsFragment, ProductFullDetailsFragment.TAG);

            Log.v(TAG, "landscape REPLACING");

        } else if(fragment != null && (fragment instanceof ProductsFragment)){
            Log.v(TAG, "portrait ADD");
            ft.add(R.id.main_fragment, mProductFullDetailsFragment, ProductFullDetailsFragment.TAG);
            ft.addToBackStack(ProductFullDetailsFragment.TAG);
        }

        ft.commit();
        fm.executePendingTransactions();
    }

    private void getProductsRequest() {
        getProgressBarHelper().showProgressBar();
        ErrorEvent errorEvent = new ErrorEvent(getResources().getString(R.string.get_products_request_failure));
        mProductsServicesMediator.getProductsRequest(new ProductsEvent(errorEvent));
    }

    private void getCategoriesRequest() {
        getProgressBarHelper().showProgressBar();
        ErrorEvent errorEvent = new ErrorEvent(getResources().getString(R.string.get_categories_request_failure));
        mProductsServicesMediator.getCategoriesRequest(new CategoriesEvent(errorEvent));
    }

    public void onEventMainThread(CategoriesEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        getProgressBarHelper().hideProgressBar();
        mCategories = event.getCategories();
        mCategories.add(new AllCategories(getContext()));
        setCategoriesSpinnerAdapter();

        goToProductFullDetailsFragment(mProducts.get(0));
    }

    private void setCategoriesSpinnerAdapter() {
        mCategoriesSpinnerAdapter = new CategoriesSpinnerAdapter(getContext());
        mCategoriesSpinnerAdapter.setCategories(mCategories);
        mCategoriesSpinner.setAdapter(mCategoriesSpinnerAdapter);
        mCategoriesSpinner.setSelection(mCategoriesSpinner.getCount() - 1);
    }

    public void onEventMainThread(ProductsEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        mProducts = event.getProducts();
        setProductsAdapter();
        getCategoriesRequest();
    }

    private void setProductsAdapter() {
        mProductsAdapter = new ProductsAdapter(getContext());
        mProductsAdapter.setProducts(mProducts);
        mProductsList.setAdapter(mProductsAdapter);
    }

    public void onEventMainThread(ErrorEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        getProgressBarHelper().hideProgressBar();
        Util.showToastMessageCentered(getContext(), event.getErrorMessage());
    }

    @Override
    public void onStart() {
        EventBus.getDefault().registerSticky(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(PRODUCTS, (Serializable) mProducts);
        outState.putSerializable(CATEGORIES, (Serializable) mCategories);
        if (mProductFullDetailsFragment != null && mProductFullDetailsFragment.isAdded()) {
            getActivity().getSupportFragmentManager().putFragment(outState, ProductFullDetailsFragment.TAG, mProductFullDetailsFragment);
        }
    }
}
