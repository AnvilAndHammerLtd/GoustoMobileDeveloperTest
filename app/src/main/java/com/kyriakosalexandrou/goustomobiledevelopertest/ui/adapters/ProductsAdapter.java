package com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Image;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyriakos on 19/01/2016.
 */
public class ProductsAdapter extends BaseAdapter {
    private List<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public ProductsAdapter(Context context) {
        mContext = context;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }


    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TODO should implement the view holder pattern
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product, null);
        }
        ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        Product product = mProducts.get(position);


        List<Image> images = product.getImages();
        if(images != null && !images.isEmpty()){
            String mainProductImage = images.get(0).getUrl();
            setProductImage(mainProductImage, imageview);
        } else{
            imageview.setImageResource(R.drawable.ic_wink);
        }


        name.setText(product.getTitle());
        description.setText(product.getDescription());

        return convertView;
    }

    private void setProductImage(String imageURL, ImageView imageview) {
        Picasso.with(mContext)
                .load(imageURL)
                .placeholder(R.drawable.ic_wink)
                .into(imageview);
    }
}
