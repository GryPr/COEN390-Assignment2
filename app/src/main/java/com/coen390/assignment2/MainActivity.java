package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.coen390.assignment2.database.AppDatabase;
import com.coen390.assignment2.database.entity.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected AppDatabase database;

    protected enum Modes {
        NameMode,
        IDMode
    }

    protected ListView mainActivityList;
    protected TextView mainActivityInfo;
    protected FloatingActionButton addStudentFab;
    protected Modes mode;
    protected int profileCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDb(getApplicationContext());
        setupUI();
    }

    protected void setupUI() {
        mode = Modes.NameMode;

        // Setting the views
        mainActivityInfo = findViewById(R.id.mainActivityInfo);

        addStudentFab = findViewById(R.id.addStudentFab);
        addStudentFab.setOnClickListener(onClickAddStudentFab);

        mainActivityList = findViewById(R.id.mainActivityList);

        // Observing for changes in the database
        database.studentDao().getAllLive().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                refreshList();
            }
        });

        // First refresh of the list
        refreshList();
    }

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
                // do stuff
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
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student t1, Student t2) {
                return t1.surname.compareToIgnoreCase(t2.surname);
            }
        });
        // Merge the surname and name in a single string
        List<String> nameList = new ArrayList<>();
        for (Student student: students) {
            nameList.add(student.surname + ", " + student.name);
        }
        return nameList;
    }

    // Generates the list of IDs to be displayed
    private List<String> generateIDList(List<Student> students) {
        // Sort the list in increasing order
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student t1, Student t2) {
                return Integer.valueOf(t1.studentID).compareTo(t2.studentID);
            }
        });
        // Cast the integer IDs to string
        List<String> idList = new ArrayList<>();
        for (Student student: students) {
            idList.add(Integer.toString(student.studentID));
        }
        return idList;
    }

    // Set the list of events
    protected void setList(String[] list) {
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        mainActivityList.setAdapter(arr);
    }
}