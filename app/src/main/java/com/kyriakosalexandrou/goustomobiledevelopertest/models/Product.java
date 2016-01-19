
package com.kyriakosalexandrou.goustomobiledevelopertest.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("list_price")
    @Expose
    private String listPrice;
    @SerializedName("is_vatable")
    @Expose
    private Boolean isVatable;
    @SerializedName("is_for_sale")
    @Expose
    private Boolean isForSale;
    @SerializedName("age_restricted")
    @Expose
    private Boolean ageRestricted;
    @SerializedName("box_limit")
    @Expose
    private Integer boxLimit;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = new ArrayList<Category>();
    @SerializedName("attributes")
    @Expose
    private List<Object> attributes = new ArrayList<Object>();
    @SerializedName("images")
    @Expose
    private List<Object> images = new ArrayList<Object>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku The sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The listPrice
     */
    public String getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice The list_price
     */
    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * @return The isVatable
     */
    public Boolean getIsVatable() {
        return isVatable;
    }

    /**
     * @param isVatable The is_vatable
     */
    public void setIsVatable(Boolean isVatable) {
        this.isVatable = isVatable;
    }

    /**
     * @return The isForSale
     */
    public Boolean getIsForSale() {
        return isForSale;
    }

    /**
     * @param isForSale The is_for_sale
     */
    public void setIsForSale(Boolean isForSale) {
        this.isForSale = isForSale;
    }

    /**
     * @return The ageRestricted
     */
    public Boolean getAgeRestricted() {
        return ageRestricted;
    }

    /**
     * @param ageRestricted The age_restricted
     */
    public void setAgeRestricted(Boolean ageRestricted) {
        this.ageRestricted = ageRestricted;
    }

    /**
     * @return The boxLimit
     */
    public Integer getBoxLimit() {
        return boxLimit;
    }

    /**
     * @param boxLimit The box_limit
     */
    public void setBoxLimit(Integer boxLimit) {
        this.boxLimit = boxLimit;
    }

    /**
     * @return The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * @return The attributes
     */
    public List<Object> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes The attributes
     */
    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return The images
     */
    public List<Object> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<Object> images) {
        this.images = images;
    }

}
