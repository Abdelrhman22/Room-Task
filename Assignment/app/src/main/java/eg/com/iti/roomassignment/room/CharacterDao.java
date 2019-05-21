package eg.com.iti.roomassignment.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CharacterDao {
    @Insert
    void insert(Character character);

    @Update
    void update(Character character);

    @Delete
    void delete(Character character);

    @Query("DELETE FROM character_table")
    void deleteAllCharacters();

    @Query("SELECT * FROM character_table ORDER BY name ASC")
    LiveData<List<Character>> getAllCharacters();
}
