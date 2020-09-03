package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");
        //getSupportActionBar().hide();

        editTextUsername = findViewById(R.id.edt_login_userName);
        editTextPassword = findViewById(R.id.edt_login_password);

        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    logIn(buttonLogin);
                }
                return false;
            }
        });

        buttonLogin = findViewById(R.id.btn_login_login);

        textViewSignUp = findViewById(R.id.txt_login_signUp);
        progressBar = findViewById(R.id.progressbar);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.logOut();
            startActivity(new Intent(this,SocialMediaActivity.class));
        }
        Log.i("ParseUser", "logged in");
    }

    public void logIn(View view) {
        progressBar.setVisibility(View.VISIBLE);
        ParseUser.logInInBackground(editTextUsername.getText().toString(), editTextPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null && e == null) {
                    FancyToast.makeText(LoginActivity.this, user.getUsername() + " has logged in successfully",
                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                    startActivity(new Intent(LoginActivity.this,SocialMediaActivity.class));
                } else {
                    FancyToast.makeText(LoginActivity.this, e.getMessage(),
                            FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }
        });
        progressBar.setVisibility(View.INVISIBLE);
    }
}