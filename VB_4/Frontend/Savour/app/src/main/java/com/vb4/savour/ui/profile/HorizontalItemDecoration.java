package com.vb4.savour.ui.profile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalItemDecoration extends DividerItemDecoration {
    /** The offset applied to the right */
    private final int offset;

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context       Current context, it will be used to access resources.
     * @param offset        The offset
     */
    public HorizontalItemDecoration(Context context, int offset) {
        super(context, LinearLayoutManager.HORIZONTAL);
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = offset;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {}
}
