package com.platzi.platzigram.login.view;

/**
 * Created by anahisalgado on 13/07/17.
 */

public interface CreateAccountView {

    void showProgressBar();
    void hideProgressBar();

    void createAccountError(String error);

    void goHome();
}
