package com.motivation.first.myapplication.DataBaseProject;

import android.content.Context;

import com.motivation.first.myapplication.Model.Utils;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract NoteDao getNoteDao();

}
