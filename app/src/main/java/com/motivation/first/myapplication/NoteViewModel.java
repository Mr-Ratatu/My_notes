package com.motivation.first.myapplication;

import android.app.Application;

import com.motivation.first.myapplication.Model.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Utils>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Utils utils) {
        repository.insert(utils);
    }

    public void update(Utils utils) {
        repository.update(utils);
    }

    public void delete(Utils utils) {
        repository.delete(utils);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Utils>> getAllNotes() {
        return allNotes;
    }
}
