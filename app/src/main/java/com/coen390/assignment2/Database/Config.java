package com.coen390.assignment2.Database;

public class Config {
    public static final String DATABASE_NAME = "students-db";
    public static final int DATABASE_VERSION = 1;

    public static final String STUDENT_TABLE_NAME = "student";

    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_SURNAME = "surname";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_GPA = "gpa";
    public static final String COLUMN_STUDENT_TIMESTAMP = "timestamp";

    public static final String ACCESS_TABLE_NAME = "access";

    public static final String COLUMN_ACCESS_ID = "_id";
    public static final String COLUMN_STUDENT_ID_ACCESS = "studentid";
    public static final String COLUMN_ACCESS_TYPE = "accesstype";
    public static final String COLUMN_ACCESS_TIMESTAMP = "timestamp";
}
