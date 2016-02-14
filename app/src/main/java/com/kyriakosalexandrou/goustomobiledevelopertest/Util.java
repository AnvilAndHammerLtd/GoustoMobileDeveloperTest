package com.kyriakosalexandrou.goustomobiledevelopertest;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Category;
import com.kyriakosalexandrou.goustomobiledevelopertest.models.Product;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Kyriakos on 19/01/2016.
 * <p/>
 * Useful generic re-usable methods
 */
public class Util {

    public static String cleanTextFromSpaces(TextView view) {
        String textWithoutSpaces = null;

        if (view != null) {
            textWithoutSpaces = view.getText().toString();
            textWithoutSpaces = textWithoutSpaces.trim();
        }

        return textWithoutSpaces;
    }

    public static void showToastMessageCentered(Context context, int message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void showToastMessageCentered(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void dismissSoftKeyBoard(Activity activity) {
        View view = activity.getWindow().getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static float getResFloatValue(Context context, int dimenFloatResId) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(dimenFloatResId, typedValue, true);
        return typedValue.getFloat();
    }

    /**
     * gets the root view from the current context
     *
     * @param context the current context
     * @return the root view
     */
    public static ViewGroup getRootView(Context context) {
        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();
        return layout;
    }

    public static LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater LayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
    }


    public static String loadJSONFromAsset(Context context, String filePath) {
        String json;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<Category> createCategories(Context context) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String categoriesData = loadJSONFromAsset(context, "categories.json");

        Type collectionType = new TypeToken<Collection<Category>>() {
        }.getType();
        return gson.fromJson(categoriesData, collectionType);
    }

    public static ArrayList<Product> createProducts(Context context) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ProductsData = loadJSONFromAsset(context, "products.json");

        Type collectionType = new TypeToken<Collection<Product>>() {
        }.getType();
        return gson.fromJson(ProductsData, collectionType);
    }

    public static ArrayList<Product> createProductsWithExtras(Context context) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ProductsWithExtrasData = loadJSONFromAsset(context, "products_with_extras.json");

        Type collectionType = new TypeToken<Collection<Product>>() {
        }.getType();
        return gson.fromJson(ProductsWithExtrasData, collectionType);
    }
}
