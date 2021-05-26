package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lwkandroid.lib.common.widgets.view.helper.RCheckHelper;
import com.lwkandroid.lib.common.widgets.view.iface.RHelper;


/**
 * RCheckBox
 */
public class RCheckBox extends androidx.appcompat.widget.AppCompatCheckBox implements RHelper<RCheckHelper>
{

    private RCheckHelper mHelper;

    public RCheckBox(Context context)
    {
        this(context, null);
    }

    public RCheckBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mHelper = new RCheckHelper(context, this, attrs);
    }

    @Override
    public RCheckHelper getHelper()
    {
        return mHelper;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        if (mHelper != null)
        {
            mHelper.setEnabled(enabled);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mHelper != null)
        {
            mHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setSelected(boolean selected)
    {
        if (mHelper != null)
        {
            mHelper.setSelected(selected);
        }
        super.setSelected(selected);
    }

    @Override
    public void setChecked(boolean checked)
    {
        if (mHelper != null)
        {
            mHelper.setChecked(checked);
        }
        super.setChecked(checked);
    }

}
