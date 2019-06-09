package com.ivan.garcia.roomwordsample.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.garcia.roomwordsample.R;
import com.ivan.garcia.roomwordsample.Room.Word;

import java.util.List;
import java.util.zip.Inflater;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WorldViewHolder> {

    private List<Word> words;
    private LayoutInflater inflater;

    public WordListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setWords(List<Word> _words) {
        words = _words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WorldViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldViewHolder holder, int position) {
        if (words != null) {
            holder.worldItemView.setText(words.get(position).getId() + " - " + words.get(position).getWord());
        } else {
            holder.worldItemView.setText("No word");
        }
    }

    @Override
    public int getItemCount() {
        if (words != null)
            return words.size();
        else
            return 0;
    }

    class WorldViewHolder extends RecyclerView.ViewHolder {

        private final TextView worldItemView;

        public WorldViewHolder(@NonNull View itemView) {
            super(itemView);
            worldItemView = itemView.findViewById(R.id.textView);
        }
    }
}
