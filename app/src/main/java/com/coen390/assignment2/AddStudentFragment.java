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

import com.coen390.assignment2.database.DatabaseHelper;
import com.coen390.assignment2.database.entity.AccessRecord;
import com.coen390.assignment2.database.entity.Student;

public class AddStudentFragment extends DialogFragment {

    protected DatabaseHelper database;

    protected enum InvalidInputs {
        ID_OutOfRange,
        GPA_OutOfRange,
        ID_AlreadyExists,
        EmptyFields
    }

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
        database = DatabaseHelper.getDb(getContext());
        setupUI();
        return currentView;
    }

    protected void setupUI() {
        // Setting the views
        editTextSurname = currentView.findViewById(R.id.editTextSurname);
        editTextName = currentView.findViewById(R.id.editTextName);

        editTextID = currentView.findViewById(R.id.editTextID);
        editTextID.setFilters(maxFilter(8));

        editTextGPA = currentView.findViewById(R.id.editTextGPA);
        editTextGPA.setFilters(maxFilter(4));

        cancelAddStudentButton = currentView.findViewById(R.id.cancelAddStudentButton);
        cancelAddStudentButton.setOnClickListener(onClickCancelAddStudentButton);

        saveStudentButton = currentView.findViewById(R.id.saveStudentButton);
        saveStudentButton.setOnClickListener(onClickSaveStudentButton);
    }

    protected InvalidInputs validateInputs(String idString, String gpaString) {
        // Check if empty
        if (idString.equals("") || gpaString.equals("")) {
            return InvalidInputs.EmptyFields;
        }

        // Convert the strings to proper values
        int id = Integer.parseInt(idString);
        float gpa = Float.parseFloat(gpaString);

        // Validate the conditions
        if (!(id >= 10000000 && id <= 99999999)) {
            return InvalidInputs.ID_OutOfRange;
        } else if (!(gpa >= 0 && gpa <= 4.301)) {
            return InvalidInputs.GPA_OutOfRange;
        } else if (!(database.studentDao().findById(id) == null)) {
            return InvalidInputs.ID_AlreadyExists;
        } else {
            return null;
        }
    }

    private final View.OnClickListener onClickSaveStudentButton = view -> {
        // Get inputs from fields
        String idString = editTextID.getText().toString();
        String surname = editTextSurname.getText().toString();
        String name = editTextName.getText().toString();
        String gpaString = editTextGPA.getText().toString();

        // Validate the inputs
        InvalidInputs inputValidation = validateInputs(idString, gpaString);

        // Parse string to float and int respectively
        // Put after input validation in case the fields are empty to avoid exceptions
        float gpa = Float.parseFloat(gpaString);
        int id = Integer.parseInt(idString);

        // Save data if there are no issues
        if (inputValidation == null) {

            // Creates the objects to insert in the database
            Student student = new Student(id, surname, name, gpa);
            AccessRecord accessRecord = new AccessRecord(id, "Created");

            // Insert the objects in the database
            database.studentDao().insertAll(student);
            database.accessRecordDao().insertAll(accessRecord);
            // If successful, close the fragment
            getDialog().dismiss();
        } else {
            String toastText;
            // Checks what the invalid input is about, and sets toast text
            switch (inputValidation) {
                case ID_OutOfRange:
                    toastText = "ID is out of range. Must be from 10000000 to 99999999.";
                    break;
                case GPA_OutOfRange:
                    toastText = "GPA is out of range. Must be from 0 to 4.3.";
                    break;
                case ID_AlreadyExists:
                    toastText = "ID already exists in the database.";
                    break;
                case EmptyFields:
                    toastText = "Some fields are empty!";
                    break;
                default:
                    toastText = "Invalid inputs";
            }
            // Display error toast
            Toast toast = Toast.makeText(currentView.getContext(), toastText, Toast.LENGTH_LONG);
            toast.show();
        }
    };

    // Max length filter for the edit text
    protected InputFilter[] maxFilter(int maxLength) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        return FilterArray;
    }

    // Cancel button dismisses the fragment
    private final View.OnClickListener onClickCancelAddStudentButton = view -> {
        getDialog().dismiss();
    };

}
