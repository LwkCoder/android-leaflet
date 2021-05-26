package com.lwkandroid.lib.common.qrcode;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;

import androidx.annotation.AnimRes;


/**
 * Created by LWK
 * 扫描二维码界面的参数
 */

public class QRCodeOptions implements Parcelable
{
    /**
     * 是否显示为条形码样式
     */
    private boolean isBarCodeMode = false;
    /**
     * 是否显示相册入口
     */
    private boolean showAlbum = true;
    /**
     * 是否全屏扫描
     */
    private boolean fullScreenScan = false;
    /**
     * 扫描框的颜色
     */
    private int rectColor = Color.WHITE;
    /**
     * 扫描框的边角颜色
     */
    private int rectCornerColor = Color.WHITE;
    /**
     * 扫描线的颜色
     */
    private int scanLineColor = Color.WHITE;
    /**
     * 扫描线的动画时长
     */
    private int scanLineAnimDuration = 1000;
    /**
     * 提示语
     */
    private String hintText = ResourceUtils.getString(R.string.qrcodescan_hint);
    /**
     * 提示语颜色
     */
    private int hintColor = ResourceUtils.getColor(R.color.gray_lightest);
    /**
     * 二维码太小自动缩放
     */
    private boolean autoZoom = false;
    /**
     * 进入动画
     */
    private @AnimRes
    int enterAnim = R.anim.slide_in_bottom;
    /**
     * 退出动画
     */
    private @AnimRes
    int exitAnim = R.anim.slide_out_top;

    public QRCodeOptions()
    {
    }

    public boolean isBarCodeMode()
    {
        return isBarCodeMode;
    }

    public void setBarCodeMode(boolean barCodeMode)
    {
        this.isBarCodeMode = barCodeMode;
    }

    public boolean isShowAlbum()
    {
        return showAlbum;
    }

    public void setShowAlbum(boolean showAlbum)
    {
        this.showAlbum = showAlbum;
    }

    public boolean isFullScreenScan()
    {
        return fullScreenScan;
    }

    public void setFullScreenScan(boolean fullScreenScan)
    {
        this.fullScreenScan = fullScreenScan;
    }

    public int getRectCornerColor()
    {
        return rectCornerColor;
    }

    public void setRectCornerColor(int rectCornerColor)
    {
        this.rectCornerColor = rectCornerColor;
    }

    public int getScanLineColor()
    {
        return scanLineColor;
    }

    public void setScanLineColor(int scanLineColor)
    {
        this.scanLineColor = scanLineColor;
    }

    public int getScanLineAnimDuration()
    {
        return scanLineAnimDuration;
    }

    public void setScanLineAnimDuration(int scanLineAnimDuration)
    {
        this.scanLineAnimDuration = scanLineAnimDuration;
    }

    public String getHintText()
    {
        return hintText;
    }

    public void setHintText(String hintText)
    {
        this.hintText = hintText;
    }

    public int getHintColor()
    {
        return hintColor;
    }

    public void setHintColor(int hintColor)
    {
        this.hintColor = hintColor;
    }

    public int getRectColor()
    {
        return rectColor;
    }

    public void setRectColor(int rectColor)
    {
        this.rectColor = rectColor;
    }

    public boolean isAutoZoom()
    {
        return autoZoom;
    }

    public void setAutoZoom(boolean autoZoom)
    {
        this.autoZoom = autoZoom;
    }

    public int getEnterAnim()
    {
        return enterAnim;
    }

    public void setEnterAnim(int enterAnim)
    {
        this.enterAnim = enterAnim;
    }

    public int getExitAnim()
    {
        return exitAnim;
    }

    public void setExitAnim(int exitAnim)
    {
        this.exitAnim = exitAnim;
    }

    @Override
    public String toString()
    {
        return "QRCodeOptions{" +
                "isBarCodeMode=" + isBarCodeMode +
                ", showAlbum=" + showAlbum +
                ", fullScreenScan=" + fullScreenScan +
                ", rectColor=" + rectColor +
                ", rectCornerColor=" + rectCornerColor +
                ", scanLineColor=" + scanLineColor +
                ", scanLineAnimDuration=" + scanLineAnimDuration +
                ", hintText='" + hintText + '\'' +
                ", hintColor=" + hintColor +
                ", autoZoom=" + autoZoom +
                ", enterAnim=" + enterAnim +
                ", exitAnim=" + exitAnim +
                '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeByte(this.isBarCodeMode ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showAlbum ? (byte) 1 : (byte) 0);
        dest.writeByte(this.fullScreenScan ? (byte) 1 : (byte) 0);
        dest.writeInt(this.rectColor);
        dest.writeInt(this.rectCornerColor);
        dest.writeInt(this.scanLineColor);
        dest.writeInt(this.scanLineAnimDuration);
        dest.writeString(this.hintText);
        dest.writeInt(this.hintColor);
        dest.writeByte(this.autoZoom ? (byte) 1 : (byte) 0);
        dest.writeInt(this.enterAnim);
        dest.writeInt(this.exitAnim);
    }

    protected QRCodeOptions(Parcel in)
    {
        this.isBarCodeMode = in.readByte() != 0;
        this.showAlbum = in.readByte() != 0;
        this.fullScreenScan = in.readByte() != 0;
        this.rectColor = in.readInt();
        this.rectCornerColor = in.readInt();
        this.scanLineColor = in.readInt();
        this.scanLineAnimDuration = in.readInt();
        this.hintText = in.readString();
        this.hintColor = in.readInt();
        this.autoZoom = in.readByte() != 0;
        this.enterAnim = in.readInt();
        this.exitAnim = in.readInt();
    }

    public static final Creator<QRCodeOptions> CREATOR = new Creator<QRCodeOptions>()
    {
        @Override
        public QRCodeOptions createFromParcel(Parcel source)
        {
            return new QRCodeOptions(source);
        }

        @Override
        public QRCodeOptions[] newArray(int size)
        {
            return new QRCodeOptions[size];
        }
    };
}
