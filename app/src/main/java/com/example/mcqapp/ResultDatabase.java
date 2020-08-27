package com.example.mcqapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Result.class},version=1)
public abstract class ResultDatabase extends RoomDatabase {
    public abstract  ResultDao resultDao();
}
