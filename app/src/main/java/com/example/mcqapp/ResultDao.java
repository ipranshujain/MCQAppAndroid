package com.example.mcqapp;

import androidx.room.Dao;
import androidx.room.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Dao
public interface ResultDao {
    @Query("select mark from results where id=:id")
    int getMark(int id);
    @Query("select percent from results where id=:id")
    float getPercent(int id);
    @Query("select total from results where id=:id")
    int getTotal(int id);
    @Query("insert into results (mark,percent,total) values(:mark,:percent,:total)")
    void create(int mark, int total,float percent);
    @Query("select count(*) from results")
    int getSize();

}
