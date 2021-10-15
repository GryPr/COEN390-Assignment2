package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.coen390.assignment2.database.DatabaseHelper;
import com.coen390.assignment2.database.entity.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected DatabaseHelper database;

    protected enum Modes {
        NameMode,
        IDMode
    }

    protected ListView mainActivityList;
    protected TextView mainActivityInfo;
    protected FloatingActionButton addStudentFab;
    protected Modes mode;
    protected List<Student> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = DatabaseHelper.getDb(getApplicationContext());
        setupUI();
    }

    protected void setupUI() {
        // Setting the default view mode
        mode = Modes.NameMode;

        // Setting the views
        mainActivityInfo = findViewById(R.id.mainActivityInfo);

        addStudentFab = findViewById(R.id.addStudentFab);
        addStudentFab.setOnClickListener(onClickAddStudentFab);

        mainActivityList = findViewById(R.id.mainActivityList);
        mainActivityList.setOnItemClickListener(onClickListItem);

        // Observing for changes in the database
        database.studentDao().getAllLive().observe(this, students -> refreshList());

        // First refresh of the list
        refreshList();
    }

    // Binds the activity with the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // Set behavior when an option menu item is selected
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleMainActivityDisplay:
                if (mode == Modes.IDMode) {
                    mode = Modes.NameMode;
                } else {
                    mode = Modes.IDMode;
                }
                refreshList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Listener for when the add student FAB is clicked
    private final View.OnClickListener onClickAddStudentFab = view -> {
        AddStudentFragment dialog = new AddStudentFragment();
        dialog.show(getSupportFragmentManager(), "AddStudentFragment");
    };

    // Refreshes the list displayed
    private void refreshList() {
        List<String> list;
        String listTitle;
        if (mode == Modes.IDMode) {
            list = generateIDList(database.studentDao().getAll());
            listTitle = list.size() + " profiles, sorted in ascending order by ID";
        } else {
            list = generateNameList(database.studentDao().getAll());
            listTitle = list.size() + " profiles, sorted alphabetically by surname";
        }
        mainActivityInfo.setText(listTitle);
        setList(list.toArray(new String[list.size()]));
    }

    // Generates the list of names to be displayed
    private List<String> generateNameList(List<Student> students) {
        // Sort the list in alphabetical order
        Collections.sort(students, (t1, t2) -> t1.surname.compareToIgnoreCase(t2.surname));
        currentList = students;
        // Merge the surname and name in a single string
        List<String> nameList = new ArrayList<>();
        int i = 0; // line counter
        for (Student student: students) {
            i++;
            nameList.add(i + ". " + student.surname + ", " + student.name);
        }
        return nameList;
    }

    // Generates the list of IDs to be displayed
    private List<String> generateIDList(List<Student> students) {
        // Sort the list in increasing order
        Collections.sort(students, (t1, t2) -> Integer.compare(t1.studentID, t2.studentID));
        currentList = students;
        // Cast the integer IDs to string
        List<String> idList = new ArrayList<>();
        int i = 0; // line counter
        for (Student student: students) {
            i++;
            idList.add(i + ". " + student.studentID);
        }
        return idList;
    }

    // Set the list of profiles
    protected void setList(String[] list) {
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        mainActivityList.setAdapter(arr);
    }

    // Click listener for each item
    private final AdapterView.OnItemClickListener onClickListItem = (parent, view, position, id) -> {
        Student selectedStudent = currentList.get((int)id);
        Intent intent = new Intent(this, ProfileActivity.class);
        // Pass the student object to the activity
        intent.putExtra("student", selectedStudent);
        startActivity(intent);
    };
}