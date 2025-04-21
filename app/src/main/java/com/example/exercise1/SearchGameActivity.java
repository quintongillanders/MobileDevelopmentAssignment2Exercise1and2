package com.example.exercise1;

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

        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameList = new ArrayList<>();
        gameAdapter = new GameAdapter(gameList);
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
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(SearchGameActivity.this, "failed to find games", Toast.LENGTH_SHORT).show();
                        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
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




