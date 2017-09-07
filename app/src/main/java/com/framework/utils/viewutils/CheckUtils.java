//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.framework.utils.viewutils;

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class CheckUtils {
    private static final String SELECT_RUNTIME_PROPERTY = "persist.sys.dalvik.vm.lib";
    private static final String LIB_DALVIK = "libdvm.so";
    private static final String LIB_ART = "libart.so";
    private static final String LIB_ART_D = "libartd.so";

    public CheckUtils() {
    }

    public static boolean isEmpty(Object obj) {
        if(obj == null) {
            return true;
        } else if(obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if(obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else if(obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else {
            if(obj.getClass().isArray()) {
                if(obj instanceof Object[]) {
                    return ((Object[])((Object[])obj)).length == 0;
                }

                if(obj instanceof int[]) {
                    return ((int[])((int[])obj)).length == 0;
                }

                if(obj instanceof long[]) {
                    return ((long[])((long[])obj)).length == 0;
                }

                if(obj instanceof short[]) {
                    return ((short[])((short[])obj)).length == 0;
                }

                if(obj instanceof double[]) {
                    return ((double[])((double[])obj)).length == 0;
                }

                if(obj instanceof float[]) {
                    return ((float[])((float[])obj)).length == 0;
                }

                if(obj instanceof boolean[]) {
                    return ((boolean[])((boolean[])obj)).length == 0;
                }

                if(obj instanceof char[]) {
                    return ((char[])((char[])obj)).length == 0;
                }

                if(obj instanceof byte[]) {
                    return ((byte[])((byte[])obj)).length == 0;
                }
            } else if(obj instanceof EmptyCheckable) {
                return ((EmptyCheckable)obj).isEmpty();
            }

            return false;
        }
    }

    public static boolean isExist(Object obj) {
        return !isEmpty(obj);
    }

    public static <T> boolean isContainsEmpty(T... objs) {
        if(isEmpty(objs)) {
            return true;
        } else {
            Object[] var1 = objs;
            int var2 = objs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Object obj = var1[var3];
                if(isEmpty(obj)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String getCurrentRuntimeValue() {
        try {
            Class e = Class.forName("android.os.SystemProperties");

            try {
                Method e1 = e.getMethod("get", new Class[]{String.class, String.class});
                if(e1 == null) {
                    return "WTF?!";
                } else {
                    try {
                        String e2 = (String)e1.invoke(e, new Object[]{"persist.sys.dalvik.vm.lib", "Dalvik"});
                        return "libdvm.so".equals(e2)?"Dalvik":("libart.so".equals(e2)?"ART":("libartd.so".equals(e2)?"ART debug build":e2));
                    } catch (IllegalAccessException var3) {
                        return "IllegalAccessException";
                    } catch (IllegalArgumentException var4) {
                        return "IllegalArgumentException";
                    } catch (InvocationTargetException var5) {
                        return "InvocationTargetException";
                    }
                }
            } catch (NoSuchMethodException var6) {
                return "SystemProperties.get(String key, String def) method is not found";
            }
        } catch (Throwable var7) {
            return "SystemProperties class is not found";
        }
    }

    public static boolean isOdd(int i) {
        return i % 2 != 0;
    }

    public static boolean isEven(int i) {
        return i % 2 == 0;
    }

    public static boolean isContainsEnum(Enum<?>[] group, Enum<?> child) {
        if(isEmpty(group)) {
            return false;
        } else {
            Enum[] var2 = group;
            int var3 = group.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Enum enums = var2[var4];
                if(enums == child) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasRootAccess(Context ctx) {
        try {
            if(runScriptAsRoot(ctx, "exit 0") == 0) {
                return true;
            }
        } catch (Exception var2) {
            ;
        }

        return false;
    }

    public static int runScriptAsRoot(Context ctx, String script) {
        StringBuilder res = new StringBuilder();
        File file = new File(ctx.getCacheDir(), "secopt.sh");
        CheckUtils.ScriptRunner runner = new CheckUtils.ScriptRunner(file, script, res);
        runner.start();

        try {
            runner.join(40000L);
            if(runner.isAlive()) {
                runner.interrupt();
                runner.join(150L);
                runner.destroy();
                runner.join(50L);
            }
        } catch (InterruptedException var6) {
            ;
        }

        return runner.exitcode;
    }

    private static final class ScriptRunner extends Thread {
        private final File file;
        private final String script;
        private final StringBuilder res;
        public int exitcode = -1;
        private Process exec;

        public ScriptRunner(File file, String script, StringBuilder res) {
            this.file = file;
            this.script = script;
            this.res = res;
        }

        public void run() {
            try {
                this.file.createNewFile();
                String ex = this.file.getAbsolutePath();
                Runtime.getRuntime().exec("chmod 777 " + ex).waitFor();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(this.file));
                if((new File("/system/bin/sh")).exists()) {
                    out.write("#!/system/bin/sh\n");
                }

                out.write(this.script);
                if(!this.script.endsWith("\n")) {
                    out.write("\n");
                }

                out.write("exit\n");
                out.flush();
                out.close();
                this.exec = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(this.exec.getOutputStream());
                os.writeBytes(ex);
                os.flush();
                os.close();
                InputStreamReader r = new InputStreamReader(this.exec.getInputStream());
                char[] buf = new char[1024];
                boolean read = false;

                int read1;
                while((read1 = r.read(buf)) != -1) {
                    if(this.res != null) {
                        this.res.append(buf, 0, read1);
                    }
                }

                r = new InputStreamReader(this.exec.getErrorStream());
                read = false;

                while((read1 = r.read(buf)) != -1) {
                    if(this.res != null) {
                        this.res.append(buf, 0, read1);
                    }
                }

                if(this.exec != null) {
                    this.exitcode = this.exec.waitFor();
                }
            } catch (InterruptedException var11) {
                if(this.res != null) {
                    this.res.append("\nOperation timed-out");
                }
            } catch (Exception var12) {
                if(this.res != null) {
                    this.res.append("\n" + var12);
                }
            } finally {
                this.destroy();
            }

        }

        public synchronized void destroy() {
            if(this.exec != null) {
                this.exec.destroy();
            }

            this.exec = null;
        }
    }
}
