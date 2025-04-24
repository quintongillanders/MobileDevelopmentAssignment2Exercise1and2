package com.example.exercise1;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class UserController implements Callback<Users> {
    final String BASE_URL="https://reqres.in/api/";
    public Users users;
    public void start(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsersRESTAPI usersRESTAPI = retrofit.create(UsersRESTAPI.class);
        Call<Users> call  = usersRESTAPI.getUsers();
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<Users> call, Response<Users> response) {
        if(response.isSuccessful()){
            users = response.body();
            Log.d("USER_API","Getting user data successful " );
            if(users.data !=null){
                Log.d("USER_API", "Users:"+ users.data.toString());

            } else{
                Log.d("USER_API","Error User's list is empty");
            }
        }else{
            Log.d("USER_API","Error getting users");
        }
    }

    @Override
    public void onFailure(Call<Users> call, Throwable t) {
        Log.d("USER_API","Error getting users");
    }
}


