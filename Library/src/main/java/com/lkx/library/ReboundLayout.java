package com.lkx.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作者: LKX
 * 时间: 2018/1/6
 * 描述: 回弹View
 */

public class ReboundLayout extends ScrollView {

    private float mStartY;
    private ValueAnimator mValueAnimator;
    private View mView;
    private int mDistance;
    private int mTopHeight; //当前距离顶部的距离
    private boolean isPause; //是否处于回弹时被手指摁住暂停状态
    private int mPauseHeight; //暂停时的高度

    public ReboundLayout(Context context) {
        this(context, null, 0);
    }

    public ReboundLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //给子View排版
        mView = getChildAt(0);
        mTopHeight = (t + mDistance) / 3;
//        Log.d(TAG, "onLayout: "+mTopHeight);
        mView.layout(l, mTopHeight, r, b);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下时的Y坐标
                mStartY = event.getY();
                //记录按下时的padding值
                //如果当前正处于回弹状态,按下时停止回弹
                if (mValueAnimator != null && mValueAnimator.isRunning()) {
                    mValueAnimator.pause();
                    isPause = true;
                    mPauseHeight = mTopHeight;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //获取手指滑动的距离
                if (isPause) {
                    //如果处于暂停状态,就从暂停的地方继续
                    mDistance = (int) (event.getY() - mStartY + mPauseHeight);
                } else {
                    mDistance = (int) (event.getY() - mStartY);
                }
                if (mDistance > 0) {
                    requestLayout();
                }
                break;
            case MotionEvent.ACTION_UP: //当手指松开时进行回弹
                if (mDistance <= 0) {
                    return super.onTouchEvent(event);
                }
                final int animationCount = mTopHeight;
                mValueAnimator = ValueAnimator.ofInt(animationCount);
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        mDistance = animationCount - animatedValue;
                        requestLayout();
                    }
                });
                mValueAnimator.setDuration(500);
                mValueAnimator.start();
                mValueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isPause = false;
                    }
                });
                break;
        }
        return true;
    }
}
