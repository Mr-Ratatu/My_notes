package com.motivation.first.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.DataBaseProject.NoteDao;
import com.motivation.first.myapplication.Model.Utils;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Utils>> allNotes; //используем LiveData

    public NoteRepository(Application application) {
        NoteAppDataBase noteAppDataBase = NoteAppDataBase.getInstance(application);
        noteDao = noteAppDataBase.getNoteDao();
        allNotes = noteDao.getAllNotes();
    }

    /**Создаем нужные нам методы*/
    public void insert(Utils utils) {
        new InsertAsyncTask(noteDao).execute(utils);
    }

    public void update(Utils utils) {
        new UpdateAsyncTask(noteDao).execute(utils);
    }

    public void delete(Utils utils) {
        new DeleteAsyncTask(noteDao).execute(utils);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Utils>> getAllNotes() {
        return allNotes;
    }


    /**Далее нужно создать для каждого метода по AsyncTask для выплнения кода
     * в разных потоках*/
    private static class InsertAsyncTask extends AsyncTask<Utils, Void, Void> {
        private NoteDao noteDao;

        private InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Utils... utils) {
            noteDao.addNote(utils[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Utils, Void, Void> {
        private NoteDao noteDao;

        private UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Utils... utils) {
            noteDao.updateNote(utils[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Utils, Void, Void> {
        private NoteDao noteDao;

        private DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Utils... utils) {
            noteDao.deleteNote(utils[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.getAllNotes();
            return null;
        }
    }
}
