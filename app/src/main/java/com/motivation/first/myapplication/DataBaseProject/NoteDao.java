package com.motivation.first.myapplication.DataBaseProject;

import com.motivation.first.myapplication.Model.Utils;

import java.util.List;

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
    List<Utils> getAllNotes();

    @Query("select * from note where note_id ==:noteId")
    Utils getNote(long noteId);
}
