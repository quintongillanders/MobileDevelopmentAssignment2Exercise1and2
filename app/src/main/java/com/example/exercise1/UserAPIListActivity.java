package com.example.exercise1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class UserAPIListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> users = new ArrayList<>();
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userapi_list_activity);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.backButton);

        users = (List<User>) getIntent().getSerializableExtra("users");

        userAdapter = new UserAdapter(users);
        recyclerView.setAdapter(userAdapter);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(UserAPIListActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

