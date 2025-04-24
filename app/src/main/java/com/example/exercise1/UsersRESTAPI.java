package com.example.exercise1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersRESTAPI {
    @GET("users")
    Call<Users> getUsers();
}
