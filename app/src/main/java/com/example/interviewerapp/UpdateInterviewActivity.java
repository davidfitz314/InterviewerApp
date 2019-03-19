package com.example.interviewerapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interviewerapp.interview_database.Person;

import java.util.Calendar;

public class UpdateInterviewActivity extends AppCompatActivity {
    InterviewPersonViewModel mViewModel;
    private int mIdIn;
    private Person mPerson;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mHonorific;
    private CalendarView mDate;
    private EditText mLocation;
    private EditText mTitle;
    private EditText mInterview;
    private EditText mNotes;
    public static final String PAGE_LOCATION_STRING = "page_location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_interview);

        mFirstName  = findViewById(R.id.edit_interview_first_name_edit_textview);
        mLastName   = findViewById(R.id.edit_interview_last_name_edit_textview);
        mHonorific  = findViewById(R.id.edit_interview_honorific_title_textview);
        mDate       = (CalendarView) findViewById(R.id.updateInterviewDatePickerWidget);
        mLocation   = findViewById(R.id.edit_interview_locationEditText);
        mTitle      = findViewById(R.id.edit_interview_title_edit_textview);
        mInterview  = findViewById(R.id.edit_interview_interviewEditText);
        mNotes      = findViewById(R.id.edit_interview_interviewNotesEditText);

        mIdIn = getIntent().getIntExtra(InterviewGalleryActivity.PlaceholderFragment.mIdString, -1);
        mViewModel = ViewModelProviders.of(this).get(InterviewPersonViewModel.class);

        if (mIdIn != -1){
            mViewModel.getPersonById(mIdIn).observe(this, new Observer<Person>() {
                @Override
                public void onChanged(@Nullable Person person) {
                    mPerson = person;
                    mFirstName.setText(mPerson.getFirstName());
                    mLastName.setText(mPerson.getLastName());
                    mHonorific.setText(mPerson.getPersonTitle());
                    //mDate.setText(mPerson.getDate());
                    mLocation.setText(mPerson.getLocation());
                    mTitle.setText(mPerson.getInterviewTitle());
                    mInterview.setText(mPerson.getInterview());
                    mNotes.setText(mPerson.getInterviewNotes());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cancel_interview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save_interview:
                if (mIdIn != -1){
                    Intent returnIntent = getIntent();
                    returnIntent.putExtra(PAGE_LOCATION_STRING, mPerson.getId());
                    setResult(RESULT_OK, returnIntent);
                    mPerson.setInterviewTitle(mTitle.getText().toString());
                    mPerson.setInterview(mInterview.getText().toString());
                    mPerson.setInterviewNotes(mNotes.getText().toString());
                    mPerson.setFirstName(mFirstName.getText().toString());
                    mPerson.setLastName(mLastName.getText().toString());
                    mPerson.setLocation(mLocation.getText().toString());
                    mPerson.setPersonTitle(mHonorific.getText().toString());
                    mViewModel.update(mPerson);
                }
                Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_interview:
                Intent returnIntent = getIntent();
                returnIntent.putExtra(PAGE_LOCATION_STRING, mPerson.getId());
                setResult(RESULT_OK, returnIntent);
                finish();
                Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
