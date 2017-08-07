package com.wuye.wuye.utils.storage;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wuye.wuye.WApplication;
import com.wuye.wuye.utils.LogUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Store {

    private static Storage storage;
    private static Storage storageCache;

    static {
        storage = Storage.newStorage(WApplication.getContext(), "payease");
        storageCache = Storage.newStorage(WApplication.getContext(), "CACHE");
    }

    //// TODO: 16/7/15
    public static void refreshStore() {
        storage = Storage.newStorage(WApplication.getContext(), "payease");
        storageCache = Storage.newStorage(WApplication.getContext(), "CACHE");
    }

    public static void remove(String key) {
        if (key == null) {
            return;
        }
        storage.remove(key);
    }

    public static void removeCache(String key) {
        if (key == null) {
            return;
        }
        storageCache.remove(key);
    }

    public static void put(String key, String value) {
        if (key == null || value == null) {
            return;
        }
        storage.putString(key, value);
    }

    public static void putCache(String key, String value) {
        if (key == null || value == null) {
            return;
        }
        storageCache.putString(key, value);
    }

    public static void put(String key, boolean value) {
        storage.putBoolean(key, value);
    }

    public static void put(String key, int value) {
        storage.putInt(key, value);
    }

    public static void put(String key, long value) {
        storage.putLong(key, value);
    }

    public static void put(String key, float value) {
        storage.putFloat(key, value);
    }

    /**
     * 存泛型(List/Map 等...)用这个
     * #ran.feng
     */
    public static void put(String key, Serializable value) {
        storage.putString(key, JSON.toJSONString(value));
    }

    public static void putCache(String key, Serializable value) {
        storageCache.putString(key, JSON.toJSONString(value));
    }

    public static void putCache(String key, int value) {
        storageCache.putInt(key, value);
    }

    public static boolean get(String key, boolean defaultValue) {
        try {
            return storage.getBoolean(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int get(String key, int defValue) {
        try {
            return storage.getInt(key, defValue);
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
            return defValue;
        }
    }

    public static int getCache(String key, int defValue) {
        try {
            return storageCache.getInt(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static String get(String key, String defValue) {
        try {
            return storage.getString(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static String getCache(String key, String defValue) {
        try {
            return storageCache.getString(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long get(String key, long defValue) {
        try {
            return storage.getLong(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static float get(String key, float defValue) {
        try {
            return storage.getFloat(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static <T extends Serializable> T get(String key, Class<T> clazz, T defValue) {
        try {
            T obj = JSON.parseObject(storage.getString(key, null), clazz);
            return obj == null ? defValue : obj;
        } catch (Exception e) {
            return defValue;
        }
    }

    public static <T extends Serializable> T getSerializable(String key, Class<T> clazz, T defValue) {
        try {
            Serializable e = storage.getSerializable(key);
            T obj = JSON.parseObject(e.toString(), clazz);
            return obj == null ? defValue : obj;
        } catch (Exception e) {
            return defValue;
        }
    }

    public static void putSerializable(String key, Serializable value) {
        try {
            storage.putSerializable(key, JSON.toJSONString(value));
        } catch (Exception e) {
        }
    }

    public static <T extends Serializable> T getCache(String key, Class<T> clazz, T defValue) {
        try {
            T obj = JSON.parseObject(storage.getString(key, null), clazz);
            return obj == null ? defValue : obj;
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 取泛型(List/Map 等...)用这个
     * #ran.feng
     */
    public static <T extends Serializable> T get(String key, TypeReference<T> type) {
        try {
            return JSON.parseObject(storage.getString(key, null), type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取泛型(List/Map 等...)用这个
     * #ran.feng
     */
    public static <T extends Serializable> T getCache(String key, TypeReference<T> type) {
        try {
            return JSON.parseObject(storageCache.getString(key, null), type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return 是否成功
     */
    public static boolean saveObject(Object object, String key) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = WApplication.getContext().openFileOutput(key, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            WApplication.getContext().deleteFile(key);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                oos = null;
                fos = null;
            }
        }
        return false;
    }

    public static Object loadObject(String key) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = WApplication.getContext().openFileInput(key);
            ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if (obj != null) {
                return obj;
            }
        } catch (Exception e) {
            WApplication.getContext().deleteFile(key);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                ois = null;
                fis = null;
            }
        }
        return null;
    }

    public static void saveUCPramJsonStr(String paramStr) {
        storage.putString("_ucparamJsonStr", paramStr);
    }

    public static String getUCParamJsonStr() {
        return storage.getString("_ucparamJsonStr", "");
    }

    public static void removeUCPramJsonStr() {
        storage.putString("_ucparamJsonStr", "");
    }


}
