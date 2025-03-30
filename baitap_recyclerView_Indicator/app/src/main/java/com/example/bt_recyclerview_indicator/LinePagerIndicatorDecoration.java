package com.example.bt_recyclerview_indicator;


import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
    private final Paint activePaint = new Paint();
    private final Paint inactivePaint = new Paint();
    private final int indicatorCount = 3;
    public LinePagerIndicatorDecoration() {
        inactivePaint.setColor(0xFF888888);
        inactivePaint.setStrokeWidth(5);
        activePaint.setColor(0xFFFFFFFF);
        activePaint.setStrokeWidth(5);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            int spanCount = layoutManager.getSpanCount();
            int currentPage = firstVisibleItem / spanCount;

            float totalWidth = indicatorCount * 20;
            float startX = (parent.getWidth() - totalWidth) / 2;
            float y = parent.getHeight() - 20;

            for (int i = 0; i < indicatorCount; i++) {
                Paint paint = (i == currentPage) ? activePaint : inactivePaint;
                canvas.drawCircle(startX + i * 20, y, 5, paint);
            }
        }
    }
}



