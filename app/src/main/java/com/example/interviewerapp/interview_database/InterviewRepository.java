package com.example.interviewerapp.interview_database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class InterviewRepository {
    private InterviewDao mDao;
    private LiveData<List<Person>> mAllPersons;
    private LiveData<Person> mPerson;

    public InterviewRepository(Application application){
        InterviewPersonDatabase db = InterviewPersonDatabase.getDatabase(application);
        mDao = db.interviewDao();
        mAllPersons = mDao.getAllInterviews();
        mPerson = mDao.getAnyPerson();

    }

    //get all interviews from database
    public LiveData<List<Person>> getAllPersons(){
        return mAllPersons;
    }

    //get last 1 interview from database
    public LiveData<Person> getPerson() {
        return mPerson;
    }

    //get 1 person by id
    public LiveData<Person> getPersonById(int id) {
        LiveData<Person> mPersonById = mDao.getPersonById(id);
        return mPersonById;
    }

    //call the asynctasks for insert, delete/deleteAll, and edit
    public void insert(Person person){
        new InterviewInsertAsync(mDao).execute(person);
    }

    public void deleteAll(){
        new InterviewDeleteAllAsync(mDao).execute();
    }

    public void delete(Person person){
        new InterviewDeleteSingleAsync(mDao).execute(person);
    }

    public void update(Person person){
        new InterviewEditAsync(mDao).execute(person);
    }

    //private static classes use async to insert, delete/deleteAll, and edit
    private static class InterviewInsertAsync extends AsyncTask<Person, Void, Void>{
        private InterviewDao mAsyncTaskDao;
        InterviewInsertAsync(InterviewDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            mAsyncTaskDao.insert(people[0]);
            return null;
        }
    }

    private static class InterviewDeleteAllAsync extends AsyncTask<Void, Void, Void>{
        private InterviewDao mAsyncTaskDao;
        InterviewDeleteAllAsync(InterviewDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class InterviewDeleteSingleAsync extends AsyncTask<Person, Void, Void>{
        private InterviewDao mAsyncTaskDao;
        InterviewDeleteSingleAsync(InterviewDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            mAsyncTaskDao.deleteInterview(people[0]);
            return null;
        }
    }

    private static class InterviewEditAsync extends AsyncTask<Person, Void, Void>{
        private InterviewDao mAsyncTaskDao;
        InterviewEditAsync(InterviewDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            mAsyncTaskDao.updateInterview(people[0]);
            return null;
        }
    }
}
