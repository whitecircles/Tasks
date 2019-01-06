package by.home.white.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<Note>> mAllWords;
    private List<Note> mAll;



    public NoteViewModel(Application application) throws ExecutionException, InterruptedException {
        super(application);
        mRepository = new NoteRepository(application);
        mAllWords = mRepository.getAllNotes();



    }

    LiveData<List<Note>> getAllNotes() { return mAllWords; }





    public void insert(Note note) { mRepository.insert(note); }

    public void delete(Note note) { mRepository.deleteNote(note); }

    public void update(Note note) { mRepository.updateNote(note); }




}

