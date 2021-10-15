package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coen390.assignment2.Database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    protected DatabaseHelper database;

    ListView mainActivityList;
    TextView mainActivityInfo;
    FloatingActionButton addStudentFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(getApplicationContext());
        setupUI();
    }

    protected void setupUI() {
        mainActivityList = findViewById(R.id.mainActivityList);
        mainActivityInfo = findViewById(R.id.mainActivityInfo);

        addStudentFab = findViewById(R.id.addStudentFab);
        addStudentFab.setOnClickListener(onClickAddStudentFab);

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
}