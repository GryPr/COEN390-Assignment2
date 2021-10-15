package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.coen390.assignment2.database.DatabaseHelper;
import com.coen390.assignment2.database.entity.AccessRecord;
import com.coen390.assignment2.database.entity.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    protected DatabaseHelper database;

    protected TextView userProfileInfo;
    protected Student student;
    protected ListView accessHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Retrieves the student object from the intent
        student = (Student) getIntent().getSerializableExtra("student");

        // Set title of activity
        setTitle(student.name + " " + student.surname + "'s Profile");

        // Get the database
        database = DatabaseHelper.getDb(getApplicationContext());

        // Inserts the access record for opening the profile
        AccessRecord accessRecord = new AccessRecord(student.studentID, "Opened");
        database.accessRecordDao().insertAll(accessRecord);

        setupUI();
    }

    @SuppressLint("SetTextI18n")
    protected void setupUI() {
        // Set the view
        userProfileInfo = findViewById(R.id.userProfileInfo);
        accessHistoryList = findViewById(R.id.accessHistoryList);
        // Set the profile info
        userProfileInfo.setText("Surname: " + student.surname + "\n" +
                "Name: " + student.name + "\n" +
                "ID: " + student.studentID + "\n" +
                "GPA: " + student.gpa + "\n" +
                "Profile Created: " + student.getFormattedDate());
        refreshList();
    }

    protected void refreshList() {
        List<AccessRecord> accessRecords = database.accessRecordDao().findByStudentId(student.studentID);
        setList(generateAccessRecordList(accessRecords).toArray(new String[0]));
    }

    protected List<String> generateAccessRecordList(List<AccessRecord> accessRecords) {
        // Reverse list to show the most recent entries first
        Collections.reverse(accessRecords);
        // Merge the date and access type in one string
        List<String> entryList = new ArrayList<>();
        for (AccessRecord accessRecord: accessRecords) {
            entryList.add(accessRecord.getFormattedDate() + " " + accessRecord.accesstype);
        }
        return entryList;
    }

    // Set the list of access events
    protected void setList(String[] list) {
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        accessHistoryList.setAdapter(arr);
    }

    // Binds the activity with the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    // Set behavior when an option menu item is selected
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteProfile:
                // Delete student, and add a record concerning the deleted student
                database.studentDao().delete(student);
                database.accessRecordDao().insertAll(new AccessRecord(student.studentID, "Deleted"));
                // Return to main activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Add a record when exiting the activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccessRecord accessRecord = new AccessRecord(student.studentID, "Closed");
        database.accessRecordDao().insertAll(accessRecord);
    }

}