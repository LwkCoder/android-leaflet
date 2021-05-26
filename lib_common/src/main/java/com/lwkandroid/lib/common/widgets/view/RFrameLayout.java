package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lwkandroid.lib.common.widgets.view.helper.RBaseHelper;
import com.lwkandroid.lib.common.widgets.view.iface.RHelper;


/**
 * RFrameLayout
 */
public class RFrameLayout extends FrameLayout implements RHelper<RBaseHelper>
{

    private RBaseHelper mHelper;

    public RFrameLayout(Context context)
    {
        this(context, null);
    }

    public RFrameLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RFrameLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mHelper = new RBaseHelper(context, this, attrs);
    }

    @Override
    public RBaseHelper getHelper()
    {
        return mHelper;
    }

    @Override
    public void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        mHelper.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        mHelper.onLayout(changed, left, top, right, bottom);
    }
}
