package eg.com.iti.roomassignment.screens.addCharacter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import eg.com.iti.roomassignment.R;

public class AddCharacter extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "eg.com.iti.roomarchitecture.EXTRA_TITLE";
    public static final String EXTRA_IMAGEURL =
            "eg.com.iti.roomarchitecture.EXTRA_DESCRIPTION";

    private EditText editTextName;
    private EditText editTextImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);

        editTextName = findViewById(R.id.edit_text_name);
        editTextImageURL = findViewById(R.id.edit_text_imageUrl);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }

    private void saveNote() {
        String name = editTextName.getText().toString();
        String imageUrl = editTextImageURL.getText().toString();

        if (name.trim().isEmpty() || imageUrl.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and image Url", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_IMAGEURL, imageUrl);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}