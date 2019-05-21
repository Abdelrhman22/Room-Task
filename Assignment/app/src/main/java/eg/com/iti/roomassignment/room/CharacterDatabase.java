package eg.com.iti.roomassignment.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Character.class}, version = 1)
public abstract class CharacterDatabase extends RoomDatabase {

    private static CharacterDatabase instance;

    public abstract CharacterDao noteDao();

    public static synchronized CharacterDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CharacterDatabase.class, "character_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CharacterDao noteDao;

        private PopulateDbAsyncTask(CharacterDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
             noteDao.insert(new Character("Avengers", "https://sketchok.com/images/articles/02-comics/002-superheroes/03-avengers/45.jpg"));
             noteDao.insert(new Character("Meelo", "https://sketchok.com/images/articles/01-cartoons/027-avatar/36/11.jpg"));
             noteDao.insert(new Character("Momo", "https://sketchok.com/images/articles/01-cartoons/027-avatar/37/12.jpg"));
            return null;
        }
    }
}