package com.coen390.assignment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddStudentFragment extends DialogFragment {

    EditText editTextSurname;
    EditText editTextName;
    EditText editTextID;
    EditText editTextGPA;
    Button cancelAddStudentButton;
    Button saveStudentButton;
    View currentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_add_student, container, false);
        setupUI();
        return currentView;
    }

    protected void setupUI() {
        editTextSurname = currentView.findViewById(R.id.editTextSurname);
        editTextName = currentView.findViewById(R.id.editTextName);
        editTextID = currentView.findViewById(R.id.editTextID);
        editTextGPA = currentView.findViewById(R.id.editTextGPA);

        cancelAddStudentButton = currentView.findViewById(R.id.cancelAddStudentButton);
        cancelAddStudentButton.setOnClickListener(onClickCancelAddStudentButton);
        saveStudentButton = currentView.findViewById(R.id.saveStudentButton);
    }

    protected boolean validateInputs() {
        String idString = editTextID.getText().toString();
        int id = Integer.parseInt(idString);
        String gpaString = editTextGPA.getText().toString();
        float gpa = Float.parseFloat(gpaString);
        return (id >= 10000000 && id <= 99999999) && (gpa >= 0 && gpa <= 4.3);
    }

    private final View.OnClickListener onClickSaveStudentButton = view -> {
        if (validateInputs()) {
            // Save data
        } else {
            // Display error toast
            Toast toast = Toast.makeText(currentView.getContext(), "Invalid Inputs", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private final View.OnClickListener onClickCancelAddStudentButton = view -> {
        getDialog().dismiss();
    };

}
