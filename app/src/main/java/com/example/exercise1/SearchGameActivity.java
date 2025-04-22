package com.example.exercise1;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;


public class SearchGameActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private GameAdapter gameAdapter;
    private List<Game> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_game_activity);


        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        backButton = findViewById(R.id.backButton);

        gameList = new ArrayList<>();
        gameAdapter = new GameAdapter(this, gameList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(gameAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                            gameList.clear();
                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                Game game = doc.toObject(Game.class);
                                gameList.add(game);
                            }
                            gameAdapter.notifyDataSetChanged();
                            Log.d("SearchGameActivity", "Games loaded: " + gameList.size()); // debug log

                            Toast.makeText(SearchGameActivity.this, "Total games in database: " + gameList.size(), Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(SearchGameActivity.this, "failed to find games", Toast.LENGTH_SHORT).show();
                        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                boolean found = false;

                for (Game game : gameList) {
                    if (game.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    Toast.makeText(SearchGameActivity.this, "Search Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchGameActivity.this, "Game not found!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gameAdapter.filter(newText);
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(SearchGameActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




