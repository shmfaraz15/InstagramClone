package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextPunchSpeed, editTextPunchPower, editTextKickSpeed, editTextKickPower;
    private Button saveButton;
    private TextView textViewGetData;

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

        textViewGetData=findViewById(R.id.txt_getData);
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

    public void getData(View view) {

//        ParseQuery<ParseObject> objectParseQuery=ParseQuery.getQuery("KickBoxer");
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
//        });

        ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("KickBoxer");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                String output="";
                if (e==null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            output += "KickBoxer Name : " + object.get("name") + "\n" +
                                    "Punch Speed : " + object.get("punchSpeed") + "\n" +
                                    "Punch Power : " + object.get("punchPower") + "\n" +
                                    "Kick Speed : " + object.get("kickSpeed") + "\n" +
                                    "Kick Power : " + object.get("kickPower") + "\n\n";
                        }

                        textViewGetData.setText(output);
                    } else {

                        FancyToast.makeText(MainActivity.this, "list size is zero", FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();
                    }
                }else {
                    FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
                            FancyToast.ERROR, true).show();
                }
            }
        });
    }
}