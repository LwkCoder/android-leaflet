package com.lwkandroid.lib.common.widgets.ptr;

import android.content.Context;
import android.util.DisplayMetrics;

import com.lwkandroid.lib.common.R;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

/**
 * 默认的下拉刷新头部控件
 * @author LWK
 */
public class PTRDefaultRefreshHeader extends AppCompatImageView implements PTRLayout.IPTRRefreshHeader
{
    private static final int MAX_ALPHA = 255;
    private static final float TRIM_RATE = 0.85f;
    private static final float TRIM_OFFSET = 0.4f;

    static final int CIRCLE_DIAMETER = 40;
    static final int CIRCLE_DIAMETER_LARGE = 56;

    private CircularProgressDrawable mProgress;
    private int mCircleDiameter;

    public PTRDefaultRefreshHeader(Context context)
    {
        super(context);
        mProgress = new CircularProgressDrawable(context);
        setColorSchemeResources(R.color.colorAccent);
        mProgress.setStyle(CircularProgressDrawable.LARGE);
        mProgress.setAlpha(MAX_ALPHA);
        mProgress.setArrowScale(0.8f);
        setImageDrawable(mProgress);
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(mCircleDiameter, mCircleDiameter);
    }

    @Override
    public void onPull(int offset, int total, int overPull)
    {
        if (mProgress.isRunning())
        {
            return;
        }
        float end = TRIM_RATE * offset / total;
        float rotate = TRIM_OFFSET * offset / total;
        if (overPull > 0)
        {
            rotate += TRIM_OFFSET * overPull / total;
        }
        mProgress.setArrowEnabled(true);
        mProgress.setStartEndTrim(0, end);
        mProgress.setProgressRotation(rotate);
    }

    public void setSize(@CircularProgressDrawable.ProgressDrawableSize int size)
    {
        if (size != CircularProgressDrawable.LARGE && size != CircularProgressDrawable.DEFAULT)
        {
            return;
        }

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        if (size == CircularProgressDrawable.LARGE)
        {
            mCircleDiameter = (int) (CIRCLE_DIAMETER_LARGE * metrics.density);
        } else
        {
            mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
        }

        // force the bounds of the progress circle inside the circle view to
        // update by setting it to null before updating its size and then
        // re-setting it
        setImageDrawable(null);
        mProgress.setStyle(size);
        setImageDrawable(mProgress);
    }

    @Override
    public void stop()
    {
        mProgress.stop();
    }

    @Override
    public void doRefresh()
    {
        mProgress.start();
    }

    public void setColorSchemeResources(@ColorRes int... colorResIds)
    {
        final Context context = getContext();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++)
        {
            colorRes[i] = ContextCompat.getColor(context, colorResIds[i]);
        }
        setColorSchemeColors(colorRes);
    }

    public void setColorSchemeColors(@ColorInt int... colors)
    {
        mProgress.setColorSchemeColors(colors);
    }
}