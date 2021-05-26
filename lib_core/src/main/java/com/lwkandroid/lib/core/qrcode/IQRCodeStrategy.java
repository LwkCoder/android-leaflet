package com.lwkandroid.lib.core.qrcode;

import android.graphics.Bitmap;

import io.reactivex.rxjava3.core.Observable;


/**
 * Description:二维码接口
 *
 * @author LWK
 * @date 2020/3/10
 */
public interface IQRCodeStrategy
{
    /**
     * 解析本地某张图片内二维码内容
     * 【同步方法】
     *
     * @param picPath 图片绝对路径
     * @return 二维码内容
     */
    String decodeQRCode(String picPath);

    /**
     * 解析某张位图内二维码内容
     * 【同步方法】
     *
     * @param bitmap 位图
     * @return 二维码内容
     */
    String decodeQRCode(Bitmap bitmap);

    /**
     * 配合RxJava解析本地某张图片内二维码内容
     * 【IO线程异步解析，订阅在主线程】
     *
     * @param picPath 图片绝对路径
     * @return 二维码内容
     */
    Observable<String> decodeQRCodeByRxJava(String picPath);

    /**
     * 配合RxJava解析某张位图内二维码内容
     * 【IO线程异步解析，订阅在主线程】
     *
     * @param bitmap 位图
     * @return 二维码内容
     */
    Observable<String> decodeQRCodeByRxJava(Bitmap bitmap);

    /**
     * 创建一张二维码位图
     * 【同步方法】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @return 二维码位图
     */
    Bitmap encodeQRCode(String content, int size);

    /**
     * 创建一张二维码位图
     * 【同步方法】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @param fColor  前景色，默认黑色
     * @param bColor  背景色，默认白色
     * @return 二维码位图
     */
    Bitmap encodeQRCode(String content, int size, int fColor, int bColor);

    /**
     * 创建一张二维码位图
     * 【同步方法】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @param logo    中间logo
     * @return 二维码位图
     */
    Bitmap encodeQRCode(String content, int size, Bitmap logo);

    /**
     * 创建一张二维码位图
     * 【同步方法】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @param fColor  前景色，默认黑色
     * @param bColor  背景色，默认白色
     * @param logo    中间logo
     * @return 二维码位图
     */
    Bitmap encodeQRCode(String content, int size, int fColor, int bColor, Bitmap logo);

    /**
     * 创建一张条码位图
     *
     * @param content  内容
     * @param width    宽度
     * @param height   高度
     * @param textSize 字体大小，px，为0不显示
     * @return 条码位图
     */
    Bitmap encodeBarcode(String content, int width, int height, int textSize);

    /**
     * 配合RxJava创建一张二维码位图
     * 【IO线程异步生成，订阅在主线程】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @return 二维码位图
     */
    Observable<Bitmap> encodeQRCodeByRxJava(String content, int size);

    /**
     * 配合RxJava创建一张二维码位图
     * 【IO线程异步生成，订阅在主线程】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @param fColor  前景色，默认黑色
     * @param bColor  背景色，默认白色
     * @return 二维码位图
     */
    Observable<Bitmap> encodeQRCodeByRxJava(String content, int size, int fColor, int bColor);

    /**
     * 配合RxJava创建一张二维码位图
     * 【IO线程异步生成，订阅在主线程】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @param logo    中间logo
     * @return 二维码位图
     */
    Observable<Bitmap> encodeQRCodeByRxJava(String content, int size, Bitmap logo);

    /**
     * 配合RxJava创建一张二维码位图
     * 【IO线程异步生成，订阅在主线程】
     *
     * @param content 二维码内容
     * @param size    图片大小，单位px
     * @param fColor  前景色，默认黑色
     * @param bColor  背景色，默认白色
     * @param logo    中间logo
     * @return 二维码位图
     */
    Observable<Bitmap> encodeQRCodeByRxJava(String content, int size, int fColor, int bColor, Bitmap logo);

    /**
     * 配合RxJava创建一张条码位图
     *
     * @param content  内容
     * @param width    宽度
     * @param height   高度
     * @param textSize 字体大小，px，为0不显示
     * @return 条码位图
     */
    Observable<Bitmap> encodeBarcodeByRxjava(String content, int width, int height, int textSize);
}
