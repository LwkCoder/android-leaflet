package com.lwkandroid.lib.common.qrcode;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * Description:二维码扫描帮助类
 *
 * @author LWK
 * @date 2020/3/13
 */
public final class QRCodeScanHelper
{
    private QRCodeScanHelper()
    {
    }

    /**
     * 跳转扫码界面
     */
    public static void startScanQRCode(Activity activity, int requestCode)
    {
        QRCodeScanActivity.start(activity, requestCode);
    }

    /**
     * 跳转扫码界面
     */
    public static void startScanQRCode(Activity activity, int requestCode, QRCodeOptions options)
    {
        QRCodeScanActivity.start(activity, requestCode, options);
    }

    /**
     * 跳转扫码界面
     */
    public static void startScanQRCode(Fragment fragment, int requestCode)
    {
        QRCodeScanActivity.start(fragment.getActivity(), requestCode);
    }

    /**
     * 跳转扫码界面
     */
    public static void startScanQRCode(Fragment fragment, int requestCode, QRCodeOptions options)
    {
        QRCodeScanActivity.start(fragment.getActivity(), requestCode, options);
    }

    /**
     * 解析成功扫码后的结果【用于发起跳转界面的onActivityResult()】
     *
     * @return 扫码结果
     */
    public static String parseScanResult(int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && data != null)
        {
            return data.getStringExtra(QRCodeScanActivity.KEY_RESULT);
        } else
        {
            return "";
        }
    }
}
