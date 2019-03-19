package com.example.interviewerapp.interview_database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

@Database(entities = {Person.class}, version = 5, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class InterviewPersonDatabase extends RoomDatabase {

    public abstract InterviewDao interviewDao();

    private static InterviewPersonDatabase INSTANCE;

    public static InterviewPersonDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (InterviewPersonDatabase.class) {
                if (INSTANCE == null) {
                    //create the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), InterviewPersonDatabase.class, "interview_database")
                            //wipes and rebuilds instead of migration
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final InterviewDao mDao;

        PopulateDbAsync(InterviewPersonDatabase db){
            mDao = db.interviewDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mDao.getAnyInterviewee().length < 1){
                Date currentTime = Calendar.getInstance().getTime();
                Person person = new Person("Jane", "Doe", "Mrs.", currentTime, "555 S. Main St., Wonderland, NZ", "Example: About Wood and WoodChucks", "Lorem ipsum dolar exposium, Fermendum to frie do sat une heide.", "Additional Notes");
                mDao.insert(person);

            }
            return null;
        }
    }
}
