package com.wuye.wuye.utils;

import android.text.TextUtils;

import com.wuye.wuye.WApplication;

import java.lang.Character.UnicodeBlock;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessUtils {
    public static final String ENCRYPT_TYPE_SHA1 = "SHA-1";
    public static final String ENCRYPT_TYPE_MD5 = "MD5";

    public BusinessUtils() {
    }

    public static String fixedDistance(float src) {
        return src < 50.0F ? "小于50米" : (src < 1000.0F ? "约" + Math.round(src / 10.0F) * 10 + "米" : "约" + (float) Math.round(src / 100.0F) / 10.0F + "公里");
    }

    private static boolean checkIDParityBit(String certiCode) {
        boolean flag = false;
        String ai = certiCode.substring(0, 17);
        String[] arrVerifyCode = new String[]{"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int totalmulAiWi = 0;

        int modValue;
        for (modValue = 0; modValue < 17; ++modValue) {
            totalmulAiWi += Integer.parseInt(ai.substring(modValue, modValue + 1)) * wi[modValue];
        }

        modValue = totalmulAiWi % 11;
        String strVerifyCode = arrVerifyCode[modValue];
        String code = ai + strVerifyCode;
        if (!certiCode.toLowerCase(WApplication.getContext().getResources().getConfiguration().locale).equals(code)) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static String replacePrice(String beReplaceStr, String totalPrice, String totalPrize) {
        return replacePrice(beReplaceStr, totalPrice, totalPrize, "", "");
    }

    public static String replacePrice(String beReplaceStr, String totalPrice, String totalPrize, String payAmount, String overagePrice) {
        String price = "";

        try {
            double e = Double.parseDouble(totalPrice);
            if (TextUtils.isEmpty(totalPrize)) {
                totalPrize = "0";
            }

            double rprice = Double.parseDouble(totalPrize);
            price = formatDouble2String(e + rprice);
            return beReplaceStr.replaceAll("\\{price\\}", price == null ? "" : price).replaceAll("\\{dprice\\}", totalPrice == null ? "" : totalPrice).replaceAll("\\{rprice\\}", totalPrize == null ? "" : totalPrize).replaceAll("\\{totalPrice\\}", totalPrice == null ? "" : totalPrice).replaceAll("\\{prepayAmount\\}", payAmount == null ? "" : payAmount).replaceAll("\\{overagePrice\\}", overagePrice == null ? "" : overagePrice);
        } catch (NumberFormatException var10) {
            var10.printStackTrace();
            return beReplaceStr;
        }
    }

    private static boolean checkDate(String year, String month, String day) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd", WApplication.getContext().getResources().getConfiguration().locale);

        try {
            String yearNum = year + month + day;
            simpledateformat.setLenient(false);
            simpledateformat.parse(yearNum);
        } catch (ParseException var7) {
            return false;
        }

        int yearNum1 = Integer.parseInt(year);
        if (yearNum1 >= 1850 && yearNum1 <= DateTimeUtils.getCurrentDateTime().get(1)) {
            String s4 = year + "-" + month + "-" + day;
            Calendar cal = DateTimeUtils.getCalendar(s4);
            return cal.before(DateTimeUtils.getCurrentDateTime());
        } else {
            return false;
        }
    }

    private static int checkCertiCode(String certiCode) {
        try {
            if (certiCode != null && (certiCode.length() == 15 || certiCode.length() == 18)) {
                String exception;
                String s2;
                String s3;
                if (certiCode.length() == 15) {
                    if (!checkFigure(certiCode)) {
                        return 5;
                    }

                    exception = "19" + certiCode.substring(6, 8);
                    s2 = certiCode.substring(8, 10);
                    s3 = certiCode.substring(10, 12);
                    if (!checkDate(exception, s2, s3)) {
                        return 2;
                    }
                }

                if (certiCode.length() == 18) {
                    if (!checkFigure(certiCode.substring(0, 17))) {
                        return 5;
                    }

                    exception = certiCode.substring(6, 10);
                    s2 = certiCode.substring(10, 12);
                    s3 = certiCode.substring(12, 14);
                    if (!checkDate(exception, s2, s3)) {
                        return 2;
                    }

                    if (!checkIDParityBit(certiCode)) {
                        return 3;
                    }
                }

                return 0;
            } else {
                return 1;
            }
        } catch (Exception var4) {
            return 4;
        }
    }

    public static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static int checkPersonName(String name) {
        if (TextUtils.isEmpty(name)) {
            return 5;
        } else {
            String reg1 = "^[一-龥 A-Za-z/]+$";
            if (name.matches("^[一-龥 A-Za-z/]+$")) {
                int length = name.replaceAll("[一-龥]", "aa").length();
                return length > 54 ? 1 : (length < 3 ? 5 : 0);
            } else {
                return 6;
            }
        }
    }

    public static boolean isIdCard(String idCard) {
        return checkCertiCode(idCard) == 0;
    }

    public static boolean checkFigure(String certiCode) {
        try {
            Long.parseLong(certiCode);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean checkPhoneNumber(String number) {
        Pattern p = Pattern.compile("1\\d{10}");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean checkZipcode(String zipCodeStr) {
        Pattern p = Pattern.compile("\\d{6}");
        Matcher m = p.matcher(zipCodeStr);
        return m.matches();
    }

    public static String formatPhoneNum(String phoneNum) {
        String num = phoneNum.replaceAll(" ", "");
        num = num.replace("+86", "");
        return num;
    }

    public static boolean checkTeleNumber(String number) {
        Pattern p = Pattern.compile("[0-9]{7,12}");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean checkEmail(String email) {
        if (!checkChiness(email)) {
            Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
            Matcher m = p.matcher(email);
            return m.matches();
        } else {
            return false;
        }
    }

    public static String formatPhoneNumber(String phoneNumber) {
        String StrTmp = "tel:" + phoneNumber.replace("-", "").replace(" ", "");
        StrTmp = StrTmp.replace("转", "p");
        return StrTmp;
    }

    public static String formatCardNumber(String cardNumber) {
        if (TextUtils.isEmpty(cardNumber)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = cardNumber.toCharArray();
        int number = chars.length / 4 * 4;
        for (int i = 0; i < chars.length; i++) {
            if (i > 0 && i % 4 == 0) {
                sb.append(" ");
            }
            if (i >= number) {
                sb.append(chars[i]);
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    public static String formatPhoneNumberIgnoreExtension(String phoneNumber) {
        String StrTmp = "tel:" + phoneNumber.replace("-", "").replace(" ", "");
        StrTmp = StrTmp.replace("转", "p");

        try {
            return StrTmp.split("p")[0];
        } catch (Exception var3) {
            return StrTmp;
        }
    }

    public static String fixString(String str, int n) {
        return str.length() > n ? str.substring(0, n) + "…" : str;
    }

    public static boolean checkEnglishAndChiness(String str) {
        String reg = "^[一-龥A-Za-z]+$";
        return str.matches("^[一-龥A-Za-z]+$");
    }

    public static boolean checkChiness(String str) {
        String reg = "^[一-龥]+$";
        return str.matches("^[一-龥]+$");
    }

    public static boolean checkEnglish(String str) {
        String reg = "^[A-Za-z]+$";
        return str.matches("^[A-Za-z]+$");
    }

    public static boolean checkChinessEnghlish(String str) {
        String reg = "^[一-龥]+[A-Za-z]+[一-龥]+[一-龥a-zA-Z]*$";
        return str.matches("^[一-龥]+[A-Za-z]+[一-龥]+[一-龥a-zA-Z]*$");
    }

    public static boolean checkEnglishChiness(String str) {
        String reg = "^[A-Za-z]+[一-龥]+[一-龥a-zA-Z]*$";
        return str.matches("^[A-Za-z]+[一-龥]+[一-龥a-zA-Z]*$");
    }

    public static boolean checkPassengerAndContactEnglishName(String name) {
        String reg = "^[A-Za-z]+[///]+[A-Za-z]+$";
        return name.matches("^[A-Za-z]+[///]+[A-Za-z]+$");
    }

    public static String formatFloat2String(float value) {
        String valueStr = value + "";
        int index = valueStr.indexOf(".");
        if (index > -1) {
            String rest = valueStr.substring(index + 1);
            if ("0".equals(rest)) {
                valueStr = valueStr.substring(0, index);
            }
        }

        return valueStr;
    }

    public static String formatDouble2String(String value) {
        return formatDouble2String(Double.parseDouble(value));
    }

    public static String formatDouble2String(double value) {
        String valueStr = value + "";

        try {
            int e = valueStr.indexOf(".");
            if (e > -1) {
                String rest;
                for (rest = valueStr.substring(e + 1); rest.length() > 0 && 48 == rest.charAt(rest.length() - 1); rest = rest.substring(0, rest.length() - 1)) {
                    ;
                }

                if (rest.length() == 0) {
                    valueStr = valueStr.substring(0, e);
                } else {
                    valueStr = valueStr.substring(0, e) + "." + rest;
                }
            }

            return valueStr;
        } catch (Exception var5) {
            return value + "";
        }
    }

//    public static boolean isDawn() {
//        try {
//            Dawn e = GlobalEnv.getInstance().getDawn();
//            if(e != null) {
//                Calendar now = DateTimeUtils.getCurrentDateTime();
//                Calendar start = (Calendar)now.clone();
//                Calendar end = (Calendar)now.clone();
//                DateTimeUtils.setTimeWithHHmm(start, e.start);
//                DateTimeUtils.setTimeWithHHmm(end, e.end);
//                return e.flag == 1 && now.after(start) && now.before(end);
//            }
//        } catch (Throwable var4) {
//        }
//
//        return false;
//    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static String getBigDecimalStr(double value) {
        DecimalFormat format = new DecimalFormat("##0.00");
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        return format.format(value);
    }

    public static boolean checkCardNo(String str) {
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean checkCVV2(String str) {
        Pattern p = Pattern.compile("[0-9]{3,4}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String formatDoublePrice(double value) {
        DecimalFormat df = new DecimalFormat("##########.##");
        return df.format(value);
    }

    public static String formatDoublePrice(String value) {
        try {
            return formatDoublePrice(Double.parseDouble(value));
        } catch (Exception var2) {
            return "0";
        }
    }

    public static double parseDouble(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception var2) {
            return 0.0D;
        }
    }

    public static String twoDigitFormat(int value) {
        StringBuilder mBuilder = new StringBuilder();
        Formatter mFmt = new Formatter(mBuilder, Locale.US);
        mBuilder.delete(0, mBuilder.length());
        mFmt.format("%02d", new Object[]{Integer.valueOf(value)});
        String str = mFmt.toString();
        mFmt.close();
        return str;
    }

    public static String toMD5(String inStr) throws Exception {
        StringBuffer sb = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inStr.getBytes());
        byte[] b = md.digest();

        for (int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                sb.append("0");
            }

            sb.append(Integer.toHexString(i));
        }

        return sb.toString();
    }

    public static int getStringByteLength(String string) {
        if (TextUtils.isEmpty(string)) {
            return 0;
        } else {
            Pattern p = Pattern.compile("[一-\u9fff]");
            Matcher m = p.matcher(string);

            int i;
            for (i = 0; m.find(); ++i) {
                ;
            }

            return string.length() + i;
        }
    }

    public static boolean checkFlightNo(String str) {
        Pattern p = Pattern.compile("^[A-Za-z]{2}[0-9]{4}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
