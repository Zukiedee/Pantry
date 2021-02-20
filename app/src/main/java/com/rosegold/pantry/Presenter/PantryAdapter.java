package com.rosegold.pantry.Presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rosegold.pantry.MainActivity;
import com.rosegold.pantry.Model.DataBaseHelper;
import com.rosegold.pantry.Model.PantryModel;
import com.rosegold.pantry.View.AddItem;
import com.rosegold.pantry.R;


import java.util.List;

/**
 * To DO List Adapter
 */
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.MyViewHolder> {

    private List<PantryModel> tasksList;
    private final MainActivity activity;
    private final DataBaseHelper taskDB;
    private final TextView nudge;

    /**
     * Constructor Method
     * @param taskDB SQLite database
     * @param activity main activity to be displayed on
     */
    public PantryAdapter(DataBaseHelper taskDB, MainActivity activity, TextView nudge){
        this.activity = activity;
        this.taskDB = taskDB;
        this.nudge = nudge;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PantryModel item = tasksList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(item.getPrice());
    }

    /**
     * Converts input checkbox integer value to boolean
     * @param num 0 if false, 1 if true.
     * @return Returns false if input num is equal to 0
     */
    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<PantryModel> list){
        this.tasksList = list;
        notifyDataSetChanged();
        empty(list);
    }

    /**
     * Empty tasks placeholder on screen
     * If there are no tasks, a empty list placeholder is on screen
     * @param list database list
     */
    private void empty(List<PantryModel> list){
        if (list.isEmpty()){
            nudge.setVisibility(View.VISIBLE);
        }
        else {
            nudge.setVisibility(View.GONE);
        }
    }

    /**
     * Removes a task from the database
     * @param position task position in database
     */
    public void deleteTask(int position){
        PantryModel item = tasksList.get(position);
        taskDB.deleteTask(item.getId());
        tasksList.remove(position);
        Toast.makeText(activity.getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
        notifyItemRemoved(position);
        notifyDataSetChanged();
        empty(tasksList);
    }

    /**
     * Changes task item's description in DB
     * @param position task position
     */
    public void editItem(int position){
        PantryModel item = tasksList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getName());

        AddItem task = new AddItem();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemPrice;
        ImageView itemImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}