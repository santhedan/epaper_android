package com.dandekar.epaper.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public final class ThumbnailDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    public static final int GRID = 2;

    private Drawable mDivider;

    private int mOrientation;

    private int mSpan;

    public ThumbnailDecoration(Context context, int orientation, int span, Drawable divider) {
        mDivider = divider;
        mSpan = span;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST && orientation != GRID) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL_LIST) {
            int columns = parent.getAdapter().getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                // First cell
                outRect.set(mDivider.getIntrinsicWidth(), 0, mDivider.getIntrinsicWidth() / 2, 0);
            } else if (position == (columns - 1)) {
                //Last cell
                outRect.set(mDivider.getIntrinsicWidth() / 2, 0, mDivider.getIntrinsicWidth(), 0);
            } else {
                // Middle cell
                outRect.set(mDivider.getIntrinsicWidth() / 2, 0, mDivider.getIntrinsicWidth() / 2, 0);
            }
        } else if(mOrientation == VERTICAL_LIST) {
            int rows = parent.getAdapter().getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                // First cell
                outRect.set(0, mDivider.getIntrinsicHeight(), 0, mDivider.getIntrinsicHeight() / 2);
            } else if (position == (rows - 1)) {
                // Last cell
                outRect.set(0, mDivider.getIntrinsicHeight() / 2, 0, mDivider.getIntrinsicHeight());
            } else {
                // Middle cell
                outRect.set(0, mDivider.getIntrinsicHeight() / 2, 0, mDivider.getIntrinsicHeight() / 2);
            }
        } else {
            int childCount = parent.getAdapter().getItemCount();
            int rows = ((childCount % mSpan) == 0)? (childCount / mSpan): (childCount / mSpan) + 1;
            int position = parent.getChildAdapterPosition(view);
            // Get the position - mSpan mod
            int mod = position % mSpan;
            // Is the cell aligned to the left of screen?
            boolean isLeft = (mod == 0);
            // Is the cell aligned to the right of screen?
            boolean isRight = (mod == (mSpan - 1));
            // Is the cell part of the first row?
            boolean isTop = (position < mSpan);
            // Is the cell part of the last row?
            boolean isBottom = ((position / mSpan) == (rows - 1));
            // Temp vars
            int left, top, right, bottom = 0;
            // Left columns
            if (isLeft) {
                left = mDivider.getIntrinsicWidth();
                if (isTop) {
                    top = mDivider.getIntrinsicHeight();
                    right = mDivider.getIntrinsicWidth() / 2;
                    bottom = mDivider.getIntrinsicHeight() / 2;
                } else if (isBottom) {
                    top = mDivider.getIntrinsicHeight() / 2;
                    right = mDivider.getIntrinsicWidth() / 2;
                    bottom = mDivider.getIntrinsicHeight();
                } else {
                    top = mDivider.getIntrinsicHeight() / 2;
                    right = mDivider.getIntrinsicWidth() / 2;
                    bottom = mDivider.getIntrinsicHeight() / 2;
                }
                outRect.set(left, top, right, bottom);
            }
            // Right columns
            if (isRight) {
                right = mDivider.getIntrinsicWidth();
                if (isTop) {
                    top = mDivider.getIntrinsicHeight();
                    left = mDivider.getIntrinsicWidth() / 2;
                    bottom = mDivider.getIntrinsicHeight() / 2;
                } else if (isBottom) {
                    top = mDivider.getIntrinsicHeight() / 2;
                    left = mDivider.getIntrinsicWidth() / 2;
                    bottom = mDivider.getIntrinsicHeight();
                } else {
                    top = mDivider.getIntrinsicHeight() / 2;
                    left = mDivider.getIntrinsicWidth() / 2;
                    bottom = mDivider.getIntrinsicHeight() / 2;
                }
                outRect.set(left, top, right, bottom);
            }
            // Middle columns
            if (!isLeft && !isRight) {
                left = mDivider.getIntrinsicWidth() / 2;
                right = mDivider.getIntrinsicWidth() / 2;
                if (isTop) {
                    top = mDivider.getIntrinsicHeight();
                    bottom = mDivider.getIntrinsicHeight() / 2;
                } else if (isBottom) {
                    top = mDivider.getIntrinsicHeight() / 2;
                    bottom = mDivider.getIntrinsicHeight();
                } else {
                    top = mDivider.getIntrinsicHeight() / 2;
                    bottom = mDivider.getIntrinsicHeight() / 2;
                }
                outRect.set(left, top, right, bottom);
            }
        }
    }
}
