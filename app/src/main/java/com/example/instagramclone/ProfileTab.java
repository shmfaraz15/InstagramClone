package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileTab extends Fragment {

    private EditText editTextProfileName,editTextBio,editTextProfession,editTextHobbies,editTextFavouriteSport;
    private Button buttonUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    public static ProfileTab newInstance() {
        ProfileTab fragment = new ProfileTab();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        editTextProfileName=view.findViewById(R.id.edt_prifile_name);
        editTextBio=view.findViewById(R.id.edt_profile_bio);
        editTextProfession=view.findViewById(R.id.edt_profile_prefession);
        editTextHobbies=view.findViewById(R.id.edt_profile_hobbies);
        editTextFavouriteSport=view.findViewById(R.id.edt_profile_sport);

        buttonUpdateInfo=view.findViewById(R.id.btn_profile_update);

        final ParseUser parseUser=ParseUser.getCurrentUser();

        if (parseUser.get("profileName")==null){
            editTextProfileName.setText("");
        }else {
            editTextProfileName.setText(parseUser.get("profileName").toString());
        }

        if (parseUser.get("profileBio")==null){
            editTextBio.setText("");
        }else {
            editTextBio.setText(parseUser.get("profileBio").toString());
        }

        if (parseUser.get("profileProfession")==null){
            editTextProfession.setText("");
        }else {
            editTextProfession.setText(parseUser.get("profileProfession").toString());
        }
        if (parseUser.get("profileHobbies")==null){
            editTextHobbies.setText("");
        }else {
            editTextHobbies.setText(parseUser.get("profileHobbies").toString());
        }
        if (parseUser.get("profileFavouriteSport")==null){
            editTextFavouriteSport.setText("");
        }else {
            editTextFavouriteSport.setText(parseUser.get("profileFavouriteSport").toString());
        }
//        editTextProfileName.setText(parseUser.get("profileName").toString());
//        editTextBio.setText(parseUser.get("profileBio").toString());
//        editTextProfession.setText(parseUser.get("profileProfession").toString());
//        editTextHobbies.setText(parseUser.get("profileHobbies").toString());
//        editTextFavouriteSport.setText(parseUser.get("profileFavouriteSport").toString());

        buttonUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.put("profileName",editTextProfileName.getText().toString());
                parseUser.put("profileBio",editTextBio.getText().toString());
                parseUser.put("profileProfession",editTextProfession.getText().toString());
                parseUser.put("profileHobbies",editTextHobbies.getText().toString());
                parseUser.put("profileFavouriteSport",editTextFavouriteSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            FancyToast.makeText(getContext(), "Info Updated",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        }else {
                            FancyToast.makeText(getContext(), e.getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });

            }
        });

        return view;

    }
}