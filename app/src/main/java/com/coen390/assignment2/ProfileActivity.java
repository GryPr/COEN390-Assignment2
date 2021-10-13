package com.coen390.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    protected String surname, name, id, gpa, dateCreated;
    protected TextView userProfileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @SuppressLint("SetTextI18n")
    protected void setupUP() {
        userProfileInfo = findViewById(R.id.userProfileInfo);
        userProfileInfo.setText("Surname: " + surname + "\n" +
                "Name: " + name + "\n" +
                "ID: " + id + "\n" +
                "GPA: " + gpa + "\n" +
                "Profile Created: " + dateCreated);

    }
}