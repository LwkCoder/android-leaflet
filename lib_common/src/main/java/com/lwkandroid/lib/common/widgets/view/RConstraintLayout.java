package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.lwkandroid.lib.common.widgets.view.helper.RBaseHelper;
import com.lwkandroid.lib.common.widgets.view.iface.RHelper;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * RConstraintLayout
 */
public class RConstraintLayout extends ConstraintLayout implements RHelper<RBaseHelper>
{

    private RBaseHelper mHelper;

    public RConstraintLayout(Context context)
    {
        this(context, null);
    }

    public RConstraintLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr)
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
