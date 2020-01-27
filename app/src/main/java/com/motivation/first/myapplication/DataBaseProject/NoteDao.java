package com.motivation.first.myapplication.DataBaseProject;

import com.motivation.first.myapplication.Model.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    long addNote(Utils utils);

    @Update
    void updateNote(Utils utils);

    @Delete
    void deleteNote(Utils utils);

    @Query("select * from note")
    LiveData<List<Utils>> getAllNotes();

    @Query("delete from note")
    void deleteAllNotes();
}
