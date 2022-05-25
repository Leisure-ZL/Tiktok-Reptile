package cn.edu.swu.reptile_android.ui.video;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PagerLayoutManager extends LinearLayoutManager {

    private OnSnapChangedListener onSnapChangedListener;

    public PagerLayoutManager(Context context) {
        super(context);
        init(context);
    }

    private PagerSnapHelper helper;

    private void init(Context context) {

        helper = new PagerSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                return position;
            }
        };
    }

    @Override
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        helper.attachToRecyclerView(recyclerView);
    }

    public interface OnSnapChangedListener {
        void onSnapChanged(int position);
    }

    public void setOnSnapChangedListener(OnSnapChangedListener onSnapChangedListener) {
        this.onSnapChangedListener = onSnapChangedListener;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            View view = helper.findSnapView(this);
            if (view != null) {
                int position = getPosition(view);
                if (onSnapChangedListener != null && getChildCount() == 1) {
                    onSnapChangedListener.onSnapChanged(position);
                }
            }
        }
    }


}
