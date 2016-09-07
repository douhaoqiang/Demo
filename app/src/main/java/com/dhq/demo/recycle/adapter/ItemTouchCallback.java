package com.dhq.demo.recycle.adapter;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public class ItemTouchCallback extends ItemTouchHelper.Callback {

    private ItemTouchCallbackListener callbackListener;

    public ItemTouchCallback(ItemTouchCallbackListener callbackListener){
        this.callbackListener=callbackListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if(callbackListener!=null){
            callbackListener.dragItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(callbackListener!=null){
            callbackListener.removeItem(viewHolder.getAdapterPosition());
        }
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        int width=recyclerView.getWidth();//item的宽度
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            //表示上下拖动
//                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //表示左右滑动
            viewHolder.itemView.setAlpha(1-Math.abs(dX/width)+0.2f);
//            viewHolder.itemView.setTranslationX(dX);
            viewHolder.itemView.setX(dX);
        }

    }

    public interface ItemTouchCallbackListener{

        void dragItem(int dragPosition,int targetPosition);
        void removeItem(int adapterPosition);

    }

}

