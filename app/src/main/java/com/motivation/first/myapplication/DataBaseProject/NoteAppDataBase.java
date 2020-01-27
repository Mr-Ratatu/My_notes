package com.motivation.first.myapplication.DataBaseProject;

import android.content.Context;
import android.os.AsyncTask;

import com.motivation.first.myapplication.Model.Utils;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Utils.class}, version = 1, exportSchema = false)
public abstract class NoteAppDataBase extends RoomDatabase {

    private static final String DB_NAME = "NotesDb";
    private static NoteAppDataBase INSTANCE;

    public static NoteAppDataBase getInstance(final Context context) {

        if (INSTANCE == null) {
            synchronized (NoteAppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteAppDataBase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract NoteDao getNoteDao();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteAppDataBase db) {
            noteDao = db.getNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.addNote(new Utils("Title 1", "Description 1"));
            noteDao.addNote(new Utils("Title 2", "Description 2"));
            noteDao.addNote(new Utils("Title 3", "Description 3"));
            return null;
        }
    }

}
