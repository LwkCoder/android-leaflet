package com.lwkandroid.lib.common.widgets.view.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.view.iface.ITextViewFeature;
import com.lwkandroid.lib.common.widgets.view.utils.TextViewUtils;

import androidx.annotation.ColorInt;
import androidx.appcompat.content.res.AppCompatResources;


/**
 * TextView-Helper
 *
 * @author ZhongDaFeng
 */
public class RTextViewHelper extends RBaseHelper<TextView> implements ITextViewFeature
{

    //default value
    public static final int ICON_DIR_LEFT = 1, ICON_DIR_TOP = 2, ICON_DIR_RIGHT = 3, ICON_DIR_BOTTOM = 4;

    //icon
    private int mIconHeightLeft;
    private int mIconWidthLeft;
    private int mIconHeightRight;
    private int mIconWidthRight;

    private int mIconHeightTop;
    private int mIconWidthTop;
    private int mIconHeightBottom;
    private int mIconWidthBottom;

    private Drawable mIconLeft = null;
    private Drawable mIconNormalLeft;
    private Drawable mIconPressedLeft;
    private Drawable mIconUnableLeft;
    private Drawable mIconSelectedLeft;

    private Drawable mIconTop = null;
    private Drawable mIconNormalTop;
    private Drawable mIconPressedTop;
    private Drawable mIconUnableTop;
    private Drawable mIconSelectedTop;

    private Drawable mIconBottom = null;
    private Drawable mIconNormalBottom;
    private Drawable mIconPressedBottom;
    private Drawable mIconUnableBottom;
    private Drawable mIconSelectedBottom;

    private Drawable mIconRight = null;
    private Drawable mIconNormalRight;
    private Drawable mIconPressedRight;
    private Drawable mIconUnableRight;
    private Drawable mIconSelectedRight;

    // Text
    protected int mTextColorNormal;
    protected int mTextColorPressed;
    protected int mTextColorUnable;
    protected int mTextColorSelected;
    protected ColorStateList mTextColorStateList;
    protected int[][] states = new int[5][];

    //typeface
    private String mTypefacePath;

    //drawable和Text居中
    private boolean mDrawableWithText = false;

    /**
     * 是否设置对应的属性
     */
    protected boolean mHasPressedTextColor = false;
    protected boolean mHasUnableTextColor = false;
    protected boolean mHasSelectedTextColor = false;

    //TextView本身设置的padding
    protected int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;


    public RTextViewHelper(Context context, TextView view, AttributeSet attrs)
    {
        super(context, view, attrs);
        initAttributeSet(context, attrs);
        //监听View改变
        addOnViewChangeListener();
    }

