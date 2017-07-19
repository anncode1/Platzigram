package com.platzi.platzigram.login.repository;

import android.content.Context;

import com.platzi.platzigram.model.User;

/**
 * Created by anahisalgado on 13/07/17.
 */

public interface CreateAccountRepository {
    void createAccount(User user, Context context); // Interactor
}
