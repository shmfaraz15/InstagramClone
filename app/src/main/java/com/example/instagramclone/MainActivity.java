package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private EditText editTextName, editTextPunchSpeed, editTextPunchPower, editTextKickSpeed, editTextKickPower;
//    private Button saveButton;
//    private TextView textViewGetData;

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        editTextUsername = findViewById(R.id.edt_userName);
        editTextEmail = findViewById(R.id.edt_email);
        editTextPassword = findViewById(R.id.edt_password);

        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    signUp(buttonSignUp);
                }
                return false;
            }
        });

        buttonSignUp = findViewById(R.id.btn_signUp);

        textViewLogin = findViewById(R.id.txt_login);

        progressBar = findViewById(R.id.progressbar_signUp);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.logOut();
            Log.i("ParseUser", "logged in");
            startActivity(new Intent(this,SocialMediaActivity.class));
        }


//        editTextName = findViewById(R.id.edt_Name);
//        editTextPunchSpeed = findViewById(R.id.edt_punchSpeed);
//        editTextPunchPower = findViewById(R.id.edt_punchPower);
//        editTextKickSpeed = findViewById(R.id.edt_kickSpeed);
//        editTextKickPower = findViewById(R.id.edt_kickPower);
//
//        saveButton = findViewById(R.id.btn_save);
//
//        textViewGetData=findViewById(R.id.txt_getData);
    }

    public void signUp(View view) {
        ParseUser user = new ParseUser();
        user.setUsername(editTextUsername.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        user.setPassword(editTextPassword.getText().toString());

        if (editTextUsername.getText().toString().equals("") || editTextEmail.getText().toString().equals("") ||
                editTextPassword.getText().toString().equals("")) {

            FancyToast.makeText(MainActivity.this, "Username,Email,Password can't be empty",
                    FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
        } else {

//            ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Signing Up");
//            progressDialog.show();
            progressBar.setVisibility(View.VISIBLE);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(MainActivity.this, editTextUsername.getText().toString() + " has signed up successfully",
                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        startActivity(new Intent(MainActivity.this,SocialMediaActivity.class));
                    } else {
                        FancyToast.makeText(MainActivity.this, e.getMessage(),
                                FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
            progressBar.setVisibility(View.INVISIBLE);
            //progressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void save(View view) {
//
//        ParseObject kickBoxer = new ParseObject("KickBoxer");
//
//        try {
//            kickBoxer.put("name", editTextName.getText().toString());
//            kickBoxer.put("punchSpeed", Integer.parseInt(editTextPunchSpeed.getText().toString()));
//            kickBoxer.put("punchPower", Integer.parseInt(editTextPunchPower.getText().toString()));
//            kickBoxer.put("kickSpeed", Integer.parseInt(editTextKickSpeed.getText().toString()));
//            kickBoxer.put("kickPower", Integer.parseInt(editTextKickPower.getText().toString()));
//
//            kickBoxer.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null) {
//                        //Toast.makeText(MainActivity.this, "KickBoxer "+editTextName.getText().toString()+" is saved to sever", Toast.LENGTH_SHORT).show();
//                        FancyToast.makeText(MainActivity.this, "KickBoxer " + editTextName.getText().toString() + " is saved to sever",
//                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
//                    } else {
//                        //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
//                                FancyToast.ERROR, true).show();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            FancyToast.makeText(this, e.getMessage(),
//                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
//        }
//    }
//
//    public void getData(View view) {
//
//        /*ParseQuery<ParseObject> objectParseQuery=ParseQuery.getQuery("KickBoxer");
//        objectParseQuery.getInBackground("OAqRZH3Cnc", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (object!=null && e==null){
//                    String output="KickBoxer Name : "+object.get("name")+"\n"+
//                            "Punch Speed : "+object.get("punchSpeed")+"\n"+
//                            "Punch Power : "+object.get("punchPower")+"\n"+
//                            "Kick Speed : "+object.get("kickSpeed")+"\n"+
//                            "Kick Power : "+object.get("kickPower");
//
//                    textViewGetData.setText(output);
//                }
//                else{
//                    FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
//                            FancyToast.ERROR, true).show();
//                }
//            }
//        });*/
//
//        ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("KickBoxer");
//        parseQuery.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                String output="";
//                if (e==null) {
//                    if (objects.size() > 0) {
//                        for (ParseObject object : objects) {
//                            output += "KickBoxer Name : " + object.get("name") + "\n" +
//                                    "Punch Speed : " + object.get("punchSpeed") + "\n" +
//                                    "Punch Power : " + object.get("punchPower") + "\n" +
//                                    "Kick Speed : " + object.get("kickSpeed") + "\n" +
//                                    "Kick Power : " + object.get("kickPower") + "\n\n";
//                        }
//
//                        textViewGetData.setText(output);
//                    } else {
//
//                        FancyToast.makeText(MainActivity.this, "list size is zero", FancyToast.LENGTH_LONG,
//                                FancyToast.ERROR, true).show();
//                    }
//                }else {
//                    FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
//                            FancyToast.ERROR, true).show();
//                }
//            }
//        });
//    }
}