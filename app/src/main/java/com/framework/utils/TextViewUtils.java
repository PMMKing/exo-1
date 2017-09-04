package com.justravel.flight.framework.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * Created by meng.jiang flight_on 2015/4/7.
 */
public class TextViewUtils {
    /**
     * 设置带颜色字符串
     *
     * @param str               输入字符串
     * @param genericColorValue 颜色值
     * @param textColorSegments 一维数组 例: [5,10,15,20]的意思就是位置5到10之间变色 位置15到20之间变色
     */
    public static CharSequence genericColorfulText(String str, int genericColorValue, int[] textColorSegments) {
        if (textColorSegments != null) {
            if (textColorSegments.length == 0 || textColorSegments.length % 2 != 0) {
                return new SpannableStringBuilder(str);
            }
            int[][] alteredTextColorSegments = new int[textColorSegments.length / 2][];
            for (int i = 0; i + 2 <= textColorSegments.length; i = i + 2) {

                int[] tmpSegment = new int[2];
                tmpSegment[0] = textColorSegments[i];
                tmpSegment[1] = textColorSegments[i + 1];
                alteredTextColorSegments[i / 2] = tmpSegment;
            }
            return genericColorfulText(str, genericColorValue, alteredTextColorSegments);
        } else {
            return new SpannableStringBuilder(str);
        }
    }


    /**
     * @param str               输入字符串
     * @param genericColorValue 颜色值
     * @param textColor         变颜色的文字
     * @return
     */
    public static CharSequence genericColorfulText(String str, int genericColorValue, String textColor) {
        if (TextUtils.isEmpty(textColor) || TextUtils.isEmpty(str)) {
            return str;
        }

        int index = str.toLowerCase().indexOf(textColor.toLowerCase()), length = textColor.length();

        if (index >= 0) {
            return genericColorfulText(str, genericColorValue, new int[][]{new int[]{index, index+length}});
        } else {
            return new SpannableStringBuilder(str);
        }
    }


