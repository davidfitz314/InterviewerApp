package com.example.interviewerapp.interview_database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface InterviewDao {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Person person);

    @Query("DELETE from interview_table")
    void deleteAll();

    @Query("SELECT * from interview_table")
    LiveData<List<Person>> getAllInterviews();

    @Query("SELECT * from interview_table ORDER BY id_key DESC LIMIT 1")
    LiveData<Person> getAnyPerson();

    @Query("SELECT * from interview_table LIMIT 1")
    Person[] getAnyInterviewee();

    @Delete
    void deleteInterview(Person person);

    @Update
    void updateInterview(Person person);

    @Query("SELECT * from interview_table where id_key = :id LIMIT 1")
    LiveData<Person> getPersonById(int id);
}
