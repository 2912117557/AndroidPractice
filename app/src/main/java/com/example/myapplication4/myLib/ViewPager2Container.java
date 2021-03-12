package com.example.myapplication4.myLib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Objects;

import static java.lang.Math.abs;


/**
 * 解决ViewPager2嵌套问题，滑动冲突
 * 由于ViewPager2被设置成了final，我们无法通过继承的方式来处理，因此就需要我们在ViewPager2外部加一层自定义的Layout。
 * 此Layout包含的ViewPagers是内部的那个，主要根据是否允许外部ViewPager2拦截事件（requestDisallowInterceptTouchEvent）来解决滑动冲突
 */
public class ViewPager2Container extends LinearLayout {

    private ViewPager2 mViewPager2;
    private float startX, startY;

    public ViewPager2Container(Context context) {
        this(context, null);
    }

    public ViewPager2Container(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPager2Container(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ViewPager2Container(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ViewPager2) {
                mViewPager2 = (ViewPager2) view;
                break;
            }
        }
        if (mViewPager2 == null) {
            throw new IllegalStateException("The root child of ViewPager2Container must contains a ViewPager2");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean doNotNeedHandle = (mViewPager2.getAdapter() == null)
                || (!mViewPager2.isUserInputEnabled())
                || (mViewPager2.getAdapter() != null && mViewPager2.getAdapter().getItemCount() <= 1);

        if (doNotNeedHandle) {
            return super.onInterceptTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float disX = abs(endX - startX);
                float disY = abs(endY - startY);
                if (mViewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    onVerticalActionMove(endY, disX, disY);
                } else if (mViewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    onHorizontalActionMove(endX, disX, disY);
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 水平滑动
     * 如果是多个Item，且当前是第一个页面，那么只能拦截向左的滑动事件，向右的滑动事件就不应该由ViewPager2拦截了；
     * 如果是多个Item，且当前是最后一个页面，那么只能拦截向右的滑动事件，向左的滑动事件不应该由当前的ViewPager2拦截；
     * 如果是多个Item，且是中间页面，那么无论向左还是向右的事件都应该由ViewPager2拦截；
     */
    public void onHorizontalActionMove(float endX, float disX, float disY) {
        if (disX > disY) {
            int currentItem = mViewPager2.getCurrentItem();
            int itemCount = Objects.requireNonNull(mViewPager2.getAdapter()).getItemCount();
            if (currentItem == 0 && endX - startX > 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(currentItem != itemCount - 1
                        || endX - startX > 0);
            }
        } else if (disY > disX) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    public void onVerticalActionMove(float endY, float disX, float disY) {
        if (disY > disX) {
            int currentItem = mViewPager2.getCurrentItem();
            int itemCount = Objects.requireNonNull(mViewPager2.getAdapter()).getItemCount();
            if (currentItem == 0 && endY - startY > 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(currentItem != itemCount - 1
                        || endY - startY > 0);
            }
        } else if (disX > disY) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }


}
