package com.sty.recyclerview.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by shity on 2017/9/27/0027.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = DividerGridItemDecoration.class.getSimpleName();

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    //onDraw方法优先于drawChildren
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent){
        //列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent){
        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            //Log.i(TAG, "mDivider.getIntrinsicWidth(): " + mDivider.getIntrinsicWidth());
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            //Log.i(TAG, "mDivider.getIntrinsicHeight(): " + mDivider.getIntrinsicHeight());
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent){
        final int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            if( (pos + 1) % spanCount == 0) { //如果是最后一列，不需要绘制右边
                return true;
            }
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if(orientation == StaggeredGridLayoutManager.VERTICAL){
                if((pos + 1) % spanCount == 0) { //如果是最后一列，不需要绘制右边
                    return true;
                }
            }else{
                childCount = childCount - childCount % spanCount;
                if(pos >= childCount){ //如果是最后一列，不需要绘制右边
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            childCount = childCount - childCount % spanCount;
            if(pos >= childCount){ //如果是最后一行，不需要绘制底部
                return true;
            }
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if(orientation == StaggeredGridLayoutManager.VERTICAL){ //纵向滚动
                childCount = childCount - childCount % spanCount;
                if(pos >= childCount){ //如果是最后一行，不需要绘制底部
                    return true;
                }
            }else{ //横向滚动
                if((pos + 1) % spanCount == 0){
                    return true;
                }
            }
        }
        return false;
    }

    //getItemOffsets可以通过outRect.set()为每个Item设置一定的偏移量，类似margin,padding, 主要用于绘制Decorator
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if(isLastRaw(parent, itemPosition, spanCount, childCount)){
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }else if(isLastColumn(parent, itemPosition, spanCount, childCount)){
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }
        //Log.i(TAG, "item position---------: " + itemPosition);
    }
}