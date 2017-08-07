package com.wuye.wuye.net.base;


import com.wuye.wuye.utils.framwork.ArrayUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class BaseParam implements Serializable {

    public String service;

    public HashMap toHashMap() {
        Field[] declaredFields = this.getClass().getFields();
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!ArrayUtils.isEmpty(declaredFields)) {
            for (Field field : declaredFields) {
                if (field != null) {
                    try {
                        if (field.get(this) != null) {
                            hashMap.put(field.getName(), field.get(this));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return hashMap;
    }
}
