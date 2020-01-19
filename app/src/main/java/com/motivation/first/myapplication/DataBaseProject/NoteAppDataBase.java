package com.motivation.first.myapplication.DataBaseProject;

import com.motivation.first.myapplication.Model.Utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Utils.class}, version = 1)
public abstract class NoteAppDataBase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

}
