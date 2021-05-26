package com.lwkandroid.lib.common.widgets.dialog;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.ViewGroup;

import com.lwkandroid.lib.common.R;

import androidx.annotation.FloatRange;
import androidx.annotation.StyleRes;

/**
 * Description:Dialog配置
 *
 * @author LWK
 * @date 2020/4/7
 */
final class DialogConfig implements Parcelable
{
    private int mLayoutWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mLayoutHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private boolean mFocusable = true;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private @StyleRes
    int mThemeStyle = R.style.BaseDialogStyle;
    private @FloatRange(from = 0.0, to = 1.0f)
    float mDarkWindowDegree = 0.5f;
    private @StyleRes
    int mAnimStyle = android.R.style.Animation_Dialog;
    private int mLayoutGravity = Gravity.CENTER;

    public int getLayoutWidth()
    {
        return mLayoutWidth;
    }

    public void setLayoutWidth(int layoutWidth)
    {
        this.mLayoutWidth = layoutWidth;
    }

    public int getLayoutHeight()
    {
        return mLayoutHeight;
    }

    public void setLayoutHeight(int layoutHeight)
    {
        this.mLayoutHeight = layoutHeight;
    }

    public boolean isFocusable()
    {
        return mFocusable;
    }

    public void setFocusable(boolean b)
    {
        this.mFocusable = b;
    }

    public boolean isCancelable()
    {
        return mCancelable;
    }

    public void setCancelable(boolean b)
    {
        this.mCancelable = b;
    }

    public boolean isCanceledOnTouchOutside()
    {
        return mCanceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean b)
    {
        this.mCanceledOnTouchOutside = b;
    }

    public int getThemeStyle()
    {
        return mThemeStyle;
    }

    public void setThemeStyle(int themeStyle)
    {
        this.mThemeStyle = themeStyle;
    }

    public float getDarkWindowDegree()
    {
        return mDarkWindowDegree;
    }

    public void setDarkWindowDegree(float degree)
    {
        this.mDarkWindowDegree = degree;
    }

    public int getAnimStyle()
    {
        return mAnimStyle;
    }

    public void setAnimStyle(int animStyle)
    {
        this.mAnimStyle = animStyle;
    }

    public int getLayoutGravity()
    {
        return mLayoutGravity;
    }

    public void setLayoutGravity(int layoutGravity)
    {
        this.mLayoutGravity = layoutGravity;
    }

    @Override
    public String toString()
    {
        return "DialogConfig{" +
                "mLayoutWidth=" + mLayoutWidth +
                ", mLayoutHeight=" + mLayoutHeight +
                ", mFocusable=" + mFocusable +
                ", mCancelable=" + mCancelable +
                ", mCanceledOnTouchOutside=" + mCanceledOnTouchOutside +
                ", mThemeStyle=" + mThemeStyle +
                ", mDarkWindowDegree=" + mDarkWindowDegree +
                ", mAnimStyle=" + mAnimStyle +
                ", mLayoutGravity=" + mLayoutGravity +
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
        dest.writeInt(this.mLayoutWidth);
        dest.writeInt(this.mLayoutHeight);
        dest.writeByte(this.mFocusable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mCancelable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mCanceledOnTouchOutside ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mThemeStyle);
        dest.writeFloat(this.mDarkWindowDegree);
        dest.writeInt(this.mAnimStyle);
        dest.writeInt(this.mLayoutGravity);
    }

    public DialogConfig()
    {
    }

    protected DialogConfig(Parcel in)
    {
        this.mLayoutWidth = in.readInt();
        this.mLayoutHeight = in.readInt();
        this.mFocusable = in.readByte() != 0;
        this.mCancelable = in.readByte() != 0;
        this.mCanceledOnTouchOutside = in.readByte() != 0;
        this.mThemeStyle = in.readInt();
        this.mDarkWindowDegree = in.readFloat();
        this.mAnimStyle = in.readInt();
        this.mLayoutGravity = in.readInt();
    }

    public static final Parcelable.Creator<DialogConfig> CREATOR = new Parcelable.Creator<DialogConfig>()
    {
        @Override
        public DialogConfig createFromParcel(Parcel source)
        {
            return new DialogConfig(source);
        }

        @Override
        public DialogConfig[] newArray(int size)
        {
            return new DialogConfig[size];
        }
    };
}
