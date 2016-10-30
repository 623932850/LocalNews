package com.my.localnews.news;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by xiangjianhua on 16/10/9.
 */
public class HorizontalScrollView extends ViewGroup {

    private int mTouchSlop;
    private Scroller mScroller;
    private GestureDetector mGestureDetector;
    private float mDownX;
    private float mLastMoveX;
    private float mMoveX;
    private int leftBorder, rightBorder;


    public HorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mScroller = new Scroller(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int len = getChildCount();
        for(int i=0; i<len; i++){
            View view = getChildAt(i);
            view.measure(view.getMeasuredWidth(), heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean isChanged, int left, int top, int right, int bottom) {
        if(isChanged){
            int len = getChildCount();
            for(int i=0; i<len; i++){
                View view = getChildAt(i);

                view.layout(i * view.getMeasuredWidth(), 0, (i + 1) * view.getMeasuredWidth(), view.getMeasuredHeight());
            }
            if(len > 0){
                leftBorder = getChildAt(0).getLeft();
                rightBorder = getChildAt(getChildCount() - 1).getRight();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mLastMoveX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getRawX();
                float diff = Math.abs(mMoveX - mLastMoveX);
                if(diff > mTouchSlop){
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getRawX();
                int scrollX = (int)(mLastMoveX - mMoveX);
                if(getScrollX() + scrollX < 0){
                    scrollTo(0, 0);
                    return true;
                }else if(getScrollX() + scrollX > (rightBorder - getWidth())){
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                mLastMoveX = mMoveX;
                scrollBy(scrollX, 0);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(event);
    }
}
