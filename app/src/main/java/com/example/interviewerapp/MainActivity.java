package com.example.interviewerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mNewInterview;
    private Button mInterviewGallery;
    private Button mExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the frame layouts
        mNewInterview       = findViewById(R.id.new_interview);
        mInterviewGallery   = findViewById(R.id.interview_gallery);
        mExtras             = findViewById(R.id.extras_frame);

        //set up on click listener for new interview frame layout
        mNewInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addInterviewIntent = new Intent(MainActivity.this, AddInterviewActivity.class);
                startActivity(addInterviewIntent);
                Toast.makeText(MainActivity.this, "New Interview Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //set up on click listener for interview gallery frame layout
        mInterviewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(MainActivity.this, InterviewGalleryActivity.class);
                startActivity(galleryIntent);
                Toast.makeText(MainActivity.this, "Open Gallery Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //set up on click listener for extras frame layout
        mExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Extras Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        // TODO (4) create new activities to be launched with each button pressed, layouts can use fragments.
    }
}
