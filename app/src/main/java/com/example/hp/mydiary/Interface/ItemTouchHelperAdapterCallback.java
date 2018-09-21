package com.example.hp.mydiary.Interface;

public interface ItemTouchHelperAdapterCallback {
    /**
     * 当侧滑删除动作的时候回调
     *
     * @param adapterPosition
     */

    void onItemSwiped(int adapterPosition);


    /**
     * 当拖拽的时候回调
     *
     * @param fromPosition
     * @param toPosition
     * @return
     */

    void onItemMove(int fromPosition, int toPosition);
}
