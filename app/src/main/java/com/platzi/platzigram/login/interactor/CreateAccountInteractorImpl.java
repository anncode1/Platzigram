package com.platzi.platzigram.login.interactor;

import android.content.Context;

import com.platzi.platzigram.login.presenter.CreateAccountPresenter;
import com.platzi.platzigram.login.repository.CreateAccountRepository;
import com.platzi.platzigram.login.repository.CreateAccountRepositoryImpl;
import com.platzi.platzigram.model.User;

/**
 * Created by anahisalgado on 13/07/17.
 */

public class CreateAccountInteractorImpl implements CreateAccountInteractor{

    private CreateAccountPresenter presenter;
    private CreateAccountRepository repository;

    public CreateAccountInteractorImpl(CreateAccountPresenter presenter) {
        this.presenter = presenter;
        repository = new CreateAccountRepositoryImpl(presenter);
    }


    @Override
    public void createAccount(User user, Context context) {
        repository.createAccount(user, context);
    }
}
