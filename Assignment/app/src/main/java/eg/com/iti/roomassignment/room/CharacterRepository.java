package eg.com.iti.roomassignment.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CharacterRepository {
    private CharacterDao characterDao;
    private LiveData<List<Character>> allNotes;

    public CharacterRepository(Application application) {
        CharacterDatabase database = CharacterDatabase.getInstance(application);
        characterDao = database.noteDao();
        allNotes = characterDao.getAllCharacters();
    }

    public void insert(Character character) {
        new InsertNoteAsyncTask(characterDao).execute(character);
    }

    public void update(Character character) {
        new UpdateNoteAsyncTask(characterDao).execute(character);
    }

    public void delete(Character character) {
        new DeleteNoteAsyncTask(characterDao).execute(character);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(characterDao).execute();
    }

    public LiveData<List<Character>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Character, Void, Void> {
        private CharacterDao noteDao;

        private InsertNoteAsyncTask(CharacterDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Character... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Character, Void, Void> {
        private CharacterDao noteDao;

        private UpdateNoteAsyncTask(CharacterDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Character... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Character, Void, Void> {
        private CharacterDao noteDao;

        private DeleteNoteAsyncTask(CharacterDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Character... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CharacterDao noteDao;

        private DeleteAllNotesAsyncTask(CharacterDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllCharacters();
            return null;
        }
    }
}
