package eg.com.iti.roomassignment.screens.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import eg.com.iti.roomassignment.room.Character;
import eg.com.iti.roomassignment.room.CharacterViewModel;
import eg.com.iti.roomassignment.R;
import eg.com.iti.roomassignment.adapter.CharacterAdapter;
import eg.com.iti.roomassignment.screens.addCharacter.AddCharacter;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST  = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private CharacterViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCharacter.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CharacterAdapter adapter = new CharacterAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        noteViewModel.getAllCharacters().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(@Nullable List<Character> notes) {
                adapter.setNotes(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                noteViewModel.delete(adapter.getCharacterAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Character deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CharacterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Character character) {
                Intent intent = new Intent(MainActivity.this , AddCharacter.class );
                intent.putExtra(AddCharacter.EXTRA_ID,character.getId());
                intent.putExtra(AddCharacter.EXTRA_NAME,character.getName());
                intent.putExtra(AddCharacter.EXTRA_IMAGEURL,character.getImageUrl());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            String name = data.getStringExtra(AddCharacter.EXTRA_NAME);
            String imageURL = data.getStringExtra(AddCharacter.EXTRA_IMAGEURL);

            Character character = new Character(name, imageURL);
            noteViewModel.insert(character);

            Toast.makeText(this, "Character saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddCharacter.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Character can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddCharacter.EXTRA_NAME);
            String imageURL = data.getStringExtra(AddCharacter.EXTRA_IMAGEURL);

            Character character = new Character(name, imageURL);
            character.setId(id);
            noteViewModel.update(character);

            Toast.makeText(this, "Character updated", Toast.LENGTH_SHORT).show();
        }
        else
            {
            Toast.makeText(this, "Character not saved", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_characters:
                noteViewModel.deleteAllCharacters();
                Toast.makeText(this, "All Characters deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}