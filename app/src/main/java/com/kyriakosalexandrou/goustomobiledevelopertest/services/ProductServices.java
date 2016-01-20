package com.kyriakosalexandrou.goustomobiledevelopertest.services;

import android.util.Log;

import com.kyriakosalexandrou.goustomobiledevelopertest.events.ErrorEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.events.ProductsEvent;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.ProductsContainer;
import com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kyriakos on 19/01/2016.
 * <p/>
 * Requests that have to do with products.
 */
public class ProductServices {
    private static final String TAG = ProductServices.class.getName();
    private IProductServices mService;

    public ProductServices(RestAdapter restAdapter) {
        mService = restAdapter.create(IProductServices.class);
    }

    /**
     * sends a request to retrieve a list of products.
     * <p/>
     * on success the {@link ProductsFragment#onEventMainThread(ProductsEvent event)}
     * <br/>
     * on failure the {@link ProductsFragment#onEventMainThread(ErrorEvent event)}
     *
     * @param event the ProductsEvent to store the retrieved data
     */
    public void getProductsRequest(final ProductsEvent event) {

        ArrayList<String> includes = new ArrayList<>();
        includes.add("categories");
        includes.add("attributes");

        ArrayList<Integer> imageSizes = new ArrayList<>();
        imageSizes.add(Integer.valueOf(750));

//        ArrayList<String> imageSizes = new ArrayList<>();
//        imageSizes.add(String.valueOf(750));
//        imageSizes.add(String.valueOf(50));

        mService.getProducts(
                includes,
                null,
                new Callback<ProductsContainer>() {

                    @Override
                    public void success(ProductsContainer productsContainer, Response response) {
                        Log.v(TAG, "getProductsRequest success");
                        event.setProducts(productsContainer.getProducts());
                        EventBus.getDefault().postSticky(event);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.v(TAG, "getProductsRequest failure");
                        Log.v(TAG, "getProductsRequest getUrl: " + error.getUrl());
                        Log.v(TAG, "getProductsRequest getMessage: " + error.getMessage());
                        EventBus.getDefault().postSticky(event.getErrorEvent());
                    }
                }
        );
    }

    public interface IProductServices {
        @GET("//api.gousto.co.uk/products/v2.0/products")
        void getProducts(@Query("includes[]") List<String> includes,
                         @Query("image_sizes[]") List<Integer> imageSizes,
                         Callback<ProductsContainer> response);

//        @GET("//api.gousto.co.uk/products/v2.0/products")
//        void getProducts(@Query("includes[]") List<String> includes,
//                         @Query("image_sizes[]") List<Integer> imageSizes,
//                         Callback<ProductsContainer> response);
    }
}