//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wuye.wuye.utils.storage;


public class EggRoll {
    private static De de = new GoblinDe();

    public EggRoll() {
    }

    public static void setDe(De de) {
        de = de;
    }

    public static byte[] ea(byte[] param) {
        return de.ea(param);
    }

    public static byte[] da(byte[] param) {
        return de.da(param);
    }

    public static class GoblinDe implements De {
        public GoblinDe() {
        }

        public byte[] ea(byte[] param) {
            try {
//                return Goblin.ea(param);
                return param;
            } catch (Throwable var3) {
                return param;
            }
        }

        public byte[] da(byte[] param) {
            try {
//                return Goblin.da(param);
                return param;
            } catch (Throwable var3) {
                return param;
            }
        }
    }

    public interface De {
        byte[] ea(byte[] var1);

        byte[] da(byte[] var1);
    }
}