    /**
     * 设置View变化监听
     */
    private void addOnViewChangeListener()
    {
        if (mView == null)
        {
            return;
        }
        if (!mDrawableWithText)
        {
            return;
        }
        //文本改变
        mView.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                setIcon();
            }
        });
    }

    @Override
    public void onGlobalLayout()
    {
        super.onGlobalLayout();
        if (mDrawableWithText)
        {
            mPaddingLeft = mView.getPaddingLeft();
            mPaddingRight = mView.getPaddingRight();
            mPaddingTop = mView.getPaddingTop();
            mPaddingBottom = mView.getPaddingBottom();
            setIcon();
        }
    }

    /**
     * 初始化控件属性
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, AttributeSet attrs)
    {
        if (context == null || attrs == null)
        {
            setup();
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RTextView);
        //icon
        //Vector兼容处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            mIconNormalLeft = a.getDrawable(R.styleable.RTextView_icon_normal_left);
            mIconPressedLeft = a.getDrawable(R.styleable.RTextView_icon_pressed_left);
            mIconUnableLeft = a.getDrawable(R.styleable.RTextView_icon_unable_left);
            mIconSelectedLeft = a.getDrawable(R.styleable.RTextView_icon_selected_left);
            mIconNormalRight = a.getDrawable(R.styleable.RTextView_icon_normal_right);
            mIconPressedRight = a.getDrawable(R.styleable.RTextView_icon_pressed_right);
            mIconUnableRight = a.getDrawable(R.styleable.RTextView_icon_unable_right);
            mIconSelectedRight = a.getDrawable(R.styleable.RTextView_icon_selected_right);
            mIconNormalTop = a.getDrawable(R.styleable.RTextView_icon_normal_top);
            mIconPressedTop = a.getDrawable(R.styleable.RTextView_icon_pressed_top);
            mIconUnableTop = a.getDrawable(R.styleable.RTextView_icon_unable_top);
            mIconSelectedTop = a.getDrawable(R.styleable.RTextView_icon_selected_top);
            mIconNormalBottom = a.getDrawable(R.styleable.RTextView_icon_normal_bottom);
            mIconPressedBottom = a.getDrawable(R.styleable.RTextView_icon_pressed_bottom);
            mIconUnableBottom = a.getDrawable(R.styleable.RTextView_icon_unable_bottom);
            mIconSelectedBottom = a.getDrawable(R.styleable.RTextView_icon_selected_bottom);
        } else
        {
            int normalIdLeft = a.getResourceId(R.styleable.RTextView_icon_normal_left, -1);
            int pressedIdLeft = a.getResourceId(R.styleable.RTextView_icon_pressed_left, -1);
            int unableIdLeft = a.getResourceId(R.styleable.RTextView_icon_unable_left, -1);
            int selectedIdLeft = a.getResourceId(R.styleable.RTextView_icon_selected_left, -1);
            int normalIdRight = a.getResourceId(R.styleable.RTextView_icon_normal_right, -1);
            int pressedIdRight = a.getResourceId(R.styleable.RTextView_icon_pressed_right, -1);
            int unableIdRight = a.getResourceId(R.styleable.RTextView_icon_unable_right, -1);
            int selectedIdRight = a.getResourceId(R.styleable.RTextView_icon_selected_right, -1);
            int normalIdTop = a.getResourceId(R.styleable.RTextView_icon_normal_top, -1);
            int pressedIdTop = a.getResourceId(R.styleable.RTextView_icon_pressed_top, -1);
            int unableIdTop = a.getResourceId(R.styleable.RTextView_icon_unable_top, -1);
            int selectedIdTop = a.getResourceId(R.styleable.RTextView_icon_selected_top, -1);
            int normalIdBottom = a.getResourceId(R.styleable.RTextView_icon_normal_bottom, -1);
            int pressedIdBottom = a.getResourceId(R.styleable.RTextView_icon_pressed_bottom, -1);
            int unableIdBottom = a.getResourceId(R.styleable.RTextView_icon_unable_bottom, -1);
            int selectedIdBottom = a.getResourceId(R.styleable.RTextView_icon_selected_bottom, -1);
            if (normalIdLeft != -1)
            {
                mIconNormalLeft = AppCompatResources.getDrawable(context, normalIdLeft);
            }
            if (pressedIdLeft != -1)
            {
                mIconPressedLeft = AppCompatResources.getDrawable(context, pressedIdLeft);
            }
            if (unableIdLeft != -1)
            {
                mIconUnableLeft = AppCompatResources.getDrawable(context, unableIdLeft);
            }
            if (selectedIdLeft != -1)
            {
                mIconSelectedLeft = AppCompatResources.getDrawable(context, selectedIdLeft);
            }
            if (normalIdRight != -1)
            {
                mIconNormalRight = AppCompatResources.getDrawable(context, normalIdRight);
            }
            if (pressedIdRight != -1)
            {
                mIconPressedRight = AppCompatResources.getDrawable(context, pressedIdRight);
            }
            if (unableIdRight != -1)
            {
                mIconUnableRight = AppCompatResources.getDrawable(context, unableIdRight);
            }
            if (selectedIdRight != -1)
            {
                mIconSelectedRight = AppCompatResources.getDrawable(context, selectedIdRight);
            }
            if (normalIdTop != -1)
            {
                mIconNormalTop = AppCompatResources.getDrawable(context, normalIdTop);
            }
            if (pressedIdTop != -1)
            {
                mIconPressedTop = AppCompatResources.getDrawable(context, pressedIdTop);
            }
            if (unableIdTop != -1)
            {
                mIconUnableTop = AppCompatResources.getDrawable(context, unableIdTop);
            }
            if (selectedIdTop != -1)
            {
                mIconSelectedTop = AppCompatResources.getDrawable(context, selectedIdTop);
            }
            if (normalIdBottom != -1)
            {
                mIconNormalBottom = AppCompatResources.getDrawable(context, normalIdBottom);
            }
            if (pressedIdBottom != -1)
            {
                mIconPressedBottom = AppCompatResources.getDrawable(context, pressedIdBottom);
            }
            if (unableIdBottom != -1)
            {
                mIconUnableBottom = AppCompatResources.getDrawable(context, unableIdBottom);
            }
            if (selectedIdBottom != -1)
            {
                mIconSelectedBottom = AppCompatResources.getDrawable(context, selectedIdBottom);
            }
        }
        mIconWidthLeft = a.getDimensionPixelSize(R.styleable.RTextView_icon_width_left, 0);
        mIconHeightLeft = a.getDimensionPixelSize(R.styleable.RTextView_icon_height_left, 0);
        mIconWidthRight = a.getDimensionPixelSize(R.styleable.RTextView_icon_width_right, 0);
        mIconHeightRight = a.getDimensionPixelSize(R.styleable.RTextView_icon_height_right, 0);
        mIconWidthBottom = a.getDimensionPixelSize(R.styleable.RTextView_icon_width_bottom, 0);
        mIconHeightBottom = a.getDimensionPixelSize(R.styleable.RTextView_icon_height_bottom, 0);
        mIconWidthTop = a.getDimensionPixelSize(R.styleable.RTextView_icon_width_top, 0);
        mIconHeightTop = a.getDimensionPixelSize(R.styleable.RTextView_icon_height_top, 0);

        //text
        mTextColorNormal = a.getColor(R.styleable.RTextView_text_color_normal, mView.getCurrentTextColor());
        mTextColorPressed = a.getColor(R.styleable.RTextView_text_color_pressed, 0);
        mTextColorUnable = a.getColor(R.styleable.RTextView_text_color_unable, 0);
        mTextColorSelected = a.getColor(R.styleable.RTextView_text_color_selected, 0);
        //typeface
        mTypefacePath = a.getString(R.styleable.RTextView_text_typeface);
        //drawableWithText
        mDrawableWithText = a.getBoolean(R.styleable.RTextView_icon_with_text, false);

        a.recycle();

        mHasPressedTextColor = mTextColorPressed != 0;
        mHasUnableTextColor = mTextColorUnable != 0;
        mHasSelectedTextColor = mTextColorSelected != 0;

        //setup
        setup();

    }

    /**
     * 设置
     */
    private void setup()
    {

        /**
         * icon
         */
        if (!mView.isEnabled())
        {
            mIconLeft = mIconUnableLeft;
            mIconRight = mIconUnableRight;
            mIconTop = mIconUnableTop;
            mIconBottom = mIconUnableBottom;
        } else if (mView.isSelected())
        {
            mIconLeft = mIconSelectedLeft;
            mIconRight = mIconSelectedRight;
            mIconTop = mIconSelectedTop;
            mIconBottom = mIconSelectedBottom;
        } else
        {
            mIconLeft = mIconNormalLeft;
            mIconRight = mIconNormalRight;
            mIconTop = mIconNormalTop;
            mIconBottom = mIconNormalBottom;
        }

        /**
         * 设置文字颜色默认值
         */
        if (!mHasPressedTextColor)
        {
            mTextColorPressed = mTextColorNormal;
        }
        if (!mHasUnableTextColor)
        {
            mTextColorUnable = mTextColorNormal;
        }
        if (!mHasSelectedTextColor)
        {
            mTextColorSelected = mTextColorNormal;
        }

        //unable,focused,pressed,selected,normal
        states[0] = new int[]{-android.R.attr.state_enabled};//unable
        states[1] = new int[]{android.R.attr.state_focused};//focused
        states[2] = new int[]{android.R.attr.state_pressed};//pressed
        states[3] = new int[]{android.R.attr.state_selected};//selected
        states[4] = new int[]{android.R.attr.state_enabled};//normal

        //设置文本颜色
        setTextColor();

        //设置ICON
        setIcon();

        //设置文本字体样式
        setTypeface();

    }

    /************************
     * Typeface
     ************************/

    public RTextViewHelper setTypeface(String typefacePath)
    {
        this.mTypefacePath = typefacePath;
        setTypeface();
        return this;
    }

    public String getTypefacePath()
    {
        return mTypefacePath;
    }

    private void setTypeface()
    {
        if (!TextUtils.isEmpty(mTypefacePath))
        {
            AssetManager assetManager = mContext.getAssets();
            Typeface typeface = Typeface.createFromAsset(assetManager, mTypefacePath);
            mView.setTypeface(typeface);
        }
    }

    /************************
     * Icon
     ************************/
    public RTextViewHelper setIconNormalLeft(Drawable icon)
    {
        this.mIconNormalLeft = icon;
        this.mIconLeft = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalRight(Drawable icon)
    {
        this.mIconNormalRight = icon;
        this.mIconRight = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalTop(Drawable icon)
    {
        this.mIconNormalTop = icon;
        this.mIconTop = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalBottom(Drawable icon)
    {
        this.mIconNormalBottom = icon;
        this.mIconBottom = icon;
        setIcon();
        return this;
    }

    public Drawable getIconNormalLeft()
    {
        return mIconNormalLeft;
    }

    public Drawable getIconNormalRight()
    {
        return mIconNormalRight;
    }

    public Drawable getIconNormalTop()
    {
        return mIconNormalTop;
    }

    public Drawable getIconNormalBottom()
    {
        return mIconNormalBottom;
    }

    public RTextViewHelper setIconPressedLeft(Drawable icon)
    {
        this.mIconPressedLeft = icon;
        this.mIconLeft = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedRight(Drawable icon)
    {
        this.mIconPressedRight = icon;
        this.mIconRight = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedTop(Drawable icon)
    {
        this.mIconPressedTop = icon;
        this.mIconTop = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedBottom(Drawable icon)
    {
        this.mIconPressedBottom = icon;
        this.mIconBottom = icon;
        setIcon();
        return this;
    }

    public Drawable getIconPressedLeft()
    {
        return mIconPressedLeft;
    }

    public Drawable getIconPressedRight()
    {
        return mIconPressedRight;
    }

    public Drawable getIconPressedTop()
    {
        return mIconPressedTop;
    }

    public Drawable getIconPressedBottom()
    {
        return mIconPressedBottom;
    }

    public RTextViewHelper setIconUnableLeft(Drawable icon)
    {
        this.mIconUnableLeft = icon;
        this.mIconLeft = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableRight(Drawable icon)
    {
        this.mIconUnableRight = icon;
        this.mIconRight = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableTop(Drawable icon)
    {
        this.mIconUnableTop = icon;
        this.mIconTop = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableBottom(Drawable icon)
    {
        this.mIconUnableBottom = icon;
        this.mIconBottom = icon;
        setIcon();
        return this;
    }

    public Drawable getIconUnableLeft()
    {
        return mIconUnableLeft;
    }

    public Drawable getIconUnableRight()
    {
        return mIconUnableRight;
    }

    public Drawable getIconUnableTop()
    {
        return mIconUnableTop;
    }

    public Drawable getIconUnableBottom()
    {
        return mIconUnableBottom;
    }

    public RTextViewHelper setIconSelectedLeft(Drawable icon)
    {
        this.mIconSelectedLeft = icon;
        this.mIconLeft = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedRight(Drawable icon)
    {
        this.mIconSelectedRight = icon;
        this.mIconRight = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedTop(Drawable icon)
    {
        this.mIconSelectedTop = icon;
        this.mIconTop = icon;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedBottom(Drawable icon)
    {
        this.mIconSelectedBottom = icon;
        this.mIconBottom = icon;
        setIcon();
        return this;
    }

    public Drawable getIconSelectedLeft()
    {
        return mIconSelectedLeft;
    }

    public Drawable getIconSelectedRight()
    {
        return mIconSelectedRight;
    }

    public Drawable getIconSelectedTop()
    {
        return mIconSelectedTop;
    }

    public Drawable getIconSelectedBottom()
    {
        return mIconSelectedBottom;
    }

    public RTextViewHelper setIconSizeLeft(int iconWidth, int iconHeight)
    {
        this.mIconWidthLeft = iconWidth;
        this.mIconHeightLeft = iconHeight;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeRight(int iconWidth, int iconHeight)
    {
        this.mIconWidthRight = iconWidth;
        this.mIconHeightRight = iconHeight;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeTop(int iconWidth, int iconHeight)
    {
        this.mIconWidthTop = iconWidth;
        this.mIconHeightTop = iconHeight;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeBottom(int iconWidth, int iconHeight)
    {
        this.mIconWidthBottom = iconWidth;
        this.mIconHeightBottom = iconHeight;
        setIcon();
        return this;
    }

    public int getIconWidthLeft()
    {
        return mIconWidthLeft;
    }

    public int getIconHeightLeft()
    {
        return mIconHeightLeft;
    }

    public int getIconWidthRight()
    {
        return mIconWidthRight;
    }

    public int getIconHeightRight()
    {
        return mIconHeightRight;
    }

    public int getIconWidthTop()
    {
        return mIconWidthTop;
    }

    public int getIconHeightTop()
    {
        return mIconHeightTop;
    }

    public int getIconWidthBottom()
    {
        return mIconWidthBottom;
    }

    public int getIconHeightBottom()
    {
        return mIconHeightBottom;
    }

    //    /**
    //     * 主要用于子类调用
    //     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
    //     */
    //    @Deprecated
    //    @SuppressWarnings("unchecked")
    //    protected void setIcon(Drawable icon)
    //    {
    //        this.mIcon = icon;
    //        setIcon();
    //    }

    /**
     * 主要用于子类调用
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     * 备注：添加上下左右之后此处功能默认 drawableLeft
     */
    @SuppressWarnings("unchecked")
    protected void setIconLeft(Drawable icon)
    {
        this.mIconLeft = icon;
        setIcon();
    }

    @SuppressWarnings("unchecked")
    protected void setIconRight(Drawable icon)
    {
        this.mIconRight = icon;
        setIcon();
    }

    @SuppressWarnings("unchecked")
    protected void setIconTop(Drawable icon)
    {
        this.mIconTop = icon;
        setIcon();
    }

    @SuppressWarnings("unchecked")
    protected void setIconBottom(Drawable icon)
    {
        this.mIconBottom = icon;
        setIcon();
    }

    private void setIcon()
    {
        //未设置图片大小
        if (mIconHeightLeft == 0 && mIconWidthLeft == 0)
        {
            if (mIconLeft != null)
            {
                mIconWidthLeft = mIconLeft.getIntrinsicWidth();
                mIconHeightLeft = mIconLeft.getIntrinsicHeight();
            }
        }
        if (mIconHeightRight == 0 && mIconWidthRight == 0)
        {
            if (mIconRight != null)
            {
                mIconWidthRight = mIconRight.getIntrinsicWidth();
                mIconHeightRight = mIconRight.getIntrinsicHeight();
            }
        }
        if (mIconHeightTop == 0 && mIconWidthTop == 0)
        {
            if (mIconTop != null)
            {
                mIconWidthTop = mIconTop.getIntrinsicWidth();
                mIconHeightTop = mIconTop.getIntrinsicHeight();
            }
        }
        if (mIconHeightBottom == 0 && mIconWidthBottom == 0)
        {
            if (mIconBottom != null)
            {
                mIconWidthBottom = mIconBottom.getIntrinsicWidth();
                mIconHeightBottom = mIconBottom.getIntrinsicHeight();
            }
        }

        setIcon(mIconLeft, mIconRight, mIconTop, mIconBottom);
    }

    /**
     * 新版本设置icon逻辑代码
     *
     * @param drawableLeft
     * @param drawableRight
     * @param drawableTop
     * @param drawableBottom
     */
    private void setIcon(Drawable drawableLeft, Drawable drawableRight, Drawable drawableTop, Drawable drawableBottom)
    {
        if (drawableLeft != null || drawableRight != null || drawableTop != null || drawableBottom != null)
        {
            if (drawableLeft != null)
            {
                drawableLeft.setBounds(0, 0, mIconWidthLeft, mIconHeightLeft);
            }
            if (drawableRight != null)
            {
                drawableRight.setBounds(0, 0, mIconWidthRight, mIconHeightRight);
            }
            if (drawableTop != null)
            {
                drawableTop.setBounds(0, 0, mIconWidthTop, mIconHeightTop);
            }
            if (drawableBottom != null)
            {
                drawableBottom.setBounds(0, 0, mIconWidthBottom, mIconHeightBottom);
            }
            //setDrawable
            mView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);

            //drawable间距
            if (!mDrawableWithText)
            {
                return;
            }

            int drawablePadding = mView.getCompoundDrawablePadding();
            int drawablePaddingHorizontal = 0, drawablePaddingVertical = 0;
            if (mIconLeft != null)
            {
                drawablePaddingHorizontal += drawablePadding;
            }
            if (mIconRight != null)
            {
                drawablePaddingHorizontal += drawablePadding;
            }
            if (mIconTop != null)
            {
                drawablePaddingVertical += drawablePadding;
            }
            if (mIconBottom != null)
            {
                drawablePaddingVertical += drawablePadding;
            }

            final int drawableWidthFinal = mIconWidthLeft + mIconWidthRight;
            final int drawableHeightFinal = mIconHeightTop + mIconHeightBottom;
            final int drawablePaddingVerticalFinal = drawablePaddingVertical;//垂直方向上drawable间距
            final int drawablePaddingHorizontalFinal = drawablePaddingHorizontal;//水平方向上drawable间距
            //view.getLineCount() need post
            mView.post(() -> {

                //水平方向计算
                float textWidth = TextViewUtils.get().getTextWidth(mView, drawableWidthFinal, mPaddingLeft, mPaddingRight, drawablePaddingHorizontalFinal);
                float bodyWidth = textWidth + drawableWidthFinal + drawablePaddingHorizontalFinal;//内容宽度
                float actualWidth = mView.getWidth() - (mPaddingLeft + mPaddingRight);//实际可用宽度
                int translateX = (int) (actualWidth - bodyWidth) / 2;//两边使用
                if (translateX < 0)
                {
                    translateX = 0;
                }

                //垂直方向计算
                float textHeight = TextViewUtils.get().getTextHeight(mView, drawableHeightFinal, mPaddingTop, mPaddingBottom, drawablePaddingVerticalFinal);
                float bodyHeight = textHeight + drawableHeightFinal + drawablePaddingVerticalFinal;//内容高度
                float actualHeight = mView.getHeight() - (mPaddingTop + mPaddingBottom);//实际可用高度
                int translateY = (int) (actualHeight - bodyHeight) / 2;
                if (translateY < 0)
                {
                    translateY = 0;
                }

                //关键技术点
                mView.setPadding(translateX + mPaddingLeft, translateY + mPaddingTop, translateX + mPaddingRight, translateY + mPaddingBottom);
            });

        }
    }

    /************************
     * text color
     ************************/

    public RTextViewHelper setTextColorNormal(@ColorInt int textColor)
    {
        this.mTextColorNormal = textColor;
        if (!mHasPressedTextColor)
        {
            mTextColorPressed = mTextColorNormal;
        }
        if (!mHasUnableTextColor)
        {
            mTextColorUnable = mTextColorNormal;
        }
        if (!mHasSelectedTextColor)
        {
            mTextColorSelected = mTextColorNormal;
        }
        setTextColor();
        return this;
    }

    public int getTextColorNormal()
    {
        return mTextColorNormal;
    }

    public RTextViewHelper setTextColorPressed(@ColorInt int textColor)
    {
        this.mTextColorPressed = textColor;
        this.mHasPressedTextColor = true;
        setTextColor();
        return this;
    }

    public int getTextColorPressed()
    {
        return mTextColorPressed;
    }

    public RTextViewHelper setTextColorUnable(@ColorInt int textColor)
    {
        this.mTextColorUnable = textColor;
        this.mHasUnableTextColor = true;
        setTextColor();
        return this;
    }

    public int getTextColorUnable()
    {
        return mTextColorUnable;
    }


    public RTextViewHelper setTextColorSelected(@ColorInt int textColor)
    {
        this.mTextColorSelected = textColor;
        this.mHasSelectedTextColor = true;
        setTextColor();
        return this;
    }

    public int getTextColorSelected()
    {
        return mTextColorSelected;
    }

    public RTextViewHelper setTextColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable, @ColorInt int selected)
    {
        this.mTextColorNormal = normal;
        this.mTextColorPressed = pressed;
        this.mTextColorUnable = unable;
        this.mTextColorSelected = selected;
        this.mHasPressedTextColor = true;
        this.mHasUnableTextColor = true;
        this.mHasSelectedTextColor = true;
        setTextColor();
        return this;
    }

    protected void setTextColor()
    {
        //unable,focused,pressed,selected,normal
        int[] colors = new int[]{mTextColorUnable, mTextColorPressed, mTextColorPressed, mTextColorSelected, mTextColorNormal};
        mTextColorStateList = new ColorStateList(states, colors);
        mView.setTextColor(mTextColorStateList);
    }

    /**
     * 设置是否启用
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setEnabled(boolean enabled)
    {
        if (enabled)
        {
            if (mIconNormalLeft != null)
            {
                mIconLeft = mIconNormalLeft;
            }
            if (mIconNormalRight != null)
            {
                mIconRight = mIconNormalRight;
            }
            if (mIconNormalTop != null)
            {
                mIconTop = mIconNormalTop;
            }
            if (mIconNormalBottom != null)
            {
                mIconBottom = mIconNormalBottom;
            }
            setIcon();
        } else
        {
            if (mIconUnableLeft != null)
            {
                mIconLeft = mIconUnableLeft;
            }
            if (mIconUnableRight != null)
            {
                mIconRight = mIconUnableRight;
            }
            if (mIconUnableTop != null)
            {
                mIconTop = mIconUnableTop;
            }
            if (mIconUnableBottom != null)
            {
                mIconBottom = mIconUnableBottom;
            }
            setIcon();
        }
    }

    /**
     * 设置是否选中
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setSelected(boolean selected)
    {
        if (!mView.isEnabled())
        {
            return;
        }
        if (selected)
        {
            if (mIconSelectedLeft != null)
            {
                mIconLeft = mIconSelectedLeft;
            }
            if (mIconSelectedRight != null)
            {
                mIconRight = mIconSelectedRight;
            }
            if (mIconSelectedTop != null)
            {
                mIconTop = mIconSelectedTop;
            }
            if (mIconSelectedBottom != null)
            {
                mIconBottom = mIconSelectedBottom;
            }
            setIcon();
        } else
        {
            if (mIconNormalLeft != null)
            {
                mIconLeft = mIconNormalLeft;
            }
            if (mIconNormalRight != null)
            {
                mIconRight = mIconNormalRight;
            }
            if (mIconNormalTop != null)
            {
                mIconTop = mIconNormalTop;
            }
            if (mIconNormalBottom != null)
            {
                mIconBottom = mIconNormalBottom;
            }
            setIcon();
        }
    }

    @Override
    public void onVisibilityChanged(View changedView, int visibility)
    {
        if (visibility != View.GONE)
            setIcon();
    }

    /**
     * 触摸事件逻辑
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onTouchEvent(MotionEvent event)
    {
        if (!mView.isEnabled())
        {
            return;
        }
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP://抬起
            case MotionEvent.ACTION_CANCEL://父级控件获取控制权
                if (mIconNormalLeft != null)
                {
                    mIconLeft = mView.isSelected() ? mIconSelectedLeft : mIconNormalLeft;
                }
                if (mIconNormalRight != null)
                {
                    mIconRight = mView.isSelected() ? mIconSelectedRight : mIconNormalRight;
                }
                if (mIconNormalTop != null)
                {
                    mIconTop = mView.isSelected() ? mIconSelectedTop : mIconNormalTop;
                }
                if (mIconNormalBottom != null)
                {
                    mIconBottom = mView.isSelected() ? mIconSelectedBottom : mIconNormalBottom;
                }
                setIcon();
                break;
            case MotionEvent.ACTION_DOWN://按下
                if (mIconPressedLeft != null)
                {
                    mIconLeft = mIconPressedLeft;
                }
                if (mIconPressedRight != null)
                {
                    mIconRight = mIconPressedRight;
                }
                if (mIconPressedTop != null)
                {
                    mIconTop = mIconPressedTop;
                }
                if (mIconPressedBottom != null)
                {
                    mIconBottom = mIconPressedBottom;
                }
                setIcon();
                break;
            case MotionEvent.ACTION_MOVE://移动
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (isOutsideView(x, y))
                {
                    if (mIconNormalLeft != null)
                    {
                        mIconLeft = mView.isSelected() ? mIconSelectedLeft : mIconNormalLeft;
                    }
                    if (mIconNormalRight != null)
                    {
                        mIconRight = mView.isSelected() ? mIconSelectedRight : mIconNormalRight;
                    }
                    if (mIconNormalTop != null)
                    {
                        mIconTop = mView.isSelected() ? mIconSelectedTop : mIconNormalTop;
                    }
                    if (mIconNormalBottom != null)
                    {
                        mIconBottom = mView.isSelected() ? mIconSelectedBottom : mIconNormalBottom;
                    }
                    setIcon();
                }
                break;
        }
    }

    /**
     * 初始化按下状态文本颜色
     * 备注:当存在 Checked 状态并且没有设置 Pressed 时，Pressed = Checked 更符合常规UI
     * 备注:使用子类 RCheckHelper 中的属性，提供方法入口
     */
    protected void initPressedTextColor(boolean hasCheckedTextColor, int textColorChecked)
    {
        if (!mHasPressedTextColor)
        {
            mTextColorPressed = hasCheckedTextColor ? textColorChecked : mTextColorNormal;
        }
    }

}
