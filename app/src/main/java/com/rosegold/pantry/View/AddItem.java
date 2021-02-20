package com.rosegold.pantry.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rosegold.pantry.Model.DataBaseHelper;
import com.rosegold.pantry.Model.PantryModel;
import com.rosegold.pantry.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Adds new or updates task from bottom of the screen
 */
public class AddItem extends BottomSheetDialogFragment {

    public static final String TAG = "AddTask";

    //widgets
    private EditText newTask;
    private Button saveButton;
    private DataBaseHelper taskDB;

    public static AddItem newInstance(){
        return new AddItem();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_newtask , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Design_BottomSheetDialog);
        newTask = view.findViewById(R.id.inputText);
        saveButton = view.findViewById(R.id.button_save);

        taskDB = new DataBaseHelper(getActivity());

        boolean updateTask = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newTask.setFocusedByDefault(true);
        }

        final Bundle bundle = getArguments();
        if (bundle != null){
            updateTask = true;
            String task = bundle.getString("task");
            newTask.setText(task);

            if (task.length() > 0 ){
                saveButton.setEnabled(false);
            }

        }
        newTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                taskTextListener(text);
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                taskTextListener(text);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        final boolean update = updateTask;
        saveButton.setOnClickListener(v -> {
            String text = newTask.getText().toString();

            if (update){
                taskDB.updateTask(bundle.getInt("id") , text);
                Toast.makeText(requireActivity().getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();

            }else{
                PantryModel item = new PantryModel();
                item.setName(text);
                item.setPrice(0);
                taskDB.insertTask(item);
                Toast.makeText(requireActivity().getApplicationContext(), "Task added", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });


    }

    /**
     * Sets save button to enabled if the input string is not empty
     * Event Handler
     * @param text input task text
     */
    private void taskTextListener(CharSequence text){
        saveButton.setEnabled(!text.toString().equals(""));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof onDialogCloseListener){
            ((onDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}