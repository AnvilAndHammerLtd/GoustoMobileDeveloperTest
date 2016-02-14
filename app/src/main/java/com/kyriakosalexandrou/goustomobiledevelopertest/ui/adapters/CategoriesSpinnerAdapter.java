package com.kyriakosalexandrou.goustomobiledevelopertest.ui.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kyriakosalexandrou.goustomobiledevelopertest.R;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Category;

import java.util.List;

/**
 * Created by Kyriakos on 21/01/2016.
 */
public class CategoriesSpinnerAdapter implements SpinnerAdapter {

    private Context mContext;
    private List<Category> mCategories;
    private ViewHolder mViewHolder = new ViewHolder();

    public void setCategories(List<Category> mCategories) {
        this.mCategories = mCategories;
    }

    public CategoriesSpinnerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    private View getItemView(int position, View convertView, ViewGroup parent) {
        /*
        using the view holder pattern to reuse views. This is probably an overkill as it currently
        seems there aren't a lot of categories. However, requirements change and we should build in
        such a way
        */
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
            TextView text = (TextView) convertView.findViewById(R.id.title);
            mViewHolder.setText(text);
        }

        mViewHolder.getText().setText(mCategories.get(position).getTitle());
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Category getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private class ViewHolder {
        private TextView text;

        public TextView getText() {
            return text;
        }

        public void setText(TextView text) {
            this.text = text;
        }
    }
}


