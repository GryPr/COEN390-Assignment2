package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView mainActivityList;
    TextView mainActivityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void setupUI() {
        mainActivityList = findViewById(R.id.mainActivityList);
        mainActivityInfo = findViewById(R.id.mainActivityInfo);
    }

    
}