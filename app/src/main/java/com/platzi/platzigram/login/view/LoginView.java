package com.platzi.platzigram.login.view;

/**
 * Created by anahisalgado on 24/05/17.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showProgressBar();
    void hideProgressBar();

    void loginError(String error);

    void goCreateAccount();
    void goHome();

}
