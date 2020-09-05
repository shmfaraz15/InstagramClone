package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class UsersTab extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;



    public UsersTab() {
        // Required empty public constructor
    }


    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();

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
        View view= inflater.inflate(R.layout.fragment_users_tab, container, false);

        final ArrayList<String> arrayList=new ArrayList<>();
        listView=view.findViewById(R.id.listView);

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e==null){
                    if (users.size()>0){

                        for (ParseUser user:users){
                            arrayList.add(user.getUsername());
                        }

                        arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arrayList);
                        listView.setAdapter(arrayAdapter);
                    }else {
                        FancyToast.makeText(getContext(), "List size is zero",
                                FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }else {
                    FancyToast.makeText(getContext(), e.getMessage(),
                            FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }
        });
        return view;
    }
}