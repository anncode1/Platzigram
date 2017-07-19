package com.platzi.platzigram.login.presenter;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.platzi.platzigram.model.User;

/**
 * Created by anahisalgado on 13/07/17.
 */

public interface CreateAccountPresenter {

    void createAccount(User user, Context context); // Interactor
    void createAccountSuccess();
    void createAccountError(String error);
}
