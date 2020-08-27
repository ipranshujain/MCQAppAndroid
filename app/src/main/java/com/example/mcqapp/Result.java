package com.example.mcqapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "results")
public class Result {
    @PrimaryKey
    int id;
    @ColumnInfo(name="mark")
    int mark;
    @ColumnInfo(name="total")
    int total;
    @ColumnInfo(name="percent")
    float percent;

}
