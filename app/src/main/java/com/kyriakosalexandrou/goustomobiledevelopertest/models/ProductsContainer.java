
package com.kyriakosalexandrou.goustomobiledevelopertest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductsContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Product> mProducts = new ArrayList<Product>();

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The products
     */
    public List<Product> getProducts() {
        return mProducts;
    }

    /**
     * @param products The products
     */
    public void setProduct(List<Product> products) {
        this.mProducts = products;
    }

}
