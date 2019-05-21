package eg.com.iti.roomassignment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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

public class CharacterAdapter extends ListAdapter<Character, CharacterAdapter.CharacterHolder> {

    Context context;
    private OnItemClickListener listener;

    public CharacterAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Character> DIFF_CALLBACK = new DiffUtil.ItemCallback<Character>() {
        @Override
        public boolean areItemsTheSame(Character oldItem, Character newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Character oldItem, Character newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getImageUrl().equals(newItem.getImageUrl());
        }
    };

    @NonNull
    @Override
    public CharacterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item, parent, false);
        return new CharacterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterHolder holder, int position) {
        Character currentCharacter = getItem(position);
        holder.character_name.setText(currentCharacter.getName());
        Log.i("characterName", "" + currentCharacter.getName());
        Log.i("characterImageURL", "" + currentCharacter.getImageUrl());
        Picasso.get().load(currentCharacter.getImageUrl()).into(holder.character_imageView);
    }
    /*
    @Override
    public int getItemCount() {
        return characters.size();
    }
    public void setNotes(List<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }
    */
    public Character getCharacterAt(int position) {
        return getItem(position);
    }
    class CharacterHolder extends RecyclerView.ViewHolder {
        private TextView character_name;
        private ImageView character_imageView;

        public CharacterHolder(View itemView) {
            super(itemView);
            character_name = itemView.findViewById(R.id.character_name);
            character_imageView = itemView.findViewById(R.id.character_imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Character character);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
