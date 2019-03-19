package com.example.interviewerapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.interviewerapp.interview_database.Person;

import java.util.List;

public class InterviewGalleryActivity extends AppCompatActivity {
    private InterviewPersonViewModel mViewModel;
    private static final int INTENT_REQUEST_CODE = 1;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //connect to the database view model
        mViewModel = ViewModelProviders.of(this).get(InterviewPersonViewModel.class);
        mViewModel.getAllInterviews().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> people) {
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mSectionsPagerAdapter.setPeople(people);
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
        });


        //hide bottom bar of app
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    //sets the activity to full screen mode
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    //TODO update so it populates data immediately after recieving the intent results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mViewModel.getAllInterviews().observe(this, new Observer<List<Person>>() {
                @Override
                public void onChanged(@Nullable List<Person> people) {
                    mSectionsPagerAdapter.setPeople(people);
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(data.getIntExtra(UpdateInterviewActivity.PAGE_LOCATION_STRING, 1));
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interview_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        protected static final String mIdString = "ID_NUMBER";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_TITLE = "section_title";
        private static final String ARG_SECTION_NAME = "section_name";
        private static final String ARG_SECTION_LASTNAME = "section_lastname";
        private static final String ARG_SECTION_INTERVIEW = "section_interview";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Person person) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, person.getId());
            args.putString(ARG_SECTION_TITLE, person.getInterviewTitle());
            args.putString(ARG_SECTION_NAME, person.getFirstName());
            args.putString(ARG_SECTION_LASTNAME, person.getLastName());
            args.putString(ARG_SECTION_INTERVIEW, person.getInterview());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_interview_gallery, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            //sets the title of the interview and underlines it.
            TextView titleView = (TextView) rootView.findViewById(R.id.interview_title_textview);
            String title_string = getArguments().getString(ARG_SECTION_TITLE);
            SpannableString spanTitle = new SpannableString(title_string);
            spanTitle.setSpan(new UnderlineSpan(), 0, spanTitle.length(), 0);
            titleView.setText(spanTitle);

            TextView firstNameView = (TextView) rootView.findViewById(R.id.interview_first_name_textview);
            firstNameView.setText(getString(R.string.section_format_title, getArguments().getString(ARG_SECTION_NAME)));

            TextView lastNameView = (TextView) rootView.findViewById(R.id.interview_last_name_textview);
            lastNameView.setText(getString(R.string.section_format_title, getArguments().getString(ARG_SECTION_LASTNAME)));

            TextView interviewTextView = (TextView) rootView.findViewById(R.id.galleryInterviewText);
            interviewTextView.setText(getString(R.string.section_format_title, getArguments().getString(ARG_SECTION_INTERVIEW))+"...");

            CardView fragCard = (CardView) rootView.findViewById(R.id.fragment_card_view);
            fragCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateIntent = new Intent(getContext(), UpdateInterviewActivity.class);
                    updateIntent.putExtra(mIdString, getArguments().getInt(ARG_SECTION_NUMBER));
                    startActivityForResult(updateIntent, INTENT_REQUEST_CODE);
                    Toast.makeText(getContext(), "Update!", Toast.LENGTH_SHORT).show();
                }
            });
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<Person> mPeople;
        private int mPosition;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (mPeople != null){
                Person currentPerson = mPeople.get(position);
                mPosition = position;
                return PlaceholderFragment.newInstance(currentPerson);
            } else {
                //return PlaceholderFragment.newInstance(position + 1, getString(R.string.no_people_found_error_message));
            }
            return null;
        }

        public void setPeople(List<Person> personList){
            mPeople = personList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (mPeople != null){
                return mPeople.size();
            }
            return 0;
        }

    }
}
