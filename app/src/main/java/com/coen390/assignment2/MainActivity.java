package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected AppDatabase database;

    ListView mainActivityList;
    TextView mainActivityInfo;
    FloatingActionButton addStudentFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDb(getApplicationContext());
        setupUI();
    }

    protected void setupUI() {
        mainActivityInfo = findViewById(R.id.mainActivityInfo);

        addStudentFab = findViewById(R.id.addStudentFab);
        addStudentFab.setOnClickListener(onClickAddStudentFab);

        mainActivityList = findViewById(R.id.mainActivityList);
        List<String> nameList = generateNameList();
        setList(nameList.toArray(new String[nameList.size()]));
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

    private final View.OnClickListener onClickAddStudentFab = view -> {
        AddStudentFragment dialog = new AddStudentFragment();
        dialog.show(getSupportFragmentManager(), "AddStudentFragment");
    };

    private List<String> generateNameList() {
        List<String> surname = database.studentDao().getAllSurname();
        List<String> name = database.studentDao().getAllName();

        List<String> nameList = new ArrayList<>();
        for (int i = 0; i < Math.min(surname.size(), name.size()); i++) {
            nameList.add(surname.get(i) + ", " + name.get(i));
        }
        return nameList;
    }

    // Set the list of events
    protected void setList(String[] list) {
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        mainActivityList.setAdapter(arr);
    }
}