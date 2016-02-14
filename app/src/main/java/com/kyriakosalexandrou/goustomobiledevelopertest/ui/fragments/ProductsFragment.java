package com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private static final String SELECTED_PRODUCT = "SELECTED_PRODUCT";

    private ListView mProductsList;
    private ProductsAdapter mProductsAdapter;

    private ImageView mTopBannerImage;
    private Spinner mCategoriesSpinner;

    private Product mSelectedProduct;
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
            mSelectedProduct = (Product) savedInstanceState.getSerializable(SELECTED_PRODUCT);

            setProductsAdapter();
            setCategoriesSpinnerAdapter();
            ProductFullDetailsFragment productFullDetailsFragment = (ProductFullDetailsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ProductFullDetailsFragment.TAG);

            if (!BaseActivity.isPortrait()) {
                if (productFullDetailsFragment == null) {
                    /*
                    TODO if there isn't a product already selected when rotating to landscape we want to set a default product as selected
                    when doing it this way then something happens to the main_fragment and it does not respond, not sure why this is happening
                     */
//                    mSelectedProduct = mProducts.get(0);
//                    productFullDetailsFragment = ProductFullDetailsFragment.newInstance(mSelectedProduct, getProgressBarHelper());
//                    goToProductFullDetailsFragment(productFullDetailsFragment);
                } else {
                    //TODO maybe if the main fragment contains the details of a product it should be removed??
                }
            }
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
                mSelectedProduct = mProducts.get(position);
                ProductFullDetailsFragment productFullDetailsFragment = ProductFullDetailsFragment.newInstance(mSelectedProduct, getProgressBarHelper());
                goToProductFullDetailsFragment(productFullDetailsFragment);
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

    private void goToProductFullDetailsFragment(ProductFullDetailsFragment productFullDetailsFragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (BaseActivity.isPortrait()) {
            ft.add(R.id.main_fragment, productFullDetailsFragment, ProductFullDetailsFragment.TAG);
            ft.addToBackStack(ProductFullDetailsFragment.TAG);
        } else {
            ft.replace(R.id.right_fragment, productFullDetailsFragment, ProductFullDetailsFragment.TAG);
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
        if (!BaseActivity.isPortrait()) {
            mSelectedProduct = mProducts.get(0);
            ProductFullDetailsFragment productFullDetailsFragment = ProductFullDetailsFragment.newInstance(mSelectedProduct, getProgressBarHelper());
            goToProductFullDetailsFragment(productFullDetailsFragment);
        }
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
        outState.putSerializable(SELECTED_PRODUCT, mSelectedProduct);
        outState.putSerializable(PRODUCTS, (Serializable) mProducts);
        outState.putSerializable(CATEGORIES, (Serializable) mCategories);
    }
}
