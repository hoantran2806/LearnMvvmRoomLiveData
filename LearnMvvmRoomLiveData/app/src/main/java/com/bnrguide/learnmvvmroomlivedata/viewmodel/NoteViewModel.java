package com.bnrguide.learnmvvmroomlivedata.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bnrguide.learnmvvmroomlivedata.model.Note;
import com.bnrguide.learnmvvmroomlivedata.model.Repository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public void insertNote(Note note){
        mRepository.insert(note);
    }

    public void updateNote(Note note){
        mRepository.update(note);
    }

    public void deleteNote(Note note){
        mRepository.delete(note);
    }

    public void deleteAllNotes(){
        mRepository.deleteAllNote();
    }

    public LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }
}
