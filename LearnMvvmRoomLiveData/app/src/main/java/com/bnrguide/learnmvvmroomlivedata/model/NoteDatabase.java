package com.bnrguide.learnmvvmroomlivedata.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    LiveData<List<Note>> allNotes;

    public abstract NoteDao noteDao();

    private static NoteDatabase instance;


    public static synchronized NoteDatabase getInstance(Application application){
        if(instance == null){
            instance = Room.databaseBuilder(application,
                    NoteDatabase.class, "note_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(mCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback mCallback = new Callback() {
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // create action method
            new CreateDummieDatabase(instance).execute();
        }
    };

    private static class CreateDummieDatabase extends AsyncTask<Void, Void, Void>{
        private NoteDao mNoteDao;

        private CreateDummieDatabase(NoteDatabase database){
            mNoteDao = database.noteDao();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.insert(new Note("Tittle 1", "Description 1", 1));
            mNoteDao.insert(new Note("Tittle 2", "Description 2", 2));
            mNoteDao.insert(new Note("Tittle 3", "Description 3", 3));
            return null;
        }
    }
}
