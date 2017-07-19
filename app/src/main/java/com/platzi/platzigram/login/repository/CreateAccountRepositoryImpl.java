package com.platzi.platzigram.login.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.platzi.platzigram.api.adapter.PlatzigramFirebaseAdapter;
import com.platzi.platzigram.api.services.PlatzigramFirebaseService;
import com.platzi.platzigram.login.presenter.CreateAccountPresenter;
import com.platzi.platzigram.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anahisalgado on 13/07/17.
 */

public class CreateAccountRepositoryImpl implements CreateAccountRepository {

    private static final String TAG = "CreateAccountRepository";
    private CreateAccountPresenter presenter;

    public CreateAccountRepositoryImpl(CreateAccountPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void createAccount(User user, Context context) {
        PlatzigramFirebaseAdapter platzigramFirebaseAdapter = new PlatzigramFirebaseAdapter();
        PlatzigramFirebaseService platzigramFirebaseService = platzigramFirebaseAdapter.getFirebaseService(context);
        Call<JsonObject> call = platzigramFirebaseService.createUser(user);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.w(TAG, "RESPONSE ~ " +  response);
                Log.w(TAG, "RESPONSE ~ " +  response.body());
                presenter.createAccountSuccess();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                presenter.createAccountError(t.toString());
                Log.e(TAG, t.toString());
                t.printStackTrace();
            }
        });
    }
}
