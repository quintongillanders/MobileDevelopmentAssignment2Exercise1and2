package com.example.exercise1;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class UserController implements Callback<Users> {
    final String BASE_URL="https://reqres.in/api/";
    private UserFetchCallback callback;
    private Context context;

    public UserController(Context context, UserFetchCallback callback) {
        this.context = context;
        this.callback = callback;
    }
    public Users users;
    public void start(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsersRESTAPI usersRESTAPI = retrofit.create(UsersRESTAPI.class);
        Call<Users> call  = usersRESTAPI.getUsers("reqres-free-v1");
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<Users> call, Response<Users> response) {
        if(response.isSuccessful()){
            users = response.body();
            Log.d("USER_API","Getting user data successful");
            Toast.makeText(context, "API Call Success!", Toast.LENGTH_SHORT).show();
            if(users.data !=null){
                Log.d("USER_API", "Users:"+ users.data.toString());
                if (callback != null) {
                    callback.onUsersFetched(users.data);
                }

            } else{
                Log.d("USER_API","List is empty");
            }
        }else{
            Log.d("USER_API","Error getting users");
            Toast.makeText(context, "API Call Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Users> call, Throwable t) {
        Log.d("USER_API","Error getting users");
        if (callback != null) {
            callback.onError();
        }
    }

    public interface UserFetchCallback {
        void onUsersFetched(List<User> users);
        void onError();
    }
}


