package com.bnrguide.learnmvvmroomlivedata.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.os.AsyncTask;

import java.util.List;

public class Repository {
    private NoteDao mNoteDao;

    private LiveData<List<Note>> allNotes;

    public Repository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        mNoteDao = database.noteDao();
        allNotes = mNoteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNote(mNoteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNote(mNoteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNote(mNoteDao).execute(note);
    }

    public void deleteAllNote() {
        new DeleteAllNotes(mNoteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNote extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        private InsertNote(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param notes The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNote extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        private UpdateNote(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNote extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        private DeleteNote(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotes extends AsyncTask<Void, Void, Void> {
        private NoteDao mNoteDao;

        private DeleteAllNotes(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.deleteAllNotes();
            return null;
        }
    }
}
