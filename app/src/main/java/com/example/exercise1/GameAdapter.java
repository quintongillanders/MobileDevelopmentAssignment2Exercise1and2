package com.example.exercise1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> games;
    private List<Game> gameListFull;

    public GameAdapter(SearchGameActivity searchGameActivity, List<Game> games) {
        this.games = new ArrayList<>(games);
        this.gameListFull = new ArrayList<>(games);

    }
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
    Game game = games.get(position);
    Log.d("GameAdapter", "Binding:" + game.getTitle()); // debug log
    holder.gameTitle.setText(game.getTitle());
    holder.platform.setText("Platform: " + game.getPlatform());
    holder.genre.setText("genre: " + game.getGenre());
    holder.year.setText("year: " + game.getYear());

    }

    @Override public int getItemCount() {
        return games.size();
    }
    public void filter(String text) {
        games.clear();

        if(text.isEmpty()) {
            games.addAll(gameListFull);
        } else {
            text = text.toLowerCase();
            for (Game game : gameListFull) {
                if (game.getTitle().toLowerCase().contains(text)) {
                    games.add(game);
                }
            }

        }
    notifyDataSetChanged();
}

    public static class GameViewHolder extends RecyclerView.ViewHolder {
    TextView gameTitle, platform, genre, year;

    GameViewHolder(View itemView) {
        super(itemView);
        gameTitle = itemView.findViewById(R.id.gameTitle);
        platform = itemView.findViewById(R.id.platform);
        genre = itemView.findViewById(R.id.genre);
        year = itemView.findViewById(R.id.year);
        }
    }
}


