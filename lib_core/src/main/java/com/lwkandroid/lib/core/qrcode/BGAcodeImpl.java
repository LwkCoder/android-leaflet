package com.lwkandroid.lib.core.qrcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.lwkandroid.lib.core.rx.scheduler.RxSchedulers;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.rxjava3.core.Observable;

/**
 * Description:BGAQRCode_Android实现的策略，底层是ZXing
 * 【https://github.com/bingoogolapple/BGAQRCode-Android】
 *
 * @author LWK
 * @date 2020/3/10
 */
final class BGAcodeImpl implements IQRCodeStrategy
{
    @Override
    public String decodeQRCode(String picPath)
    {
        return QRCodeDecoder.syncDecodeQRCode(picPath);
    }

    @Override
    public String decodeQRCode(Bitmap bitmap)
    {
        return QRCodeDecoder.syncDecodeQRCode(bitmap);
    }

    @Override
    public Observable<String> decodeQRCodeByRxJava(String picPath)
    {
        return Observable.just(picPath)
                .map(s -> decodeQRCode(picPath))
                .compose(RxSchedulers.applyComputation2Main());
    }

    @Override
    public Observable<String> decodeQRCodeByRxJava(Bitmap bitmap)
    {
        return Observable.just(bitmap)
                .map(b -> decodeQRCode(bitmap))
                .compose(RxSchedulers.applyComputation2Main());
    }

    @Override
    public Bitmap encodeQRCode(String content, int size)
    {
        return QRCodeEncoder.syncEncodeQRCode(content, size, Color.BLACK, Color.WHITE, null);
    }

    @Override
    public Bitmap encodeQRCode(String content, int size, int fColor, int bColor)
    {
        return QRCodeEncoder.syncEncodeQRCode(content, size, fColor, bColor, null);
    }

    @Override
    public Bitmap encodeQRCode(String content, int size, Bitmap logo)
    {
        return QRCodeEncoder.syncEncodeQRCode(content, size, Color.BLACK, Color.WHITE, logo);
    }

    @Override
    public Bitmap encodeQRCode(String content, int size, int fColor, int bColor, Bitmap logo)
    {
        return QRCodeEncoder.syncEncodeQRCode(content, size, fColor, bColor, logo);
    }

    @Override
    public Bitmap encodeBarcode(String content, int width, int height, int textSize)
    {
        return QRCodeEncoder.syncEncodeBarcode(content, width, height, textSize);
    }

    @Override
    public Observable<Bitmap> encodeQRCodeByRxJava(String content, int size)
    {
        return encodeQRCodeByRxJava(content, size, Color.BLACK, Color.WHITE, null);
    }

    @Override
    public Observable<Bitmap> encodeQRCodeByRxJava(String content, int size, int fColor, int bColor)
    {
        return encodeQRCodeByRxJava(content, size, fColor, bColor, null);
    }

    @Override
    public Observable<Bitmap> encodeQRCodeByRxJava(String content, int size, Bitmap logo)
    {
        return encodeQRCodeByRxJava(content, size, Color.BLACK, Color.WHITE, logo);
    }

    @Override
    public Observable<Bitmap> encodeQRCodeByRxJava(String content, int size, int fColor, int bColor, Bitmap logo)
    {
        return Observable.just(content)
                .map(s -> encodeQRCode(s, size, fColor, bColor, logo))
                .compose(RxSchedulers.applyIo2Main());
    }

    @Override
    public Observable<Bitmap> encodeBarcodeByRxjava(String content, int width, int height, int textSize)
    {
        return Observable.just(content)
                .map(s -> encodeBarcode(content, width, height, textSize))
                .compose(RxSchedulers.applyIo2Main());
    }
}
