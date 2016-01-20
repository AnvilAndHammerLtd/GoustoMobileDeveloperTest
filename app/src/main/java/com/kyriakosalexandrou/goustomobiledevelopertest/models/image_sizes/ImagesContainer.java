
package com.kyriakosalexandrou.goustomobiledevelopertest.models.image_sizes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * when an image is found from the server then it's data will be
 * stored here in each respective size based on our requested images size
 */
public class ImagesContainer {

    @SerializedName("750")
    @Expose
    private Size750 size750;
    @SerializedName("50")
    @Expose
    private Size50 size50;

    /**
     * @return The _750
     */
    public Size750 getSize750() {
        return size750;
    }

    /**
     * @param size750 The 750
     */
    public void setSize750(Size750 size750) {
        this.size750 = size750;
    }

    /**
     * @return The _50
     */
    public Size50 getSize50() {
        return size50;
    }

    /**
     * @param size50 The 50
     */
    public void setSize50(Size50 size50) {
        this.size50 = size50;
    }

}