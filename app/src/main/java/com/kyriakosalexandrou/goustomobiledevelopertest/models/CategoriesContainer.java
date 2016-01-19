
package com.kyriakosalexandrou.goustomobiledevelopertest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoriesContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Category> Categories = new ArrayList<Category>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Category> getCategories() {
        return Categories;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setCategories(List<Category> categories) {
        this.Categories = categories;
    }

}
