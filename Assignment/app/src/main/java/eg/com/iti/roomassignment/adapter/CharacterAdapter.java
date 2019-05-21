package eg.com.iti.roomassignment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import eg.com.iti.roomassignment.room.Character;
import eg.com.iti.roomassignment.R;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterHolder> {
    private List<Character> characters = new ArrayList<>();
    Context context;

    public CharacterAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item, parent, false);
        return new CharacterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterHolder holder, int position) {
        Character currentCharacter = characters.get(position);
        holder.character_name.setText(currentCharacter.getName());
        Log.i("characterName", "" + currentCharacter.getName());
        Log.i("characterImageURL", "" + currentCharacter.getImageUrl());
        Picasso.get().load(currentCharacter.getImageUrl()).into(holder.character_imageView);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void setNotes(List<Character> notes) {
        this.characters = notes;
        notifyDataSetChanged();
    }
    public Character getCharacterAt(int position) {
        return characters.get(position);
    }
    class CharacterHolder extends RecyclerView.ViewHolder {
        private TextView character_name;
        private ImageView character_imageView;

        public CharacterHolder(View itemView) {
            super(itemView);
            character_name = itemView.findViewById(R.id.character_name);
            character_imageView = itemView.findViewById(R.id.character_imageView);

        }
    }
}
