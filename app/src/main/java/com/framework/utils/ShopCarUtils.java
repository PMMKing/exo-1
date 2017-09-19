package com.framework.utils;

import android.text.TextUtils;

import com.framework.app.MainApplication;
import com.page.home.model.ShopCarData;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class ShopCarUtils {

    private static final String SHOPCAR = "shopcar";
    private static ShopCarUtils instance = null;
    private ShopCarData shopCarData;


    private ShopCarUtils() {
    }


    public static ShopCarUtils getInstance() {
        if (instance == null) {
            synchronized (ShopCarUtils.class) {
                if (instance == null) {
                    instance = new ShopCarUtils();
                }
            }
        }
        return instance;
    }


    public void saveShopCardData(ShopCarData data) {
        shopCarData = data;
        SPUtils.put(MainApplication.getInstance(), SHOPCAR, data);
    }

    public ShopCarData getShopCarData() {
        if (shopCarData != null) {
            return shopCarData;
        }
        shopCarData = (ShopCarData) SPUtils.get(MainApplication.getInstance(), SHOPCAR, new ShopCarData());
        if (shopCarData == null) {
            shopCarData = new ShopCarData();
        }
        return shopCarData;
    }

    public void addProduct(Product product) {
        if (product == null || TextUtils.isEmpty(product.id)) return;
        ShopCarData shopCarData = getShopCarData();
        Product temp = shopCarData.products.get(product.id);
        if (temp != null) {
            temp.num += product.num;
        } else {
            shopCarData.products.put(product.id, product);
        }
        saveShopCardData(shopCarData);
    }

    public void subProduct(Product product) {
        if (product == null || TextUtils.isEmpty(product.id)) return;
        ShopCarData shopCarData = getShopCarData();
        Product temp = shopCarData.products.get(product.id);
        if (temp != null) {
            temp.num -= product.num;
        } else {
            shopCarData.products.put(product.id, product);
        }
        saveShopCardData(shopCarData);
    }

    public void saveProduct(Product product) {
        if (product == null || TextUtils.isEmpty(product.id)) return;
        ShopCarData shopCarData = getShopCarData();
        shopCarData.products.put(product.id, product);
        saveShopCardData(shopCarData);
    }

    public void removeProduct(Product product) {
        ShopCarData shopCarData = getShopCarData();
        shopCarData.products.remove(product.id);
    }

    public Product getProductForId(String id) {
        ShopCarData shopCarData = getShopCarData();
        return shopCarData.products.get(id);
    }

    public void clearData() {
        saveShopCardData(new ShopCarData());
    }

    public int getShopCarSize() {
        int size = 0;
        ShopCarData shopCarData = getShopCarData();
        Set<String> strings = shopCarData.products.keySet();
        if (ArrayUtils.isEmpty(strings)) return size;
        for (String s : strings) {
            Product product = shopCarData.products.get(s);
            size += product.num;
        }
        return size;
    }

    public ArrayList<Product> getShopCarList() {
        ShopCarData shopCarData = getShopCarData();
        ArrayList<Product> products = new ArrayList<>();
        Iterator<Map.Entry<String, Product>> iterator = shopCarData.products.entrySet().iterator();
        while (iterator.hasNext()) {
            products.add(iterator.next().getValue());
        }
        return products;
    }

}
