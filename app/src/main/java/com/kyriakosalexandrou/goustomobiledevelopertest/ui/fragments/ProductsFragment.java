package com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.Util;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ProductsEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ErrorEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.BaseProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.kyriakosalexandrou.goustomobiledevelopertest.services.ProductServices;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters.ProductsAdapter;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities.BaseActivity;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Kyriakos on 19/01/2016.
 */
public class ProductsFragment extends BaseFragment {
    public static final String TAG = ProductsFragment.class.getName();

    private ListView mProductsList;
    private ProductsAdapter mProductsAdapter;
    private List<Product> mProducts;
    private ImageView mTopBannerImage;

    public ProductsFragment() {
    }

    public static ProductsFragment newInstance(BaseProgressBarHelper baseProgressBarHelper) {
        ProductsFragment fragment = new ProductsFragment();
        fragment.setProgressBarHelper(baseProgressBarHelper);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        bindViews(view);
        setAdapters();
        setListeners();
        return view;
    }

    @Override
    public void bindViews(View view) {
        mProductsList = (ListView) view.findViewById(R.id.products_list);
        mTopBannerImage = (ImageView) view.findViewById(R.id.top_banner_image);
    }

    @Override
    public void setAdapters() {
        mProductsAdapter = new ProductsAdapter(getContext());
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getProductsRequest();

    }

    private void getProductsRequest() {
        getProgressBarHelper().showProgressBar();
        ProductServices mProductServices = new ProductServices(BaseActivity.REST_ADAPTER);
        ErrorEvent errorEvent = new ErrorEvent(getResources().getString(R.string.get_products_request_failure));
        mProductServices.getProductsRequest(new ProductsEvent(errorEvent));
    }

    public void onEventMainThread(ProductsEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        getProgressBarHelper().hideProgressBar();
        mProducts = event.getProducts();

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
}
