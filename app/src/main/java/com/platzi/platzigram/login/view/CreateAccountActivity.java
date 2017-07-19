package com.platzi.platzigram.login.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.platzi.platzigram.R;
import com.platzi.platzigram.login.presenter.CreateAccountPresenter;
import com.platzi.platzigram.login.presenter.CreateAccountPresenterImpl;
import com.platzi.platzigram.login.presenter.LoginPresenter;
import com.platzi.platzigram.model.User;
import com.platzi.platzigram.view.ContainerActivity;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountView{

    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnJoinUs;
    private TextInputEditText edtEmail, edtName, edtUser, edtPassword, edtPasswordConfirm;
    private ProgressBar progressBarCreateAccount;

    private boolean userLogged = false;
    private User user;


    private CreateAccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount), true);

        presenter = new CreateAccountPresenterImpl(this);

        btnJoinUs           = (Button) findViewById(R.id.joinUs);
        edtEmail            = (TextInputEditText) findViewById(R.id.email);
        edtName             = (TextInputEditText) findViewById(R.id.name);
        edtUser             = (TextInputEditText) findViewById(R.id.user);
        edtPassword         = (TextInputEditText) findViewById(R.id.password_createaccount);
        edtPasswordConfirm  = (TextInputEditText) findViewById(R.id.confirmPassword);
        progressBarCreateAccount  = (ProgressBar) findViewById(R.id.progressbarCreateAccount);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    FirebaseCrash.logcat(Log.WARN, TAG, "Usuario logeado " + firebaseUser.getEmail());

                    if (userLogged){
                        presenter.createAccount(user, getBaseContext());
                    }

                }else {
                    FirebaseCrash.logcat(Log.WARN, TAG, "Usuario NO logeado ");
                }
            }
        };

        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                user = new User(
                    edtName.getText().toString(),
                    edtUser.getText().toString(),
                    edtEmail.getText().toString(),
                    edtPassword.getText().toString()
                );

                if (user.getEmail().trim().equals("")){
                    Toast.makeText(CreateAccountActivity.this, "El Email es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getName().trim().equals("")){
                    Toast.makeText(CreateAccountActivity.this, "El Nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getUsername().trim().equals("")){
                    Toast.makeText(CreateAccountActivity.this, "El Username es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!user.getPassword().equals(edtPasswordConfirm.getText().toString())){
                    Toast.makeText(CreateAccountActivity.this, "Los Passwords deben ser iguales", Toast.LENGTH_SHORT).show();
                    return;
                }

                createAccount(user);
            }
        });

    }

    private void createAccount(final User user) {

        userLogged = true;
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this, "Cuenta Creada Exitosamente", Toast.LENGTH_SHORT).show();

                        }else {
                            userLogged = false;
                            Toast.makeText(CreateAccountActivity.this, "Ocurrió un Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void showProgressBar() {
        progressBarCreateAccount.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        progressBarCreateAccount.setVisibility(View.GONE);
    }

    @Override
    public void createAccountError(String error) {
        Toast.makeText(this, "Ocurrió este error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
