package com.example.interviewerapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.interviewerapp.interview_database.Person;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddInterviewActivity extends AppCompatActivity {
    InterviewPersonViewModel addModel;
    private EditText mTitleEdit;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;
    private EditText mHonorificsEdit;
    private CalendarView mDateEdit;
    private EditText mLocationEdit;
    private EditText mInterviewEdit;
    private EditText mNotesEdit;
    private Person mAddPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview);
        //initialize edit texts
        mTitleEdit = findViewById(R.id.add_interview_title_edit_textview);
        mFirstNameEdit = findViewById(R.id.add_interview_first_name_edit_textview);
        mLastNameEdit = findViewById(R.id.add_interview_last_name_edit_textview);
        mHonorificsEdit = findViewById(R.id.add_interview_honorific_title_textview);
        mDateEdit = findViewById(R.id.addInterviewDatePickerWidget);


        mLocationEdit = findViewById(R.id.add_interview_locationEditText);
        mInterviewEdit = findViewById(R.id.add_interview_interviewEditText);
        mNotesEdit = findViewById(R.id.add_interview_interviewNotesEditText);

        addModel = ViewModelProviders.of(this).get(InterviewPersonViewModel.class);

        mAddPerson = new Person();
        addModel.insert(mAddPerson);
        addModel.getPerson().observe(this, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person person) {
                mAddPerson = person;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cancel_interview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save_interview:
                //save the interview
                if (mTitleEdit.getText().toString().equals("") || mTitleEdit == null){
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (mFirstNameEdit.getText().toString().equals("") || mFirstNameEdit == null){
                    Toast.makeText(this, "First Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (mLastNameEdit.getText().toString().equals("") || mLastNameEdit == null){
                    Toast.makeText(this, "Last Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (mDateEdit == null){
                    Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String firstName = mFirstNameEdit.getText().toString();
                    String lastName = mLastNameEdit.getText().toString();
                    String honorifics = mHonorificsEdit.getText().toString();

                    /*String iDate = mDateEdit.getText().toString();
                    iDate += " " + mTimeEdit.getText().toString();*/
                    String location = mLocationEdit.getText().toString();
                    String title = mTitleEdit.getText().toString();
                    String interview = mInterviewEdit.getText().toString();
                    String notes = mNotesEdit.getText().toString();

                    //add the applied changes to person.
                    mAddPerson.setFirstName(firstName);
                    mAddPerson.setLastName(lastName);
                    mAddPerson.setPersonTitle(honorifics);
                    //mAddPerson.setDate(iDate);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String selectedDate = sdf.format(new Date(mDateEdit.getDate()));
                    Log.d("DATETEST", selectedDate);
                    mAddPerson.setLocation(location);
                    mAddPerson.setInterviewTitle(title);
                    mAddPerson.setInterview(interview);
                    mAddPerson.setInterviewNotes(notes);
                    try {
                        addModel.update(mAddPerson);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.close_interview:
                //close the add interview and return to main menu.
                if (mTitleEdit.getText().toString().equals("") || mTitleEdit == null){
                    addModel.delete(mAddPerson);
                } else if (mFirstNameEdit.getText().toString().equals("") || mFirstNameEdit == null){
                    addModel.delete(mAddPerson);
                } else if (mLastNameEdit.getText().toString().equals("") || mLastNameEdit == null){
                    addModel.delete(mAddPerson);
                }/* else if (mDateEdit.getText().toString().equals("") || mDateEdit == null){
                    addModel.delete(mAddPerson);
                }*/
                finish();
                Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
