package com.example.exercise1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;


public class UpdateGameActivity extends AppCompatActivity {

    private Spinner gameList;
    private List<String> gameTitles = new ArrayList<>();
    private Map<String, String> gameIdMap = new HashMap<>();
    private EditText titleGame, platform, genre, year;
    private Button updateGameButton, backButton;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_game_activity);

        gameList = findViewById(R.id.gameList);

        titleGame = findViewById(R.id.titleGame);
        platform = findViewById(R.id.platform);
        genre = findViewById(R.id.genre);
        year = findViewById(R.id.year);
        updateGameButton = findViewById(R.id.updategameButton);
        backButton = findViewById(R.id.backButton);

        db = FirebaseFirestore.getInstance();

        db.collection("games").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (var doc : queryDocumentSnapshots) {
                String id = doc.getId();
                String title = doc.getString("title");

                if (title != null) {
                    gameTitles.add(title);
                    gameIdMap.put(title, id);
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gameTitles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gameList.setAdapter(adapter);
        });

        gameList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = gameTitles.get(position);
                String docID = gameIdMap.get(selectedTitle);

                if (docID != null) {
                    db.collection("games").document(docID).get().addOnSuccessListener(documentSnapshot -> {
                        titleGame.setText(documentSnapshot.getString("title"));
                        platform.setText(documentSnapshot.getString("platform"));
                        genre.setText(documentSnapshot.getString("genre"));
                        year.setText(documentSnapshot.getString("year"));
                    });
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        updateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object selectedItem = gameList.getSelectedItem();
                if(selectedItem != null) {
                String selectedTitle = gameList.getSelectedItem().toString();
                String docID = gameIdMap.get(selectedTitle);

                if (docID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("title", titleGame.getText().toString().trim());
                    updatedData.put("platform", platform.getText().toString().trim());
                    updatedData.put("genre", genre.getText().toString().trim());
                    updatedData.put("year", year.getText().toString().trim());

                    db.collection("games").document(docID)
                            .update(updatedData)
                            .addOnSuccessListener(aVoid -> Toast.makeText(UpdateGameActivity.this, "Game updated successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(UpdateGameActivity.this, "Failed to update game", Toast.LENGTH_SHORT).show());
                }
                } else {
                    Toast.makeText(UpdateGameActivity.this, "Please select a title first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(UpdateGameActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }












