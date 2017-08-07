package com.wuye.wuye.utils.simpleutils;



import com.wuye.wuye.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by shucheng.qu on 2016/9/23 023.
 */

public class FileMD5Verify {


    public static boolean verifyInstallPackage(String packagePath, String crc) {
//        try {
        try {
            MessageDigest sig = MessageDigest.getInstance("MD5");
            File packageFile = new File(packagePath);
            InputStream signedData = new FileInputStream(packageFile);
            byte[] buffer = new byte[4096];//每次检验的文件区大小
            long toRead = packageFile.length();
            long soFar = 0;
            boolean interrupted = false;
            while (soFar < toRead) {
                interrupted = Thread.interrupted();
                if (interrupted) break;
                int read = signedData.read(buffer);
                soFar += read;
                sig.update(buffer, 0, read);
            }
            byte[] digest = sig.digest();
            String digestStr = bytesToHexString(digest);//将得到的MD5值进行移位转换
//            digestStr = digestStr.toLowerCase();
//            crc = crc.toLowerCase();
            LogUtils.d("下载文件 ：： " + digestStr + "  ::  " + crc + "  ::  " + digestStr.equals(crc));
            return digestStr.equalsIgnoreCase(crc);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        int i = 0;
        while (i < src.length) {
            int v;
            String hv;
            v = (src[i] >> 4) & 0x0F;
            hv = Integer.toHexString(v);
            stringBuilder.append(hv);
            v = src[i] & 0x0F;
            hv = Integer.toHexString(v);
            stringBuilder.append(hv);
            i++;
        }
        return stringBuilder.toString();
    }


}
