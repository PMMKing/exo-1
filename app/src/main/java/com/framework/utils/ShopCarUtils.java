package com.framework.utils;

import android.text.TextUtils;

import com.framework.app.MainApplication;
import com.page.home.model.ShopCarData;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;

import java.util.Iterator;

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

    public void saveProduct(Product product) {
        if (product == null || TextUtils.isEmpty(product.id)) return;
        ShopCarData shopCarData = getShopCarData();
        Iterator<Product> iterator = shopCarData.products.iterator();
        boolean isHave = false;
        while (iterator.hasNext()) {
            Product next = iterator.next();
            if (TextUtils.equals(next.id, product.id)) {
                next.num += product.num;
                isHave = true;
                break;
            }
        }
        if (!isHave) {
            shopCarData.products.add(product);
        }
        saveShopCardData(shopCarData);
    }

    public boolean removeProduct(Product product) {
        ShopCarData shopCarData = getShopCarData();
        if (shopCarData != null && !ArrayUtils.isEmpty(shopCarData.products)) {
            Iterator<Product> iterator = shopCarData.products.iterator();
            while (iterator.hasNext()) {
                Product next = iterator.next();
                if (next != null && TextUtils.equals(next.id, product.id)) {
                    iterator.remove();
                    saveShopCardData(shopCarData);
                    return true;
                }
            }
        }
        return false;
    }

    public void clearData() {
        saveShopCardData(new ShopCarData());
    }

    public int getShopCarSize() {
        ShopCarData shopCarData = getShopCarData();
        if (shopCarData != null && !ArrayUtils.isEmpty(shopCarData.products)) {
            return shopCarData.products.size();
        } else {
            return 0;
        }
    }

}
