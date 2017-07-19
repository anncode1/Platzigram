package com.platzi.platzigram.api.services;



import com.google.gson.JsonObject;
import com.platzi.platzigram.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by anahisalgado on 13/07/17.
 */

public interface PlatzigramFirebaseService {

    @POST("Users.json")
    Call<JsonObject> createUser(@Body User user);

}
