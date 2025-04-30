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

        fetchUsersFromAPI();

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

    private void fetchUsersFromAPI() {
        UserController userController = new UserController(UserAPIListActivity.this, new UserController.UserFetchCallback() {
            @Override
            public void onUsersFetched(List<User> usersFromAPI) {
                users.clear();
                users.addAll(usersFromAPI);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Toast.makeText(UserAPIListActivity.this, "Error fetching users from API", Toast.LENGTH_SHORT).show();
            }
        });
        userController.start();
    }

}


