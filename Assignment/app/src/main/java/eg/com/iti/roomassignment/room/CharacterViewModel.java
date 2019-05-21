package eg.com.iti.roomassignment.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {
    private CharacterRepository repository;
    private LiveData<List<Character>> allNotes;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        repository = new CharacterRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Character character)
    {
        repository.insert(character);
    }

    public void update(Character character)
    {
        repository.update(character);
    }

    public void delete(Character character)
    {
        repository.delete(character);
    }

    public void deleteAllCharacters()
    {
        repository.deleteAllNotes();
    }

    public LiveData<List<Character>> getAllCharacters()
    {
        return allNotes;
    }
}