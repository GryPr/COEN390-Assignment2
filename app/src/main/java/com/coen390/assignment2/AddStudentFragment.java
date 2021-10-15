package com.coen390.assignment2;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.coen390.assignment2.database.AppDatabase;
import com.coen390.assignment2.database.entity.Student;

public class AddStudentFragment extends DialogFragment {

    protected AppDatabase database;

    protected EditText editTextSurname;
    protected EditText editTextName;
    protected EditText editTextID;
    protected EditText editTextGPA;
    protected Button cancelAddStudentButton;
    protected Button saveStudentButton;
    protected View currentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_add_student, container, false);
        database = AppDatabase.getDb(getContext());
        setupUI();
        return currentView;
    }

    protected void setupUI() {
        editTextSurname = currentView.findViewById(R.id.editTextSurname);
        editTextName = currentView.findViewById(R.id.editTextName);

        editTextID = currentView.findViewById(R.id.editTextID);
        editTextID.setFilters(setFilters(8));

        editTextGPA = currentView.findViewById(R.id.editTextGPA);
        editTextGPA.setFilters(setFilters(4));

        cancelAddStudentButton = currentView.findViewById(R.id.cancelAddStudentButton);
        cancelAddStudentButton.setOnClickListener(onClickCancelAddStudentButton);

        saveStudentButton = currentView.findViewById(R.id.saveStudentButton);
        saveStudentButton.setOnClickListener(onClickSaveStudentButton);
    }

    protected boolean validateInputs() {
        String idString = editTextID.getText().toString();
        String gpaString = editTextGPA.getText().toString();
        if (idString.equals("") || gpaString.equals("")) {
            return false;
        }
        int id = Integer.parseInt(idString);
        float gpa = Float.parseFloat(gpaString);
        return (id >= 10000000 && id <= 99999999) && (gpa >= 0 && gpa <= 4.301);
    }

    private final View.OnClickListener onClickSaveStudentButton = view -> {
        // Save data
        if (validateInputs()) {
            String idString = editTextID.getText().toString();
            int id = Integer.parseInt(idString);

            String surname = editTextSurname.getText().toString();
            String name = editTextName.getText().toString();

            String gpaString = editTextGPA.getText().toString();
            float gpa = Float.parseFloat(gpaString);

            Student student = new Student(id, surname, name, gpa);

            // If successful, close the fragment
            database.studentDao().insertAll(student);
            getDialog().dismiss();
        } else {
            // Display error toast
            Toast toast = Toast.makeText(currentView.getContext(), "Invalid Inputs", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    // Max length filter for the edit text
    protected InputFilter[] setFilters(int maxLength) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        return FilterArray;
    }

    private final View.OnClickListener onClickCancelAddStudentButton = view -> {
        getDialog().dismiss();
    };

}
