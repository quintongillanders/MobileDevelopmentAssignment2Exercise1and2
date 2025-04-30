package com.example.exercise1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UsersRESTAPI {
    @GET("users")
    Call<Users> getUsers(@Header("x-api-key") String apiKey);
}
