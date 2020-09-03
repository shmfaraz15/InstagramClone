package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextPunchSpeed, editTextPunchPower, editTextKickSpeed, editTextKickPower;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.edt_Name);
        editTextPunchSpeed = findViewById(R.id.edt_punchSpeed);
        editTextPunchPower = findViewById(R.id.edt_punchPower);
        editTextKickSpeed = findViewById(R.id.edt_kickSpeed);
        editTextKickPower = findViewById(R.id.edt_kickPower);

        saveButton = findViewById(R.id.btn_save);
    }

    public void save(View view) {

        ParseObject kickBoxer = new ParseObject("KickBoxer");

        try {
            kickBoxer.put("name", editTextName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(editTextPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(editTextPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(editTextKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(editTextKickPower.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Toast.makeText(MainActivity.this, "KickBoxer "+editTextName.getText().toString()+" is saved to sever", Toast.LENGTH_SHORT).show();
                        FancyToast.makeText(MainActivity.this, "KickBoxer " + editTextName.getText().toString() + " is saved to sever",
                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(this, e.getMessage(),
                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }
}