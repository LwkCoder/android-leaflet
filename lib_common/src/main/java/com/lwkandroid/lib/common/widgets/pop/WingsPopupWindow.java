package com.lwkandroid.lib.common.widgets.pop;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.log.KLog;

/**
 * Description:
 *
 * @author LWK
 * @date 2020/4/8
 */
public final class WingsPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener
{
    private PopConfig mConfig;
    private IPopUiController mUiController;
    private PopupWindow.OnDismissListener mDismissListener;
    private FrameLayout mContentView;
    private SparseArray<OnPopChildClickListener> mChildClickArray = new SparseArray<>();


    static WingsPopupWindow create(PopConfig config, IPopUiController controller)
    {
        return new WingsPopupWindow()
                .setConfig(config)
                .setUiController(controller);
    }

    private WingsPopupWindow()
    {
    }

    public WingsPopupWindow setConfig(PopConfig config)
    {
        this.mConfig = config;
        return this;
    }

    public WingsPopupWindow setUiController(IPopUiController controller)
    {
        this.mUiController = controller;
        return this;
    }

    public WingsPopupWindow setPopDismissListener(PopupWindow.OnDismissListener listener)
    {
        this.mDismissListener = listener;
        return this;
    }

    public WingsPopupWindow addOnChildClickListener(int vId, OnPopChildClickListener listener)
    {
        this.mChildClickArray.put(vId, listener);
        return this;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        init(getActivityFromView(parent));
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity)
    {
        init(getActivityFromView(anchor));
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void showWithAnchor(final View anchor,
                               @XGravity final int xGravity,
                               @YGravity final int yGravity)
    {
        showWithAnchor(anchor, xGravity, yGravity, 0, 0);
    }

    public void showWithAnchor(final View anchor,
                               @XGravity final int xGravity,
                               @YGravity final int yGravity,
                               final int xoff,
                               final int yoff)
    {
        init(getActivityFromView(anchor));
        //        getContentView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        //        {
        //            @Override
        //            public void onGlobalLayout()
        //            {
        //                getContentView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        //                int width = getContentView().getMeasuredWidth();
        //                int height = getContentView().getMeasuredHeight();
        //                KLog.e("cw="+width+" ch="+height);
        //                int realXOffset = calculateX(anchor, xGravity, width, xoff);
        //                int realYOffset = calculateY(anchor, yGravity, height, yoff);
        //                update(anchor, realXOffset, realYOffset, width, height);
        //            }
        //        });
        //        // NOTICE:
        //        // ????????????????????????????????????PopupWindowCompat.showAsDropDown????????????????????????????????????
        //        super.showAsDropDown(anchor, 0, 0, Gravity.NO_GRAVITY);

        int width = getContentView().getMeasuredWidth();
        int height = getContentView().getMeasuredHeight();
        int realXOffset = calculateX(anchor, xGravity, width, xoff);
        int realYOffset = calculateY(anchor, yGravity, height, yoff);
        super.showAsDropDown(anchor, realXOffset, realYOffset, Gravity.NO_GRAVITY);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context)
    {
        if (mUiController == null)
        {
            throw new IllegalArgumentException("You must to set a IPopUiController with nonNull layout resource for WingsPopupWindow !");
        }
        if (mConfig == null)
        {
            KLog.w("WingsPopupWindow will use the default config instead of null.");
            mConfig = new PopConfig();
        }
        if (!(context instanceof Activity))
        {
            KLog.w("WingsPopupWindow cannot access Activity context,will use top Activity.");
            context = ActivityLifecycleHelper.get().getTopActivity();
        }
        //???????????????
        mContentView = new FrameLayout(context);
        View layout = LayoutInflater.from(context).inflate(mUiController.getLayoutId(), mContentView, false);
        mContentView.addView(layout);
        mContentView.measure(makeDropDownMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT),
                makeDropDownMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT));
        //????????????????????????
        mUiController.onCreateView(mContentView, this);
        //??????????????????
        for (int i = 0, size = mChildClickArray.size(); i < size; i++)
        {
            final int index = i;
            final int viewId = mChildClickArray.keyAt(index);
            View child = mContentView.findViewById(viewId);
            if (child != null)
            {
                child.setOnClickListener(v -> {
                    OnPopChildClickListener listener = mChildClickArray.valueAt(index);
                    if (listener != null)
                    {
                        listener.onPopChildClicked(v.getId(), v, mContentView, WingsPopupWindow.this);
                    }
                });
            }
        }
        //????????????
        setContentView(mContentView);
        setWidth(mConfig.getWidth());
        setHeight(mConfig.getHeight());
        setFocusable(mConfig.isFocusable());
        setTouchable(mConfig.isTouchable());
        setAnimationStyle(mConfig.getAnimStyle());
        setInputMethodMode(mConfig.getInputMethodMode());
        setSoftInputMode(mConfig.getSoftInputMode());
        setOnDismissListener(this);
        setOutsideTouchable(mConfig.isCanceledOnTouchOutside());
        if (mConfig.isCanceledOnTouchOutside())
        {
            setBackgroundDrawable(new BitmapDrawable());
            setTouchInterceptor((v, event) -> {
                if (isTouchDownOutside(event))
                {
                    dismiss();
                }
                return v.onTouchEvent(event);
            });
        } else
        {
            //?????????????????????????????????
            setFocusable(true);
            setOutsideTouchable(false);
            setBackgroundDrawable(null);
            //????????????????????????contentView ??????PopupWindow
            getContentView().setFocusable(true);
            getContentView().setFocusableInTouchMode(true);
            //??????????????????????????????????????????PopupDecorView???????????????onKeyListener()???
            //??????Back?????????dismiss()
            getContentView().setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    dismiss();
                    return true;
                }
                return false;
            });
            //???Android 6.0?????? ????????????????????????????????????
            setTouchInterceptor((v, event) -> isTouchDownOutside(event));
        }

        int w = mContentView.getMeasuredWidth();
        int h = mContentView.getMeasuredHeight();
        KLog.e("w=" + w + " h=" + h);
        if (getContentView() != null)
            KLog.e(" ww=" + getContentView().getMeasuredWidth() + " hh=" + getContentView().getMeasuredHeight());
    }


    @Override
    public void onDismiss()
    {
        if (mUiController != null)
        {
            mUiController.onDismiss();
        }
        if (mDismissListener != null)
        {
            mDismissListener.onDismiss();
            mDismissListener = null;
        }
        if (mChildClickArray != null)
        {
            mChildClickArray.clear();
        }
    }

    private int makeDropDownMeasureSpec(int measureSpec)
    {
        int mode;
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            mode = View.MeasureSpec.AT_MOST;
        } else
        {
            mode = View.MeasureSpec.EXACTLY;
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode);
    }

    /**
     * ????????????????????????
     *
     * @param event ????????????
     */
    private boolean isTouchDownOutside(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int x = (int) event.getX();
            int y = (int) event.getY();
            return x < 0 || x > getContentView().getWidth()
                    || y < 0 || y > getContentView().getHeight();
        } else
        {
            return event.getAction() == MotionEvent.ACTION_OUTSIDE;
        }
    }

    /**
     * ??????View???????????????Activity
     */
    private Activity getActivityFromView(View view)
    {
        if (null != view)
        {
            Context context = view.getContext();
            while (context != null && !(context instanceof Activity))
            {
                if (context instanceof ContextWrapper)
                {
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            return (Activity) context;
        }
        return null;
    }

    /**
     * ????????????????????????x??????
     */
    private int calculateX(View anchor, int xGravity, int measuredW, int x)
    {
        switch (xGravity)
        {
            case XGravity.LEFT:
                //anchor view??????
                x -= measuredW;
                break;
            case XGravity.ALIGN_RIGHT:
                //???anchor view????????????
                x -= measuredW - anchor.getWidth();
                break;
            case XGravity.CENTER:
                //anchor view????????????
                x += anchor.getWidth() / 2 - measuredW / 2;
                break;
            case XGravity.ALIGN_LEFT:
                //???anchor view????????????
                // Default position.
                break;
            case XGravity.RIGHT:
                //anchor view??????
                x += anchor.getWidth();
                break;
            default:
                break;
        }
        return x;
    }

    /**
     * ????????????????????????y??????
     */
    private int calculateY(View anchor, int yGravity, int measuredH, int y)
    {
        switch (yGravity)
        {
            case YGravity.ABOVE:
                //anchor view??????
                y -= measuredH + anchor.getHeight();
                break;
            case YGravity.ALIGN_BOTTOM:
                //anchor view????????????
                y -= measuredH;
                break;
            case YGravity.CENTER:
                //anchor view????????????
                y -= anchor.getHeight() / 2 + measuredH / 2;
                break;
            case YGravity.ALIGN_TOP:
                //anchor view????????????
                y -= anchor.getHeight();
                break;
            case YGravity.BELOW:
                //anchor view??????
                // Default position.
                break;
            default:
                break;
        }
        return y;
    }
}