    public static CharSequence genericColorfulText(String str, int genericColorValue, int[][] textColorSegments) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        for (int[] textColorSegment : textColorSegments) {
            if (textColorSegment[0] > -1 && textColorSegment[1] <= ssb.length()) {
                ssb.setSpan(new ForegroundColorSpan(genericColorValue),
                        textColorSegment[0], textColorSegment[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    /**
     * 设置不同size字符串
     *
     * @param str              输入字符串
     * @param genericSizeValue 字符size
     * @param textSizeSegments 一维数组 例: [5,10,15,20]的意思就是位置5到10之间一个size 位置15到20之间一个size
     */
    public static CharSequence genericSizefulText(String str, int genericSizeValue, int[] textSizeSegments) {
        if (textSizeSegments != null) {
            if (textSizeSegments.length == 0 || textSizeSegments.length % 2 != 0) {
                return new SpannableStringBuilder(str);
            }
            int[][] alteredTextSizeSegments = new int[textSizeSegments.length / 2][];
            for (int i = 0; i + 2 <= textSizeSegments.length; i = i + 2) {

                int[] tmpSegment = new int[2];
                tmpSegment[0] = textSizeSegments[i];
                tmpSegment[1] = textSizeSegments[i + 1];
                alteredTextSizeSegments[i / 2] = tmpSegment;
            }
            return genericSizefulText(str, genericSizeValue, alteredTextSizeSegments);
        } else {
            return new SpannableStringBuilder(str);
        }
    }

    public static CharSequence genericSizefulText(String str, int genericSizeValue, int[][] textSizeSegments) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        for (int[] textSizeSegment : textSizeSegments) {
            if (textSizeSegment[0] > -1 && textSizeSegment[1] <= ssb.length()) {
                ssb.setSpan(new AbsoluteSizeSpan(genericSizeValue),
                        textSizeSegment[0], textSizeSegment[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    /**
     * 设置不同size,color字符串
     *
     * @param str                   输入字符串
     * @param genericSizeValue      字符size
     * @param genericColorValue     字符color
     * @param textSizeColorSegments 一维数组 例: [5,10,15,20]的意思就是位置5到10之间一个size 位置15到20之间一个size
     */
    public static CharSequence genericSizeColorfulText(String str, int genericSizeValue, int genericColorValue, int[] textSizeColorSegments) {
        if (textSizeColorSegments != null) {
            if (textSizeColorSegments.length == 0 || textSizeColorSegments.length % 2 != 0) {
                return new SpannableStringBuilder(str);
            }
            int[][] alteredTextSizeColorSegments = new int[textSizeColorSegments.length / 2][];
            for (int i = 0; i + 2 <= textSizeColorSegments.length; i = i + 2) {

                int[] tmpSegment = new int[2];
                tmpSegment[0] = textSizeColorSegments[i];
                tmpSegment[1] = textSizeColorSegments[i + 1];
                alteredTextSizeColorSegments[i / 2] = tmpSegment;
            }
            return genericSizeColorfulText(str, genericSizeValue, genericColorValue, alteredTextSizeColorSegments);
        } else {
            return new SpannableStringBuilder(str);
        }
    }

    public static CharSequence genericSizeColorfulText(String str, int genericSizeValue, int genericColorValue, int[][] textSizeColorSegments) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        for (int[] textSizeColorSegment : textSizeColorSegments) {
            if (textSizeColorSegment[0] > -1 && textSizeColorSegment[1] <= ssb.length()) {
                ssb.setSpan(new AbsoluteSizeSpan(genericSizeValue),
                        textSizeColorSegment[0], textSizeColorSegment[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new ForegroundColorSpan(genericColorValue),
                        textSizeColorSegment[0], textSizeColorSegment[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    /**
     * 设置不同size,color字符串
     *
     * @param str
     * @param genericSizeValue
     * @param genericColorValue
     * @param textSizeSegments
     * @param textColorSegments
     * @return
     */
    public static CharSequence genericSizeColorfulText(String str, int genericSizeValue, int genericColorValue, int[] textSizeSegments, int[] textColorSegments) {
        if (textSizeSegments != null && textColorSegments != null) {
            if (textSizeSegments.length == 0 || textSizeSegments.length % 2 != 0) {
                return new SpannableStringBuilder(str);
            }
            int[][] alteredTextSizeSegments = new int[textSizeSegments.length / 2][];
            for (int i = 0; i + 2 <= textSizeSegments.length; i = i + 2) {

                int[] tmpSegment = new int[2];
                tmpSegment[0] = textSizeSegments[i];
                tmpSegment[1] = textSizeSegments[i + 1];
                alteredTextSizeSegments[i / 2] = tmpSegment;
            }

            if (textColorSegments.length == 0 || textColorSegments.length % 2 != 0) {
                return new SpannableStringBuilder(str);
            }
            int[][] alteredTextColorSegments = new int[textColorSegments.length / 2][];
            for (int i = 0; i + 2 <= textColorSegments.length; i = i + 2) {

                int[] tmpSegment = new int[2];
                tmpSegment[0] = textColorSegments[i];
                tmpSegment[1] = textColorSegments[i + 1];
                alteredTextColorSegments[i / 2] = tmpSegment;
            }

            return genericSizeColorfulText(str, genericSizeValue, genericColorValue, alteredTextSizeSegments, alteredTextColorSegments);
        } else {
            return new SpannableStringBuilder(str);
        }
    }

    public static CharSequence genericSizeColorfulText(String str, int genericSizeValue, int genericColorValue, int[][] textSizeSegments, int[][] textColorSegments) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        for (int[] textSizeSegment : textSizeSegments) {
            if (textSizeSegment[0] > -1 && textSizeSegment[1] <= ssb.length()) {
                ssb.setSpan(new AbsoluteSizeSpan(genericSizeValue),
                        textSizeSegment[0], textSizeSegment[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        for (int[] textColorSegment : textColorSegments) {
            if (textColorSegment[0] > -1 && textColorSegment[1] <= ssb.length()) {
                ssb.setSpan(new ForegroundColorSpan(genericColorValue),
                        textColorSegment[0], textColorSegment[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    /**
     * 用来将价格前面的符号（人民币符号，美元符号）字号变小
     *
     * @param symble
     * @param str
     * @return
     */
    public static SpannableStringBuilder lowerSymbleSize(String symble, String str, int size) {
        if (symble == null || symble.length() < 1) {
            return null;
        }
        if (str != null && str.length() > 0) {
            String finalString = symble + str;
            SpannableStringBuilder span4Price = new SpannableStringBuilder(finalString);
            CharacterStyle spanSize = new AbsoluteSizeSpan(size, true);
            CharacterStyle spanStyle = new StyleSpan(Typeface.NORMAL);
            span4Price.setSpan(spanSize, finalString.indexOf(symble), symble.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span4Price.setSpan(spanStyle, finalString.indexOf(symble), symble.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span4Price;
        }
        return null;
    }
}
