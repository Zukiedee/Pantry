package com.rosegold.pantry.View;

import android.graphics.Canvas;
import android.graphics.Color;

import com.rosegold.pantry.Presenter.PantryAdapter;
import com.rosegold.pantry.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Task item touch handler on Recyclerview
 */
public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {

    private final PantryAdapter adapter;

    /**
     * Constructor Method
     * @param adapter Recyclerview adapter
     */
    public RecyclerViewTouchHelper(PantryAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    /**
     * Task item move functionality disabled.
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Handles swipes left and right
     * @param viewHolder adapter
     * @param direction Swiped left = edit item. Swiped right = delete item.
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT){
            adapter.deleteTask(position);
            adapter.notifyDataSetChanged();
        }else{
            adapter.editItem(position);

        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        String editTask = "Edit";
        String deleteTask = "Delete";

    }
}