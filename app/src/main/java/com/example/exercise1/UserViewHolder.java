package com.example.exercise1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class UserViewHolder extends RecyclerView.ViewHolder {
    ImageView avatar;
    TextView username, userid;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        avatar = itemView.findViewById(R.id.avatar);
        username = itemView.findViewById(R.id.username);
        userid = itemView.findViewById(R.id.userid);

    }

    public void bind(User user) {
        username.setText(user.getFirst_name());
        userid.setText(user.getId());
    }
}
