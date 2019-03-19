package com.example.interviewerapp.interview_database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "interview_table")
public class Person {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_key")
    private int mId;

    @NonNull
    @ColumnInfo(name = "interview_first_name")
    private String mFirstName;

    @NonNull
    @ColumnInfo(name = "interview_last_name")
    private String mLastName;

    @ColumnInfo(name = "interview_person_title")
    private String mPersonTitle;

    @NonNull
    @ColumnInfo(name = "interview_date")
    private Date mDate;

    @ColumnInfo(name = "interview_location")
    private String mLocation;

    @NonNull
    @ColumnInfo(name = "interview_title")
    private String mInterviewTitle;

    @ColumnInfo(name = "interview")
    private String mInterview;

    @ColumnInfo(name = "interview_notes")
    private String mInterviewNotes;

    public Person(){
        this.mFirstName = "";
        this.mLastName = "";
        this.mPersonTitle = "";
        this.mDate = new Date();
        this.mLocation = "";
        this.mInterviewTitle = "";
        this.mInterview = "";
        this.mInterviewNotes = "";
    }

    @Ignore
    public Person(int id, @NonNull String first_name, @NonNull String last_name, String person_title, @NonNull Date interview_date, String interview_location, @NonNull String interview_title, String interview, String interview_notes){
        this.mId = id;
        this.mFirstName = first_name;
        this.mLastName = last_name;
        this.mPersonTitle = person_title;
        this.mDate = interview_date;
        this.mLocation = interview_location;
        this.mInterviewTitle = interview_title;
        this.mInterview = interview;
        this.mInterviewNotes = interview_notes;
    }

    @Ignore
    public Person(@NonNull String first_name, @NonNull String last_name, String person_title, @NonNull Date interview_date, String interview_location, @NonNull String interview_title, String interview, String interview_notes){
        this.mFirstName = first_name;
        this.mLastName = last_name;
        this.mPersonTitle = person_title;
        this.mDate = interview_date;
        this.mLocation = interview_location;
        this.mInterviewTitle = interview_title;
        this.mInterview = interview;
        this.mInterviewNotes = interview_notes;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setFirstName(@NonNull String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setLastName(@NonNull String mLastName) {
        this.mLastName = mLastName;
    }

    public void setPersonTitle(String mPersonTitle) {
        this.mPersonTitle = mPersonTitle;
    }

    public void setDate(@NonNull Date mDate) {
        this.mDate = mDate;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setInterviewTitle(@NonNull String mInterviewTitle) {
        this.mInterviewTitle = mInterviewTitle;
    }

    public void setInterview(String mInterview) {
        this.mInterview = mInterview;
    }

    public void setInterviewNotes(String mInterviewNotes) {
        this.mInterviewNotes = mInterviewNotes;
    }

    public String getInterviewNotes() {
        return mInterviewNotes;
    }

    public String getInterview() {
        return mInterview;
    }

    @NonNull
    public String getInterviewTitle() {
        return mInterviewTitle;
    }

    public String getLocation() {
        return mLocation;
    }

    @NonNull
    public Date getDate() {
        return mDate;
    }

    public String getPersonTitle() {
        return mPersonTitle;
    }

    @NonNull
    public String getLastName() {
        return mLastName;
    }

    @NonNull
    public String getFirstName() {
        return mFirstName;
    }

    public int getId() {
        return mId;
    }
}
