package com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.AllCategories;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Category;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.image_sizes.ImagesContainer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyriakos on 19/01/2016.
 */
public class ProductsAdapter extends BaseAdapter {
    private List<Product> mProducts = new ArrayList<>();
    private List<Product> mFilteredProducts = new ArrayList<>();
    private Context mContext;
    private ProductFilterByTitle mProductFilterByTitle = new ProductFilterByTitle();
    private ProductFilterByID mProductFilterByID = new ProductFilterByID();

    public ProductsAdapter(Context context) {
        mContext = context;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }


    @Override
    public int getCount() {
        return mFilteredProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mFilteredProducts.get(position);
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

        Product product = mFilteredProducts.get(position);

        List<ImagesContainer> imagesContainers = product.getImagesContainers();
        if (imagesContainers != null && !imagesContainers.isEmpty()) {
            String mainProductImage = imagesContainers.get(0).getSize750().getUrl();
            setProductImage(mainProductImage, imageview);
        } else {
            imageview.setImageResource(R.drawable.ic_wink);
        }

//        ImagesContainer imagesContainer = product.getImagesContainer();
//        if(imagesContainer != null && imagesContainer != null){
//            String mainProductImage = imagesContainer.getSize750().getUrl();
//            setProductImage(mainProductImage, imageview);
//        } else{
//            imageview.setImageResource(R.drawable.ic_wink);
//        }

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

    public enum FilterBy {
        ID, TITLE
    }

    /**
     * filter the products based on a category type
     *
     * @param category the category object to use for filtering
     * @param filterBy to filter the category based on either {@link FilterBy#ID} or {@link FilterBy#TITLE}
     */
    public void filterByType(Category category, FilterBy filterBy) {
        switch (filterBy) {
            case ID:
                mProductFilterByID.filter(category.getId());
                break;
            case TITLE:
                mProductFilterByTitle.filter(category.getTitle());
                break;
        }
    }

    private class ProductFilterByID extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            constraint = constraint.toString().toLowerCase();

            if (constraint == null || constraint.length() == 0 || constraint.equals(AllCategories.ID.toLowerCase())) {
                results.values = mProducts;
                results.count = mProducts.size();
            } else {

                ArrayList<Product> filteredProducts = new ArrayList<Product>();

                for (Product product : mProducts) {
                    for (int i = 0; i < product.getCategories().size(); i++) {
                        Category category = product.getCategories().get(i);
                        String id = category.getId().toString().toLowerCase();

                        if (id.equals(constraint)) {
                            filteredProducts.add(product);
                        }
                    }
                }
                results.values = filteredProducts;
                results.count = filteredProducts.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredProducts = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }

    private class ProductFilterByTitle extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            constraint = constraint.toString().toLowerCase();
            String allCategories = mContext.getResources().getString(R.string.all_categories);

            if (constraint == null || constraint.length() == 0 || constraint.equals(allCategories.toLowerCase())) {
                results.values = mProducts;
                results.count = mProducts.size();
            } else {

                ArrayList<Product> filteredProducts = new ArrayList<Product>();

                for (Product product : mProducts) {
                    for (int i = 0; i < product.getCategories().size(); i++) {
                        Category category = product.getCategories().get(i);
                        String title = category.getTitle().toString().toLowerCase();

                        if (title.equals(constraint)) {
                            filteredProducts.add(product);
                        }
                    }
                }
                results.values = filteredProducts;
                results.count = filteredProducts.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredProducts = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }
}
