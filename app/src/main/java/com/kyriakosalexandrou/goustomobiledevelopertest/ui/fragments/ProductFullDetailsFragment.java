package com.kyriakosalexandrou.goustomobiledevelopertest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.helpers.BaseProgressBarHelper;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.squareup.picasso.Picasso;

/**
 * Created by Kyriakos on 20/01/2016.
 */
public class ProductFullDetailsFragment extends BaseFragment {
    public static final String TAG = ProductFullDetailsFragment.class.getName();
    private static final String PRODUCT = "PRODUCT";

    private Product mProduct;
    private ImageView mImage;
    private TextView mId;
    private TextView mDescription;
    private TextView mAgeRestricted;
    private TextView mLimitPerPerson;
    private TextView mTitle;
    private TextView mPrice;

    public ProductFullDetailsFragment() {
    }

    public static ProductFullDetailsFragment newInstance(Product product, BaseProgressBarHelper baseProgressBarHelper) {
        ProductFullDetailsFragment fragment = new ProductFullDetailsFragment();
        fragment.setProgressBarHelper(baseProgressBarHelper);
        fragment.mProduct = product;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_full_details, container, false);
        bindViews(view);
        setAdapters();
        setListeners();

        if (savedInstanceState != null) {
            mProduct = (Product) savedInstanceState.getSerializable(PRODUCT);
        }

        setViewsProductValue();
        return view;
    }

    private void setViewsProductValue() {
        if (!mProduct.getImagesContainers().isEmpty()) {
            setViewProductImageFromUrl(mProduct.getImagesContainers().get(0).getSize750().getUrl(), mImage);
        } else {
            mImage.setImageResource(R.drawable.ic_wink);
        }
//
//        if (mProduct.getImagesContainer() != null) {
//            setViewProductImageFromUrl(mProduct.getImagesContainer().getSize750().getUrl(), mImage);
//        } else {
//            mImage.setImageResource(R.drawable.ic_wink);
//        }


        mId.setText(mProduct.getId());
        mTitle.setText(mProduct.getTitle());
        mDescription.setText(mProduct.getDescription());
        mPrice.setText(mProduct.getListPrice());
        mAgeRestricted.setText(String.valueOf(mProduct.getAgeRestricted()));
        mLimitPerPerson.setText(String.valueOf(mProduct.getBoxLimit()));
    }

    private void setViewProductImageFromUrl(String imageURL, ImageView imageview) {
        Picasso.with(getContext())
                .load(imageURL)
                .placeholder(R.drawable.ic_wink)
                .into(imageview);
    }

    @Override
    public void bindViews(View view) {
        mImage = (ImageView) view.findViewById(R.id.image_value);
        mId = (TextView) view.findViewById(R.id.id_value);
        mTitle = (TextView) view.findViewById(R.id.title_value);
        mDescription = (TextView) view.findViewById(R.id.description_value);
        mPrice = (TextView) view.findViewById(R.id.price_value);
        mAgeRestricted = (TextView) view.findViewById(R.id.age_restricted_value);
        mLimitPerPerson = (TextView) view.findViewById(R.id.limit_per_person_value);
    }

    @Override
    public void setAdapters() {
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PRODUCT, mProduct);
    }

}
