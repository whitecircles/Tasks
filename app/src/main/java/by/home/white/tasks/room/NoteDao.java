package by.home.white.tasks.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import by.home.white.tasks.entities.Note;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * from note_table ORDER BY pendDate ASC")
    LiveData<List<Note>> getAllNotes();

    @Delete
    void deleteNote(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * from note_table")
    List<Note> getAll();


}
