package com.example.hp.mydiary.Utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.hp.mydiary.Interface.ItemTouchHelperAdapterCallback;

public class DiaryItemTouchHelperAdapterCallback extends ItemTouchHelper.Callback {

    public final ItemTouchHelperAdapterCallback adapterCallback;

    public DiaryItemTouchHelperAdapterCallback (ItemTouchHelperAdapterCallback adapter) {
        adapterCallback = adapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;

        int swipeFlags = ItemTouchHelper.LEFT;//左滑
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapterCallback.onItemSwiped(viewHolder.getAdapterPosition());
    }

}
