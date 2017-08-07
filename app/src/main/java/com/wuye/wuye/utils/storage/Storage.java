//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wuye.wuye.utils.storage;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Storage {
    public static final String DEFAULT_SANDBOX = "hen";
    public static final String DEAFAULT_USER = "visitor";
    private static int canWriteFlag = 0;
    private String mSandbox;
    private IStorage mProxy;

    public static Storage newStorage(Context context) {
        return newStorage(context, "visitor");
    }

    public static Storage newStorage(Context context, String owner) {
        return new Storage(context, owner);
    }

    private static Storage newInnerStorage(Context context, String sanbox) {
        return newInnerStorage(context, sanbox, "visitor");
    }

    private static Storage newInnerStorage(Context context, String sanbox, String owner) {
        return new Storage(context, sanbox, owner);
    }

    public static boolean hasFroyo() {
        return VERSION.SDK_INT >= 8;
    }

    @TargetApi(8)
    private static File getExternalFilesDir(Context context) {
        if(hasFroyo()) {
            File dir1 = context.getExternalFilesDir((String)null);
            return dir1 != null?dir1:context.getFilesDir();
        } else {
            String dir = "/Android/data/" + context.getPackageName() + "/files";
            return new File(Environment.getExternalStorageDirectory(), dir);
        }
    }

    public static File getAppFileDir(Context context) {
        try {
            if("mounted".equals(Environment.getExternalStorageState())) {
                File e = getExternalFilesDir(context);
                if(canWriteFlag == 0) {
                    FileOutputStream fos = null;
                    File testFile = null;

                    try {
                        String e1 = UUID.randomUUID().toString();
                        File testDir = new File(e, e1);
                        if(!testDir.exists()) {
                            testDir.mkdirs();
                        }

                        testFile = new File(testDir, e1);
                        fos = new FileOutputStream(testFile);
                        fos.write(0);
                        fos.flush();
                        canWriteFlag = 1;
                    } catch (Throwable var15) {
                        canWriteFlag = 2;
                    } finally {
                        if(fos != null) {
                            try {
                                fos.close();
                            } catch (IOException var14) {
                                var14.printStackTrace();
                            }

                            testFile.delete();
                            testFile.getParentFile().delete();
                        }

                    }
                }

                if(canWriteFlag == 1) {
                    return e;
                }
            }
        } catch (Throwable var17) {
            var17.printStackTrace();
        }

        return context.getFilesDir();
    }

    public static File getAppDir(Context context) {
        return getAppFileDir(context).getParentFile();
    }

    public static File getFileDir(Context context) {
        String mOwner = getSandbox(context);
        File dir = new File(getAppFileDir(context), mOwner);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    private Storage(Context context, String sandbox, String owner) {
        this.mSandbox = sandbox;
        if(TextUtils.isEmpty(owner)) {
            owner = "visitor";
        }

        boolean isOnSpider;
        try {
            Class file = Class.forName("com.mqunar.core.QunarApkLoader");
            isOnSpider = file != null;
        } catch (Throwable var6) {
            //TODO
            isOnSpider = true;
        }

        if(isOnSpider) {
            this.mProxy = SpStorage.newInstance(context, this.mSandbox, owner);
        } else {
            if(!"mounted".equals(Environment.getExternalStorageState())) {
                throw new RuntimeException("不在spider运行时,手机必须得安装sdcard!");
            }

            File file1 = new File(Environment.getExternalStorageDirectory(), "justravel_file");
            this.mProxy = FileStorage.newInstance(context, new File(new File(file1, this.mSandbox), owner));
        }

    }

    private Storage(Context context, String owner) {
        this(context, getSandbox(context), owner);
    }

    static String getSandbox(Context context) {
        String sandbox = null;

        try {
            Class e = Class.forName("com.mqunar.core.QunarApkLoader");
            Method isSpiderClass = e.getDeclaredMethod("isSpiderClass", new Class[]{String.class});
            Method getPackageName = e.getDeclaredMethod("getPackageName", new Class[]{String.class});
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            StackTraceElement element = elements[2];
            String storagePackageName = Storage.class.getPackage().getName();
            StackTraceElement[] className = elements;
            int realOwner = elements.length;

            for(int i$ = 0; i$ < realOwner; ++i$) {
                StackTraceElement el = className[i$];
                boolean isspider = ((Boolean)isSpiderClass.invoke((Object)null, new Object[]{el.getClassName()})).booleanValue();
                if(isspider && !el.getClassName().startsWith(storagePackageName + ".") && !el.getClassName().startsWith(storagePackageName + "$")) {
                    element = el;
                    break;
                }
            }

            String var15 = element.getClassName();
            Object var16 = getPackageName.invoke((Object)null, new Object[]{var15});
            if(var16 != null) {
                sandbox = var16.toString();
            }
        } catch (ClassNotFoundException var13) {
            sandbox = context.getPackageName();
        } catch (Throwable var14) {
            ;
        }

        if(TextUtils.isEmpty(sandbox)) {
            sandbox = "hen";
        }

        return sandbox;
    }

    public <T extends Serializable> boolean append(String key, T value) {
        String ov = this.getString(key, (String)null);
        return !TextUtils.isEmpty(ov)?this.putString(key, ov + value):(value != null?this.putString(key, String.valueOf(value)):false);
    }

    public boolean putString(String key, String value) {
        return this.mProxy.putString(key, value);
    }

    public boolean putBoolean(String key, boolean value) {
        return this.mProxy.putBoolean(key, value);
    }

    public boolean putBytes(String key, byte[] bs) {
        return this.mProxy.putBytes(key, bs);
    }

    public boolean putInt(String key, int i) {
        return this.mProxy.putInt(key, i);
    }

    public boolean putShort(String key, short i) {
        return this.mProxy.putShort(key, i);
    }

    public boolean putLong(String key, long value) {
        return this.mProxy.putLong(key, value);
    }

    public boolean putFloat(String key, float value) {
        return this.mProxy.putFloat(key, value);
    }

    public boolean putDouble(String key, double value) {
        return this.mProxy.putDouble(key, value);
    }

    public boolean putSerializable(String key, Serializable serializable) {
        return this.mProxy.putSerializable(key, serializable);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.mProxy.getBoolean(key, defValue);
    }

    public <T extends Serializable> T getSerializable(String key, Class<T> clazz, T defValue) {
        return this.mProxy.getSerializable(key, clazz, defValue);
    }

    public <T extends Serializable> T getSerializable(String key, T defValue) {
        return (T) this.mProxy.getSerializable(key, (Class)null, defValue);
    }

    public <T extends Serializable> T getSerializable(String key) {
        return (T) this.mProxy.getSerializable(key, (Class)null, (Serializable)null);
    }

    public byte[] getBytes(String key, byte[] defaultValue) {
        return this.mProxy.getBytes(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return this.mProxy.getInt(key, defaultValue);
    }

    public short getShort(String key, short defValue) {
        return this.mProxy.getShort(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return this.mProxy.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return this.mProxy.getFloat(key, defValue);
    }

    public double getDouble(String key, double defValue) {
        return this.mProxy.getDouble(key, defValue);
    }

    public String getString(String key, String defValue) {
        return this.mProxy.getString(key, defValue);
    }

    public boolean remove(String key) {
        return this.mProxy.remove(key);
    }

    public boolean contains(String key) {
        return this.mProxy.contains(key);
    }

    public Map<String, Object> getAll() {
        return this.mProxy.getAll();
    }

    public List<String> getKeys() {
        return this.mProxy.getKeys();
    }

    public static byte[] openAsset(Context context, String fileName, boolean de) {
        byte[] bytes = null;
        ByteArrayOutputStream arrays = null;
        InputStream in = null;

        try {
            arrays = new ByteArrayOutputStream();
            in = context.getAssets().open(fileName);
            boolean e = false;
            byte[] buffer = new byte[1024];

            int e1;
            while((e1 = in.read(buffer)) != -1) {
                arrays.write(buffer, 0, e1);
            }

            arrays.flush();
            bytes = arrays.toByteArray();
        } catch (Throwable var20) {
            ;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var19) {
                    ;
                }
            }

            if(arrays != null) {
                try {
                    arrays.close();
                } catch (IOException var18) {
                    ;
                }
            }

        }

        if(bytes != null && de) {
            bytes = EggRoll.da(bytes);
        }

        return bytes;
    }

    public static byte[] openAsset(Context context, String fileName) {
        return openAsset(context, fileName, true);
    }

    public String getOwner() {
        return this.mSandbox;
    }

    public boolean clean() {
        return this.mProxy.cleanAllStorage();
    }
}
