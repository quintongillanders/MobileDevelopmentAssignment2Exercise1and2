package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddGameActivity extends AppCompatActivity {

    private EditText titleGame, platform, genre, year;
    private Button addgameButton, backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game_activity);

        titleGame = findViewById(R.id.titleGame);
        platform = findViewById(R.id.platform);
        genre = findViewById(R.id.genre);
        year = findViewById(R.id.year);
        addgameButton = findViewById(R.id.addgameButton);
        backButton = findViewById(R.id.backButton);

        db = FirebaseFirestore.getInstance();

        addgameButton.setOnClickListener(v -> {
            String title = titleGame.getText().toString().trim();
            String gamePlatform = platform.getText().toString().trim();
            String gameGenre = genre.getText().toString().trim();
            String releaseYear = year.getText().toString().trim();

            if (title.isEmpty() || gamePlatform.isEmpty() || gameGenre.isEmpty() || releaseYear.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields and try again", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> game = new HashMap<>();
            game.put("title", title);
            game.put("platform", gamePlatform);
            game.put("genre", gameGenre);
            game.put("year", releaseYear);

            db.collection("games")
                    .add(game)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Game added successfully", Toast.LENGTH_SHORT).show();
                        titleGame.setText("");
                        platform.setText("");
                        genre.setText("");
                        year.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to add" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(AddGameActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }






