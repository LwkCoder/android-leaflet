package com.lwkandroid.lib.core.utils.common;


import android.annotation.SuppressLint;

import com.lwkandroid.lib.core.context.AppContext;
import com.lwkandroid.lib.core.utils.constants.MemoryConstants;

/**
 * 转换工具类
 *
 * @author LWK
 */
public final class ConvertUtils
{
    private ConvertUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue)
    {
        final float scale = AppContext.get().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue)
    {
        final float scale = AppContext.get().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue)
    {
        final float fontScale = AppContext.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue)
    {
        final float fontScale = AppContext.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 字节数组转为内存大小
     *
     * @param byteSize Size of byte.
     * @param unit     The unit of memory size.
     *                 <ul>
     *                 <li>{@link MemoryConstants#BYTE}</li>
     *                 <li>{@link MemoryConstants#KB}</li>
     *                 <li>{@link MemoryConstants#MB}</li>
     *                 <li>{@link MemoryConstants#GB}</li>
     *                 </ul>
     * @return size of memory in unit
     */
    public static double byte2MemorySize(final long byteSize,
                                         @MemoryConstants.Unit final int unit)
    {
        if (byteSize < 0)
        {
            return -1;
        }
        return (double) byteSize / unit;
    }

    /**
     * 获取字节大小描述
     *
     * @param byteSize Size of byte.
     * @return fit size of memory
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize)
    {
        return byte2FitMemorySize(byteSize, 3);
    }

    /**
     * 获取字节大小描述
     *
     * @param byteSize  Size of byte.
     * @param precision The precision
     * @return fit size of memory
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize, int precision)
    {
        if (precision < 0)
        {
            throw new IllegalArgumentException("precision shouldn't be less than zero!");
        }
        if (byteSize < 0)
        {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        } else if (byteSize < MemoryConstants.KB)
        {
            return String.format("%." + precision + "fB", (double) byteSize);
        } else if (byteSize < MemoryConstants.MB)
        {
            return String.format("%." + precision + "fKB", (double) byteSize / MemoryConstants.KB);
        } else if (byteSize < MemoryConstants.GB)
        {
            return String.format("%." + precision + "fMB", (double) byteSize / MemoryConstants.MB);
        } else
        {
            return String.format("%." + precision + "fGB", (double) byteSize / MemoryConstants.GB);
        }
    }

}
