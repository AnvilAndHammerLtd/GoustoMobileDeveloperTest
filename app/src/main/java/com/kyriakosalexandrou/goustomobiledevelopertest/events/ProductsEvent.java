package com.kyriakosalexandrou.goustomobiledevelopertest.events;


import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.kyriakosalexandrou.goustomobiledevelopertest.services.ProductsServicesMediator;

import java.util.List;

/**
 * Created by Kyriakos on 19/01/2016.
 * <p/>
 * The event where the response from the {@link ProductsServicesMediator#getProductsRequest} gets stored
 */
public class ProductsEvent {
    private List<Product> mProducts;
    private ErrorEvent mErrorEvent;

    public ProductsEvent(ErrorEvent errorEvent) {
        mErrorEvent = errorEvent;
    }

    public ErrorEvent getErrorEvent() {
        return mErrorEvent;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        this.mProducts = products;
    }
}