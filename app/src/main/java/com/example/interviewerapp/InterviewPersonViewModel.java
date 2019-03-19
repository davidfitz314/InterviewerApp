package com.example.interviewerapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.interviewerapp.interview_database.InterviewRepository;
import com.example.interviewerapp.interview_database.Person;

import java.util.List;

public class InterviewPersonViewModel extends AndroidViewModel {
    private InterviewRepository mRepository;
    private LiveData<List<Person>> mAllInterviews;
    private LiveData<Person> mPerson;

    public InterviewPersonViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InterviewRepository(application);
        mAllInterviews = mRepository.getAllPersons();
        mPerson = mRepository.getPerson();
    }

    LiveData<List<Person>> getAllInterviews(){
        return mAllInterviews;
    }

    LiveData<Person> getPerson(){
        return mPerson;
    }

    LiveData<Person> getPersonById(int id){
        LiveData<Person> personLiveData = mRepository.getPersonById(id);
        return  personLiveData;
    }

    public void insert(Person person){
        mRepository.insert(person);
    }

    public void delete(Person person){
        mRepository.delete(person);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void update(Person person){
        mRepository.update(person);
    }
}
