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

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.Util;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.CategoriesEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ErrorEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ProductsEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.BaseProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Category;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.kyriakosalexandrou.goustomobiledevelopertest.services.ProductsServicesMediator;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.activities.BaseActivity;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters.ProductsAdapter;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Kyriakos on 19/01/2016.
 */
public class ProductsFragment extends BaseFragment {
    public static final String TAG = ProductsFragment.class.getName();

    private ListView mProductsList;
    private ProductsAdapter mProductsAdapter;

    private ImageView mTopBannerImage;

    private List<Product> mProducts;
    private List<Category> mCategories;

    private ProductsServicesMediator mProductsServicesMediator;

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
        mProductsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToProductFullDetailsFragment(mProducts.get(position));
            }
        });
    }

    private void goToProductFullDetailsFragment(Product product) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ProductFullDetailsFragment productFullDetailsFragment = ProductFullDetailsFragment.newInstance(product, getProgressBarHelper());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, productFullDetailsFragment, ProductFullDetailsFragment.TAG);
        ft.addToBackStack(ProductFullDetailsFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCategoriesRequest();
        getProductsRequest();
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
        //TODO setup  spinner values here
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
