package com.lwkandroid.lib.common.widgets.ptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;

import com.lwkandroid.lib.common.BuildConfig;
import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.utils.common.ConvertUtils;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 下拉刷新控件, 作为容器，下拉时会将子 View 下移, 并拉出 RefreshView（表示正在刷新的 View）
 * <ul>
 * <li>可通过继承并覆写 {@link #createRefreshHeader()} 方法实现自己的 RefreshView</li>
 * <li>可通过 {@link #setRefreshOffsetCalculator(RefreshOffsetCalculator)} 自己决定在下拉过程中 RefreshView 的位置</li>
 * <li>可在 xml 中使用 {@link com.lwkandroid.lib.common.R.styleable#PTRLayout} 这些属性或在 Java 设置对应的属性决定子View的开始位置、触发刷新的位置等值</li>
 * </ul>
 *
 * @author cginechen
 * @date 2016-12-11
 */
public class PTRLayout extends ViewGroup implements NestedScrollingParent
{
    private static final String TAG = "QMUIPullRefreshLayout";
    private static final int INVALID_POINTER = -1;
    private static final int FLAG_NEED_SCROLL_TO_INIT_POSITION = 1;
    private static final int FLAG_NEED_SCROLL_TO_REFRESH_POSITION = 1 << 1;
    private static final int FLAG_NEED_DO_REFRESH = 1 << 2;
    private static final int FLAG_NEED_DELIVER_VELOCITY = 1 << 3;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    boolean mIsRefreshing = false;
    private View mTargetView;
    private IPTRRefreshHeader mIRefreshHeader;
    private View mRefreshHeader;
    private int mRefreshZIndex = -1;
    private int mSystemTouchSlop;
    private int mTouchSlop;
    private OnPullListener mListener;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    /**
     * RefreshView的初始offset
     */
    private int mRefreshInitOffset;
    /**
     * 刷新时RefreshView的offset
     */
    private int mRefreshEndOffset;
    /**
     * RefreshView当前offset
     */
    private int mRefreshCurrentOffset;
    /**
     * 是否自动根据RefreshView的高度计算RefreshView的初始位置
     */
    private boolean mAutoCalculateRefreshInitOffset = true;
    /**
     * 是否自动根据TargetView在刷新时的位置计算RefreshView的结束位置
     */
    private boolean mAutoCalculateRefreshEndOffset = true;
    /**
     * 自动让TargetView的刷新位置与RefreshView高度相等
     */
    private boolean mEqualTargetRefreshOffsetToRefreshViewHeight = false;
    /**
     * 当拖拽超过超过mAutoScrollToRefreshMinOffset时，自动滚动到刷新位置并触发刷新
     * mAutoScrollToRefreshMinOffset == - 1表示要mAutoScrollToRefreshMinOffset>=mTargetRefreshOffset
     */
    private int mAutoScrollToRefreshMinOffset = -1;
    /**
     * TargetView(ListView或者ScrollView等)的初始位置
     */
    private int mTargetInitOffset;
    /**
     * 下拉时 TargetView（ListView 或者 ScrollView 等）当前的位置。
     */
    private int mTargetCurrentOffset;
    /**
     * 刷新时TargetView(ListView或者ScrollView等)的位置
     */
    private int mTargetRefreshOffset;
    private boolean mEnableOverPull = true;
    private boolean mNestedScrollInProgress;
    private int mActivePointerId = INVALID_POINTER;
    private boolean mIsDragging;
    private float mInitialDownY;
    private float mInitialDownX;
    @SuppressWarnings("FieldCanBeLocal")
    private float mInitialMotionY;
    private float mLastMotionY;
    @SuppressWarnings("FieldCanBeLocal")
    private float mDragRate = 0.65f;
    private RefreshOffsetCalculator mRefreshOffsetCalculator;
    private VelocityTracker mVelocityTracker;
    private float mMaxVelocity;
    private float mMiniVelocity;
    private Scroller mScroller;
    private int mScrollFlag = 0;
    private boolean mNestScrollDurationRefreshing = false;


    public PTRLayout(Context context)
    {
        this(context, null);
    }

    public PTRLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, R.attr.WingsPullRefreshLayoutDefStyle);
    }

    public PTRLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        final ViewConfiguration vc = ViewConfiguration.get(context);
        mMaxVelocity = vc.getScaledMaximumFlingVelocity();
        mMiniVelocity = vc.getScaledMinimumFlingVelocity();
        mSystemTouchSlop = vc.getScaledTouchSlop();
        mTouchSlop = ConvertUtils.px2dp(mSystemTouchSlop); //系统的值是8dp,如何配置？

        mScroller = new Scroller(getContext());
        mScroller.setFriction(getScrollerFriction());

        addRefreshHeader();
        setChildrenDrawingOrderEnabled(true);

        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.PTRLayout, defStyleAttr, 0);

        try
        {
            mRefreshInitOffset = array.getDimensionPixelSize(
                    R.styleable.PTRLayout_refresh_init_offset, Integer.MIN_VALUE);
            mRefreshEndOffset = array.getDimensionPixelSize(
                    R.styleable.PTRLayout_refresh_end_offset, Integer.MIN_VALUE);
            mTargetInitOffset = array.getDimensionPixelSize(
                    R.styleable.PTRLayout_target_init_offset, 0);
            mTargetRefreshOffset = array.getDimensionPixelSize(
                    R.styleable.PTRLayout_target_refresh_offset,
                    ConvertUtils.dp2px(72));
            mAutoCalculateRefreshInitOffset = mRefreshInitOffset == Integer.MIN_VALUE ||
                    array.getBoolean(R.styleable.PTRLayout_auto_calculate_refresh_init_offset, false);
            mAutoCalculateRefreshEndOffset = mRefreshEndOffset == Integer.MIN_VALUE ||
                    array.getBoolean(R.styleable.PTRLayout_auto_calculate_refresh_end_offset, false);
            mEqualTargetRefreshOffsetToRefreshViewHeight = array.getBoolean(
                    R.styleable.PTRLayout_equal_target_refresh_offset_to_refresh_view_height, false);
        } finally
        {
            array.recycle();
        }
        mRefreshCurrentOffset = mRefreshInitOffset;
        mTargetCurrentOffset = mTargetInitOffset;
    }

    public void setOnPullListener(OnPullListener listener)
    {
        mListener = listener;
    }

    public void setChildScrollUpCallback(OnChildScrollUpCallback childScrollUpCallback)
    {
        mChildScrollUpCallback = childScrollUpCallback;
    }

    protected float getScrollerFriction()
    {
        return ViewConfiguration.getScrollFriction();
    }

    public void setAutoScrollToRefreshMinOffset(int autoScrollToRefreshMinOffset)
    {
        mAutoScrollToRefreshMinOffset = autoScrollToRefreshMinOffset;
    }

    /**
     * 覆盖该方法以实现自己的 RefreshHeader。
     *
     * @return 自定义的 RefreshHeader, 注意该 View 必须实现 {@link IPTRRefreshHeader} 接口
     */
    protected View createRefreshHeader()
    {
        return new PTRDefaultRefreshHeader(getContext());
    }

    private void addRefreshHeader()
    {
        if (mRefreshHeader == null)
        {
            mRefreshHeader = createRefreshHeader();
        }
        if (!(mRefreshHeader instanceof IPTRRefreshHeader))
        {
            throw new RuntimeException("RefreshView must be a INSTANCE of IPTRRefreshHeader");
        }
        mIRefreshHeader = (IPTRRefreshHeader) mRefreshHeader;
        if (mRefreshHeader.getLayoutParams() == null)
        {
            mRefreshHeader.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        addView(mRefreshHeader);
    }

    public void setRefreshHeader(View refreshHeader)
    {
        if (refreshHeader == null)
        {
            return;
        }
        if (!(refreshHeader instanceof IPTRRefreshHeader))
        {
            throw new RuntimeException("RefreshView must be a INSTANCE of IPTRRefreshHeader");
        }
        if (mRefreshHeader != null)
        {
            removeView(mRefreshHeader);
        }
        mRefreshHeader = refreshHeader;
        mIRefreshHeader = (IPTRRefreshHeader) mRefreshHeader;
        addView(mRefreshHeader);
    }

    public IPTRRefreshHeader getRefreshHeader()
    {
        return mIRefreshHeader;
    }

    /**
     * 设置在下拉过程中 RefreshView 的偏移量
     */
    public void setRefreshOffsetCalculator(RefreshOffsetCalculator refreshOffsetCalculator)
    {
        mRefreshOffsetCalculator = refreshOffsetCalculator;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i)
    {
        if (mRefreshZIndex < 0)
        {
            return i;
        }
        // 最后才绘制mRefreshView
        if (i == mRefreshZIndex)
        {
            return childCount - 1;
        }
        if (i > mRefreshZIndex)
        {
            return i - 1;
        }
        return i;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b)
    {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        //noinspection StatementWithEmptyBody
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && mTargetView instanceof AbsListView)
                || (mTargetView != null && !ViewCompat.isNestedScrollingEnabled(mTargetView)))
        {
            // Nope.
        } else
        {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTargetView();
        if (mTargetView == null)
        {
            return;
        }
        int targetMeasureWidthSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY);
        int targetMeasureHeightSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);
        mTargetView.measure(targetMeasureWidthSpec, targetMeasureHeightSpec);
        measureChild(mRefreshHeader, widthMeasureSpec, heightMeasureSpec);
        mRefreshZIndex = -1;
        for (int i = 0; i < getChildCount(); i++)
        {
            if (getChildAt(i) == mRefreshHeader)
            {
                mRefreshZIndex = i;
                break;
            }
        }

        int refreshViewHeight = mRefreshHeader.getMeasuredHeight();
        if (mAutoCalculateRefreshInitOffset)
        {
            if (mRefreshInitOffset != -refreshViewHeight)
            {
                mRefreshInitOffset = -refreshViewHeight;
                mRefreshCurrentOffset = mRefreshInitOffset;
            }
        }
        if (mEqualTargetRefreshOffsetToRefreshViewHeight)
        {
            mTargetRefreshOffset = refreshViewHeight;
        }
        if (mAutoCalculateRefreshEndOffset)
        {
            mRefreshEndOffset = (mTargetRefreshOffset - refreshViewHeight) / 2;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0)
        {
            return;
        }
        ensureTargetView();
        if (mTargetView == null)
        {
            return;
        }

        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        mTargetView.layout(childLeft, childTop + mTargetCurrentOffset,
                childLeft + childWidth, childTop + childHeight + mTargetCurrentOffset);
        int refreshViewWidth = mRefreshHeader.getMeasuredWidth();
        int refreshViewHeight = mRefreshHeader.getMeasuredHeight();
        mRefreshHeader.layout((width / 2 - refreshViewWidth / 2), mRefreshCurrentOffset,
                (width / 2 + refreshViewWidth / 2), mRefreshCurrentOffset + refreshViewHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        ensureTargetView();

        final int action = ev.getAction();
        int pointerIndex;

        if (!isEnabled() || canChildScrollUp() || mNestedScrollInProgress)
        {
            if (BuildConfig.DEBUG)
            {
                info("fast end onIntercept: isEnabled = " + isEnabled() + "; canChildScrollUp = "
                        + canChildScrollUp() + " ; mNestedScrollInProgress = " + mNestedScrollInProgress);
            }
            return false;
        }
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mIsDragging = false;
                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0)
                {
                    return false;
                }
                mInitialDownX = ev.getX(pointerIndex);
                mInitialDownY = ev.getY(pointerIndex);
                break;

            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0)
                {
                    KLog.e(TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                startDragging(x, y);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsDragging = false;
                mActivePointerId = INVALID_POINTER;
                break;
            default:
                break;
        }

        return mIsDragging;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        final int action = ev.getAction();
        int pointerIndex;

        if (!isEnabled() || canChildScrollUp() || mNestedScrollInProgress)
        {
            KLog.d(TAG, "fast end onTouchEvent: isEnabled = " + isEnabled() + "; canChildScrollUp = "
                    + canChildScrollUp() + " ; mNestedScrollInProgress = " + mNestedScrollInProgress);
            return false;
        }

        acquireVelocityTracker(ev);

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mIsDragging = false;
                mScrollFlag = 0;
                if (!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                }
                mActivePointerId = ev.getPointerId(0);
                break;

            case MotionEvent.ACTION_MOVE:
            {
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0)
                {
                    KLog.e(TAG, "onTouchEvent Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                startDragging(x, y);

                if (mIsDragging)
                {
                    float dy = (y - mLastMotionY) * mDragRate;
                    if (dy >= 0)
                    {
                        moveTargetView(dy, true);
                    } else
                    {
                        int move = moveTargetView(dy, true);
                        float delta = Math.abs(dy) - Math.abs(move);
                        if (delta > 0)
                        {
                            // 重新dispatch一次down事件，使得列表可以继续滚动
                            ev.setAction(MotionEvent.ACTION_DOWN);
                            // 立刻dispatch一个大于mSystemTouchSlop的move事件，防止触发TargetView
                            float offsetLoc = mSystemTouchSlop + 1;
                            if (delta > offsetLoc)
                            {
                                offsetLoc = delta;
                            }
                            ev.offsetLocation(0, offsetLoc);
                            dispatchTouchEvent(ev);
                            ev.setAction(action);
                            // 再dispatch一次move事件，消耗掉所有dy
                            ev.offsetLocation(0, -offsetLoc);
                            dispatchTouchEvent(ev);
                        }
                    }
                    mLastMotionY = y;
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                pointerIndex = ev.getActionIndex();
                if (pointerIndex < 0)
                {
                    KLog.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mActivePointerId = ev.getPointerId(pointerIndex);
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
            {
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0)
                {
                    KLog.e(TAG, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }

                if (mIsDragging)
                {
                    mIsDragging = false;
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    float vy = mVelocityTracker.getYVelocity(mActivePointerId);
                    if (Math.abs(vy) < mMiniVelocity)
                    {
                        vy = 0;
                    }
                    finishPull((int) vy);
                }
                mActivePointerId = INVALID_POINTER;
                releaseVelocityTracker();
                return false;
            }
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                return false;
            default:
                break;
        }

        return true;
    }

    private void ensureTargetView()
    {
        if (mTargetView == null)
        {
            for (int i = 0, count = getChildCount(); i < count; i++)
            {
                View view = getChildAt(i);
                if (!view.equals(mRefreshHeader))
                {
                    onSureTargetView(view);
                    mTargetView = view;
                    break;
                }
            }
        }
    }

    /**
     * 确定TargetView, 提供机会给子类来做一些初始化的操作
     */
    protected void onSureTargetView(View targetView)
    {

    }

    private void finishPull(int vy)
    {
        info("finishPull: vy = " + vy + " ; mTargetCurrentOffset = " + mTargetCurrentOffset +
                " ; mTargetRefreshOffset = " + mTargetRefreshOffset + " ; mTargetInitOffset = " + mTargetInitOffset +
                " ; mScroller.isFinished() = " + mScroller.isFinished());
        int miniVy = vy / 1000; // 向下拖拽时， 速度不能太大
        onFinishPull(miniVy, mRefreshInitOffset, mRefreshEndOffset, mRefreshHeader.getHeight(),
                mTargetCurrentOffset, mTargetInitOffset, mTargetRefreshOffset);
        if (mTargetCurrentOffset >= mTargetRefreshOffset)
        {
            if (miniVy > 0)
            {
                mScrollFlag = FLAG_NEED_SCROLL_TO_REFRESH_POSITION | FLAG_NEED_DO_REFRESH;
                mScroller.fling(0, mTargetCurrentOffset, 0, miniVy,
                        0, 0, mTargetInitOffset, Integer.MAX_VALUE);
                invalidate();
            } else if (miniVy < 0)
            {
                mScroller.fling(0, mTargetCurrentOffset, 0, vy,
                        0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (mScroller.getFinalY() < mTargetInitOffset)
                {
                    mScrollFlag = FLAG_NEED_DELIVER_VELOCITY;
                } else if (mScroller.getFinalY() < mTargetRefreshOffset)
                {
                    int dy = mTargetInitOffset - mTargetCurrentOffset;
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, dy);
                } else if (mScroller.getFinalY() == mTargetRefreshOffset)
                {
                    mScrollFlag = FLAG_NEED_DO_REFRESH;
                } else
                {
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetRefreshOffset - mTargetCurrentOffset);
                    mScrollFlag = FLAG_NEED_DO_REFRESH;
                }
                invalidate();
            } else
            {
                if (mTargetCurrentOffset > mTargetRefreshOffset)
                {
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetRefreshOffset - mTargetCurrentOffset);
                }
                mScrollFlag = FLAG_NEED_DO_REFRESH;
                invalidate();
            }
        } else
        {
            if (miniVy > 0)
            {
                mScroller.fling(0, mTargetCurrentOffset, 0, miniVy, 0, 0, mTargetInitOffset, Integer.MAX_VALUE);
                if (mScroller.getFinalY() > mTargetRefreshOffset)
                {
                    mScrollFlag = FLAG_NEED_SCROLL_TO_REFRESH_POSITION | FLAG_NEED_DO_REFRESH;
                } else if (mAutoScrollToRefreshMinOffset >= 0 && mScroller.getFinalY() > mAutoScrollToRefreshMinOffset)
                {
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetRefreshOffset - mTargetCurrentOffset);
                    mScrollFlag = FLAG_NEED_DO_REFRESH;
                } else
                {
                    mScrollFlag = FLAG_NEED_SCROLL_TO_INIT_POSITION;
                }
                invalidate();
            } else if (miniVy < 0)
            {
                mScrollFlag = 0;
                mScroller.fling(0, mTargetCurrentOffset, 0, vy, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (mScroller.getFinalY() < mTargetInitOffset)
                {
                    mScrollFlag = FLAG_NEED_DELIVER_VELOCITY;
                } else
                {
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetInitOffset - mTargetCurrentOffset);
                    mScrollFlag = 0;
                }
                invalidate();
            } else
            {
                if (mTargetCurrentOffset == mTargetInitOffset)
                {
                    return;
                }
                if (mAutoScrollToRefreshMinOffset >= 0 && mTargetCurrentOffset >= mAutoScrollToRefreshMinOffset)
                {
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetRefreshOffset - mTargetCurrentOffset);
                    mScrollFlag = FLAG_NEED_DO_REFRESH;
                } else
                {
                    mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetInitOffset - mTargetCurrentOffset);
                    mScrollFlag = 0;
                }
                invalidate();
            }
        }
    }

    protected void onFinishPull(int vy, int refreshInitOffset, int refreshEndOffset, int refreshViewHeight,
                                int targetCurrentOffset, int targetInitOffset, int targetRefreshOffset)
    {

    }

    protected void onRefresh()
    {
        if (mIsRefreshing)
        {
            return;
        }
        mIsRefreshing = true;
        mIRefreshHeader.doRefresh();
        if (mListener != null)
        {
            mListener.onRefresh();
        }
    }

    public void finishRefresh()
    {
        mIsRefreshing = false;
        mIRefreshHeader.stop();
        mScrollFlag = FLAG_NEED_SCROLL_TO_INIT_POSITION;
        mScroller.forceFinished(true);
        invalidate();
    }

    public void setEnableOverPull(boolean enableOverPull)
    {
        mEnableOverPull = enableOverPull;
    }

    private void onSecondaryPointerUp(MotionEvent ev)
    {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId)
        {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    public void reset()
    {
        moveTargetViewTo(mTargetInitOffset, false);
        mIRefreshHeader.stop();
        mIsRefreshing = false;
        mScroller.forceFinished(true);
        mScrollFlag = 0;
    }

    protected void startDragging(float x, float y)
    {
        final float dx = x - mInitialDownX;
        final float dy = y - mInitialDownY;
        boolean isYDrag = isYDrag(dx, dy);
        if (isYDrag && (dy > mTouchSlop || (dy < -mTouchSlop && mTargetCurrentOffset > mTargetInitOffset)) && !mIsDragging)
        {
            mInitialMotionY = mInitialDownY + mTouchSlop;
            mLastMotionY = mInitialMotionY;
            mIsDragging = true;
        }
    }

    protected boolean isYDrag(float dx, float dy)
    {
        return Math.abs(dy) > Math.abs(dx);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        reset();
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        if (!enabled)
        {
            reset();
            invalidate();
        }
    }

    public boolean canChildScrollUp()
    {
        if (mChildScrollUpCallback != null)
        {
            return mChildScrollUpCallback.canChildScrollUp(this, mTargetView);
        }
        return mTargetView != null && mTargetView.canScrollVertically(-1);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes)
    {
        info("onStartNestedScroll: nestedScrollAxes = " + nestedScrollAxes);
        return isEnabled() && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes)
    {
        info("onNestedScrollAccepted: axes = " + axes);
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        mNestedScrollInProgress = true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    {
        info("onNestedPreScroll: dx = " + dx + " ; dy = " + dy);
        int parentCanConsume = mTargetCurrentOffset - mTargetInitOffset;
        if (dy > 0 && parentCanConsume > 0)
        {
            if (dy >= parentCanConsume)
            {
                consumed[1] = parentCanConsume;
                moveTargetViewTo(mTargetInitOffset, true);
            } else
            {
                consumed[1] = dy;
                moveTargetView(-dy, true);
            }
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        info("onNestedScroll: dxConsumed = " + dxConsumed + " ; dyConsumed = " + dyConsumed +
                " ; dxUnconsumed = " + dxUnconsumed + " ; dyUnconsumed = " + dyUnconsumed);
        if (dyUnconsumed < 0 && !canChildScrollUp())
        {
            moveTargetView(-dyUnconsumed, true);
        }
    }

    @Override
    public int getNestedScrollAxes()
    {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(View child)
    {
        info("onStopNestedScroll: mNestedScrollInProgress = " + mNestedScrollInProgress);
        mNestedScrollingParentHelper.onStopNestedScroll(child);
        if (mNestedScrollInProgress)
        {
            mNestedScrollInProgress = false;
            finishPull(0);
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
    {
        info("onNestedPreFling: mTargetCurrentOffset = " + mTargetCurrentOffset +
                " ; velocityX = " + velocityX + " ; velocityY = " + velocityY);
        if (mTargetCurrentOffset > mTargetInitOffset)
        {
            mNestedScrollInProgress = false;
            finishPull((int) -velocityY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed)
    {
        try
        {
            return super.onNestedFling(target, velocityX, velocityY, consumed);
        } catch (Throwable e)
        {
            // android 24及以上ViewGroup会继续往上派发， 23以及以下直接返回false
            // 低于5.0的机器和RecyclerView配合工作时，部分机型会调用这个方法，但是ViewGroup并没有实现这个方法，会报错，这里catch一下
        }
        return false;
    }

    private int moveTargetView(float dy, boolean isDragging)
    {
        int target = (int) (mTargetCurrentOffset + dy);
        return moveTargetViewTo(target, isDragging);
    }

    private int moveTargetViewTo(int target, boolean isDragging)
    {
        return moveTargetViewTo(target, isDragging, false);
    }

    private int moveTargetViewTo(int target, boolean isDragging, boolean calculateAnyWay)
    {
        target = Math.max(target, mTargetInitOffset);
        if (!mEnableOverPull)
        {
            target = Math.min(target, mTargetRefreshOffset);
        }
        int offset = 0;
        if (target != mTargetCurrentOffset || calculateAnyWay)
        {
            offset = target - mTargetCurrentOffset;
            ViewCompat.offsetTopAndBottom(mTargetView, offset);
            mTargetCurrentOffset = target;
            int total = mTargetRefreshOffset - mTargetInitOffset;
            if (isDragging)
            {
                mIRefreshHeader.onPull(Math.min(mTargetCurrentOffset - mTargetInitOffset, total), total,
                        mTargetCurrentOffset - mTargetRefreshOffset);
            }
            onMoveTargetView(mTargetCurrentOffset);
            if (mListener != null)
            {
                mListener.onMoveTarget(mTargetCurrentOffset);
            }

            if (mRefreshOffsetCalculator == null)
            {
                mRefreshOffsetCalculator = new PRTDefaultRefreshOffsetCalculator();
            }
            int newRefreshOffset = mRefreshOffsetCalculator.calculateRefreshOffset(mRefreshInitOffset, mRefreshEndOffset, mRefreshHeader.getHeight(),
                    mTargetCurrentOffset, mTargetInitOffset, mTargetRefreshOffset);
            if (newRefreshOffset != mRefreshCurrentOffset)
            {
                ViewCompat.offsetTopAndBottom(mRefreshHeader, newRefreshOffset - mRefreshCurrentOffset);
                mRefreshCurrentOffset = newRefreshOffset;
                onMoveRefreshView(mRefreshCurrentOffset);
                if (mListener != null)
                {
                    mListener.onMoveRefreshView(mRefreshCurrentOffset);
                }
            }
        }
        return offset;
    }

    private void acquireVelocityTracker(final MotionEvent event)
    {
        if (null == mVelocityTracker)
        {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker()
    {
        if (null != mVelocityTracker)
        {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public int getRefreshInitOffset()
    {
        return mRefreshInitOffset;
    }

    public int getRefreshEndOffset()
    {
        return mRefreshEndOffset;
    }

    public int getTargetInitOffset()
    {
        return mTargetInitOffset;
    }

    public int getTargetRefreshOffset()
    {
        return mTargetRefreshOffset;
    }

    public void setTargetRefreshOffset(int targetRefreshOffset)
    {
        mEqualTargetRefreshOffsetToRefreshViewHeight = false;
        mTargetRefreshOffset = targetRefreshOffset;
    }

    public View getTargetView()
    {
        return mTargetView;
    }

    protected void onMoveTargetView(int offset)
    {

    }

    protected void onMoveRefreshView(int offset)
    {

    }


    private boolean hasFlag(int flag)
    {
        return (mScrollFlag & flag) == flag;
    }

    private void removeFlag(int flag)
    {
        mScrollFlag = mScrollFlag & ~flag;
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            int offsetY = mScroller.getCurrY();
            moveTargetViewTo(offsetY, false);
            if (offsetY <= 0 && hasFlag(FLAG_NEED_DELIVER_VELOCITY))
            {
                deliverVelocity();
                mScroller.forceFinished(true);
            }
            invalidate();
        } else if (hasFlag(FLAG_NEED_SCROLL_TO_INIT_POSITION))
        {
            removeFlag(FLAG_NEED_SCROLL_TO_INIT_POSITION);
            if (mTargetCurrentOffset != mTargetInitOffset)
            {
                mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetInitOffset - mTargetCurrentOffset);
            }
            invalidate();
        } else if (hasFlag(FLAG_NEED_SCROLL_TO_REFRESH_POSITION))
        {
            removeFlag(FLAG_NEED_SCROLL_TO_REFRESH_POSITION);
            if (mTargetCurrentOffset != mTargetRefreshOffset)
            {
                mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetRefreshOffset - mTargetCurrentOffset);
            } else
            {
                moveTargetViewTo(mTargetRefreshOffset, false, true);
            }
            invalidate();
        } else if (hasFlag(FLAG_NEED_DO_REFRESH))
        {
            removeFlag(FLAG_NEED_DO_REFRESH);
            moveTargetViewTo(mTargetRefreshOffset, false, true);
            onRefresh();
        } else
        {
            deliverVelocity();
        }
    }

    private void deliverVelocity()
    {
        if (hasFlag(FLAG_NEED_DELIVER_VELOCITY))
        {
            removeFlag(FLAG_NEED_DELIVER_VELOCITY);
            if (mScroller.getCurrVelocity() > mMiniVelocity)
            {
                info("deliver velocity: " + mScroller.getCurrVelocity());
                // if there is a velocity, pass it on
                if (mTargetView instanceof RecyclerView)
                {
                    ((RecyclerView) mTargetView).fling(0, (int) mScroller.getCurrVelocity());
                } else if (mTargetView instanceof AbsListView && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    ((AbsListView) mTargetView).fling((int) mScroller.getCurrVelocity());
                }
            }
        }
    }

    private void info(String msg)
    {
        if (BuildConfig.DEBUG)
        {
            //            KLog.i(TAG, msg);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN)
        {
            mNestScrollDurationRefreshing = mIsRefreshing;
        } else if (mNestScrollDurationRefreshing)
        {
            if (action == MotionEvent.ACTION_MOVE)
            {
                if (!mIsRefreshing)
                {
                    mNestScrollDurationRefreshing = false;
                    ev.setAction(MotionEvent.ACTION_DOWN);
                }
            } else
            {
                mNestScrollDurationRefreshing = false;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    public interface OnPullListener
    {

        void onMoveTarget(int offset);

        void onMoveRefreshView(int offset);

        void onRefresh();
    }


    public interface OnChildScrollUpCallback
    {
        boolean canChildScrollUp(PTRLayout parent, @Nullable View child);
    }

    public interface RefreshOffsetCalculator
    {

        /**
         * 通过 targetView 的当前位置、targetView 的初始和刷新位置以及 refreshView 的初始与结束位置计算 RefreshView 的位置。
         *
         * @param refreshInitOffset   RefreshView 的初始 offset。
         * @param refreshEndOffset    刷新时 RefreshView 的 offset。
         * @param refreshViewHeight   RerfreshView 的高度
         * @param targetCurrentOffset 下拉时 TargetView（ListView 或者 ScrollView 等）当前的位置。
         * @param targetInitOffset    TargetView（ListView 或者 ScrollView 等）的初始位置。
         * @param targetRefreshOffset 刷新时 TargetView（ListView 或者 ScrollView等）的位置。
         * @return RefreshView 当前的位置。
         */
        int calculateRefreshOffset(int refreshInitOffset, int refreshEndOffset, int refreshViewHeight,
                                   int targetCurrentOffset, int targetInitOffset, int targetRefreshOffset);
    }

    public interface IPTRRefreshHeader
    {
        void stop();

        void doRefresh();

        void onPull(int offset, int total, int overPull);
    }

    public void autoRefresh()
    {
        mScroller.startScroll(0, mTargetCurrentOffset, 0, mTargetRefreshOffset - mTargetCurrentOffset);
        mScrollFlag = FLAG_NEED_DO_REFRESH;
        onRefresh();
    }
}